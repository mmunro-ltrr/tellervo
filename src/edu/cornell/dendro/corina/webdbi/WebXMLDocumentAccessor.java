package edu.cornell.dendro.corina.webdbi;

import edu.cornell.dendro.corina.core.App;
import edu.cornell.dendro.corina.gui.Bug;
import edu.cornell.dendro.corina.Build;
import edu.cornell.dendro.corina.prefs.Prefs;
import edu.cornell.dendro.corina.platform.Platform;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.XMLOutputter;

import java.net.*;
import javax.net.ssl.*;
import java.io.*;

/*
 * This class is for accessing XML documents from the web service.
 */

public class WebXMLDocumentAccessor {	
	/** The URL we plan to access */ 
	private URL url;
	
	/** The type of request we plan to use (POST or GET) */
	private int requestMethod;
	
	/** The XML document we plan to post to the server */
	private Document requestDocument;
	
	/**
	 * Makes a class capable of performing operations on a webservice "noun" 
	 * This is for original-style requests that solely use HTTP GET
	 * 
	 * For POST queries, use the constructor that only requires a noun
	 * 
	 * @param noun 
	 * What we are affecting here. For example, dictionary, sample, etc.
	 * @param verb
	 * What type of action we're making (create, read, update, delete)
	 */
	public WebXMLDocumentAccessor(String noun, String verb) {
		requestMethod = METHOD_GET;
		
		try {
			String path = App.prefs.getPref("corina.webservice.url");
			if(!path.endsWith("/"))
				path += "/";
			path += noun + ".php" + "?mode=" + verb;

			// debug
			//path = "http://www.negaverse.org:8080/cows.php";			
			
			url = new URL(path);
			
			System.out.println("Access URL: " + url);
		} catch (MalformedURLException e) {
			new Bug(e);
		}
	}

	/**
 	 * Makes a class capable of performing operations on a webservice "noun" 
	 * 
	 * Note that this constructor will be useless for queries that do not contain
	 * a POST request.
	 * 
	 * @param noun What we are affecting here. For example, dictionary, sample, etc.
	 */
	public WebXMLDocumentAccessor(String noun) {
		requestMethod = METHOD_POST;
		
		try {
			String path = App.prefs.getPref("corina.webservice.url");
			if(!path.endsWith("/"))
				path += "/";
			path += noun + ".php";
			
			url = new URL(path);
			
			System.out.println("Access URL: " + url);
		} catch (MalformedURLException e) {
			new Bug(e);
		}		
	}
		
	public Document query() throws IOException {
		return doRequest();
	}
	
	public void execute() throws IOException {
		doRequest();
	}
	
	private Document doRequest() throws IOException {
		HttpURLConnection http = (HttpURLConnection) url.openConnection();

		try {
			if(requestMethod == METHOD_POST) {
				if(this.requestDocument == null)
					throw new NullPointerException("requestDocument is null yet required for this type of query");
				http.setRequestMethod("POST");
				http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				http.setDoOutput(true);
			}
			
			// Set any header fields and do other various setup
			http.setRequestProperty("User-Agent", "Corina WSI " + Build.VERSION);
			http.setUseCaches(false);

			// if we're using an https connection, we're going to have to be careful
			if(http instanceof HttpsURLConnection) {
				HttpsURLConnection https = (HttpsURLConnection) http;

				try {
					SSLContext sc = SSLContext.getInstance("SSL");
					sc.init(null, trustAllCerts, new java.security.SecureRandom());
					https.setSSLSocketFactory(sc.getSocketFactory());
				} catch (Exception e) {
					// don't do anything; we'll just get errors later.
				}
			}

			if(requestMethod == METHOD_POST) {
				XMLOutputter outp = new XMLOutputter();
	
				http.getOutputStream().write("xmlrequest=".getBytes());
				outp.output(requestDocument, new URLEncodingOutputStream(http.getOutputStream()));
				// useful for debugging
				//outp.output(requestDocument, http.getOutputStream());
				
				outp.output(requestDocument, System.out);
			}
			
			int responseCode = http.getResponseCode();
			if(responseCode != HttpURLConnection.HTTP_OK) {
				throw new IOException("Unexpected response code " + responseCode + " while accessing " + url.toExternalForm());
			}

			//InputStream in = http.getInputStream();
			// Wrap a bufferedreader around this, so the saxbuilder can't break our socket and hang
			BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
			
			// Skip past any sort of errors PHP might throw at us.
			String line;
			in.mark(4096);
			while((line = in.readLine()) != null) {
				if(line.startsWith("<?xml")) {
					in.reset();
					break;
				}
				in.mark(4096);
			}

			/*
			//debug
			in.mark(16384);
			try {
				StringBuffer sb = new StringBuffer();
				int v;
				
				while((v = in.read()) != -1) {
					sb.append((char) v);
				}
				
				System.out.println(sb.toString());
			} catch (Exception e) {}
			in.reset();
			*/
			
			try {
				// parse the input into an XML document
				Document inDocument = new SAXBuilder().build(in);
				CorinaDocumentInspector inspector = new CorinaDocumentInspector(inDocument);
				
				// Verify our document structure, throw any exceptions!
				inspector.verifyDocument();
				
				return inDocument;				
			} catch (JDOMException jdoe) {
				throw new IOException("Could not parse document: JDOM error: " + jdoe);
			}
		} finally {
			// make sure that no matter what, we clean up.
			http.disconnect();
		}
	}

	// This allows us to ignore the server certificate chain
	private static TrustManager[] trustAllCerts = new TrustManager[] {
		new X509TrustManager() {
        	public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
        	public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
        	public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
        }
	};
	
	/**
	 * Creates an XML document-based request to be sent to the web interface with the basic format:
	 * <corina>
	 *    <request type="verb">
	 *    </request>
	 * </corina>
	 * 
	 * Calling this function a second time will create a new request.
	 * 
	 * @param requestVerb the verb to be used in request: create, read, update, delete, login
	 * @return the Element "request"
	 */
	public Element createRequest(String requestVerb) {
		requestMethod = METHOD_POST;
		
		// create the document
		requestDocument = new Document();
		
		// create the root element
		Element corina = new Element("corina");			
		requestDocument.setRootElement(corina);
		
		// create the request element
		Element request = new Element("request");
		request.setAttribute("type", requestVerb);
		corina.addContent(request);
		
		return request;
	}
	
	/**
	 * 
	 * @return the 'request' element in our xml document
	 */
	public Element getRequest() {		
		return requestDocument.getRootElement().getChild("request");
	}

	// for defining our request type...
	private static final int METHOD_POST = 1;
	private static final int METHOD_GET = 2;

	public static void main(String args[]) {
		App.platform = new Platform();
		App.platform.init();
		App.prefs = new Prefs();
		App.prefs.init();
		
		try{ 
			/*
			Dictionary d = new Dictionary();
			d.loadWait();
			*/
			
			WebXMLDocumentAccessor a = new WebXMLDocumentAccessor("authenticate");
			Element e;
			
			e = a.createRequest("nonce");
			e.addContent(new Element("authenticate"));
			a.execute();
			
			e = a.createRequest("securelogin");
			Element auth = new Element("authenticate");
			e.addContent(auth);			
			auth.setAttribute("username", "kit");
			a.execute();
			
			/*
			WebXMLDocumentAccessor a = new WebXMLDocumentAccessor("dictionaries", "read");
			Document doc = a.query();

			System.out.println("Result: ");
			XMLOutputter out = new XMLOutputter();
			out.output(doc, System.out);
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
