
package edu.cornell.dendro.corina.print;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.tridas.schema.TridasElement;
import org.tridas.schema.TridasObject;
import org.tridas.schema.TridasSample;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import edu.cornell.dendro.corina.core.App;
import edu.cornell.dendro.corina.formats.Metadata;
import edu.cornell.dendro.corina.gui.XMLDebugView;
import edu.cornell.dendro.corina.platform.Platform;
import edu.cornell.dendro.corina.sample.Sample;
import edu.cornell.dendro.corina.schema.CorinaRequestFormat;
import edu.cornell.dendro.corina.schema.SearchOperator;
import edu.cornell.dendro.corina.schema.SearchParameterName;
import edu.cornell.dendro.corina.schema.SearchReturnObject;
import edu.cornell.dendro.corina.schema.WSIBox;
import edu.cornell.dendro.corina.tridasv2.TridasComparator;
import edu.cornell.dendro.corina.tridasv2.TridasObjectEx;
import edu.cornell.dendro.corina.ui.Alert;
import edu.cornell.dendro.corina.util.labels.LabBarcode;
import edu.cornell.dendro.corina.util.pdf.PrintablePDF;
import edu.cornell.dendro.corina.util.test.PrintReportFramework;
import edu.cornell.dendro.corina.wsi.corina.CorinaResourceAccessDialog;
import edu.cornell.dendro.corina.wsi.corina.CorinaResourceProperties;
import edu.cornell.dendro.corina.wsi.corina.SearchParameters;
import edu.cornell.dendro.corina.wsi.corina.resources.EntitySearchResource;

/**
 * Class for producing labels to fix to lab storage boxes.  Designed for 
 * Avery 6579 system labels 5 x 8 1/8" labels 2 per letter sized sheet
 * 
 * @author peterbrewer
 *
 */
public class BoxLabel extends ReportBase{
	
	private ArrayList<WSIBox> boxlist = new ArrayList<WSIBox>();
	
	public BoxLabel(Sample s){
		
		if(s==null)
		{
			System.out.println("Error - sample is null");
			return;
		}
		
		if (s.getMeta(Metadata.BOX, WSIBox.class)==null)
		{
			System.out.println("Error - no box associated with this series");
			return;
		}
		boxlist.add(s.getMeta(Metadata.BOX, WSIBox.class));
	}
	
	public BoxLabel(WSIBox b){
		if (b==null)
		{
			System.out.println("Error - box is null");
			return;
		}
		boxlist.add(b);
	}
		
	public BoxLabel(ArrayList<WSIBox> bl)
	{
		boxlist = bl;
	}
	
	public void generateBoxLabel(OutputStream output) {
	
		try {
		
			PdfWriter writer = PdfWriter.getInstance(document, output);
			
			document.setPageSize(PageSize.A5.rotate());
			document.open();
		
			cb = writer.getDirectContent();			
			
			// Set basic metadata
		    document.addAuthor("Peter Brewer"); 
		    document.addSubject("Box Label"); 
				
		    for(WSIBox b : this.boxlist)
		    {
			    
				// Title Left		
				ColumnText ct = new ColumnText(cb);
				ct.setSimpleColumn(document.left(), document.top(15)-163, 283, document.top(15), 20, Element.ALIGN_LEFT);
				ct.addText(getTitlePDF(b));
				ct.go();
				
				// Barcode
				ColumnText ct2 = new ColumnText(cb);
				ct2.setSimpleColumn(284, document.top(15)-100, document.right(15), document.top(15), 20, Element.ALIGN_RIGHT);
				ct2.addElement(getBarCode(b));
				ct2.go();			
					
				// Timestamp
				ColumnText ct3 = new ColumnText(cb);
				ct3.setSimpleColumn(document.left(), document.top(15)-223, 283, document.top(15)-60, 20, Element.ALIGN_LEFT);
				ct3.setLeading(0, 1.2f);
				ct3.addText(getTimestampPDF(b));
				ct3.go();		
				
				// Pad text
		        document.add(new Paragraph(" "));      
		        Paragraph p2 = new Paragraph();
		        p2.setSpacingBefore(70);
			    p2.setSpacingAfter(10);
			    p2.add(new Chunk(" ", bodyFont));  
		        document.add(new Paragraph(p2));
		        
		        // Ring samples table
		        getTable(b);
		        document.add(getParagraphSpace());	
			    
		        getComments(b);
		        
		        document.newPage();

		    }
		    
			
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}

		// Close the document
		document.close();
	}
	
	
	/**
	 * Get an iText Paragraph for the Title 
	 * 
	 * @return Paragraph
	 */
	private Paragraph getTitlePDF(WSIBox b)
	{
		Paragraph p = new Paragraph();
		
		p.add(new Chunk(b.getTitle()+"\n", monsterFont));
	
		//p.add(new Chunk(b.getCurationLocation(), subTitleFont));
				
		return p;		
	}
	
	
	/**
	 * Create a series bar code for this series
	 * 
	 * @return Image 
	 */
	private Image getBarCode(WSIBox b)
	{
		UUID uuid = UUID.fromString(b.getIdentifier().getValue());
		LabBarcode barcode = new LabBarcode(LabBarcode.Type.BOX, uuid);

		barcode.setX(0.8f);
		barcode.setSize(5f);
		barcode.setBarHeight(20f);
		barcode.setBaseline(-1f);	
		
		Image image = barcode.createImageWithBarcode(cb, null, null);
	
		return image;
	
	}
	
	
	/**
	 * Get PdfPTable containing the samples per object
	 * 
	 * @return PdfPTable
	 * @throws DocumentException 
	 */
	private void getTable(WSIBox b) throws DocumentException
	{
		float[] widths = {0.1f, 0.75f, 0.1f};
		PdfPTable tbl = new PdfPTable(widths);
		PdfPCell headerCell = new PdfPCell();

		tbl.setWidthPercentage(100f);

		// Write header cells of table
		headerCell.setBorderWidthBottom(headerLineWidth);
		headerCell.setBorderWidthTop(headerLineWidth);
		headerCell.setBorderWidthLeft(0);
		headerCell.setBorderWidthRight(0);		
		headerCell.setPhrase(new Phrase("Object", tableHeaderFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tbl.addCell(headerCell);
		headerCell.setPhrase(new Phrase("Elements", tableHeaderFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		tbl.addCell(headerCell);
		headerCell.setPhrase(new Phrase("# Samples", tableHeaderFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl.addCell(headerCell);
									
		// Find all objects associated with samples in this box
		SearchParameters objparam = new SearchParameters(SearchReturnObject.OBJECT);
		objparam.addSearchConstraint(SearchParameterName.SAMPLEBOXID, SearchOperator.EQUALS, b.getIdentifier().getValue().toString());
		EntitySearchResource<TridasObject> objresource = new EntitySearchResource<TridasObject>(objparam);
		CorinaResourceAccessDialog dialog = new CorinaResourceAccessDialog(objresource);
		objresource.query();	
		dialog.setVisible(true);
		if(!dialog.isSuccessful()) 
		{ 
			System.out.println("oopsey doopsey.  Error getting objects");
			return;
		}
		List<TridasObject> obj = objresource.getAssociatedResult();
		
		// Check that there are not too many objects to fit on box label
		if(obj.size()>10) 
		{
			System.out.println("Warning this label has " + Integer.toString(obj.size()) + " objects associated with it so is unlikely to fit and may take some time to produce!");
		}
		
		// Sort objects into alphabetically order based on labcode
		TridasComparator sorter = new TridasComparator();
		Collections.sort(obj, sorter);
		
		// Loop through objects
		for(TridasObject myobj : obj)
		{	
			// Add object code to first column			
			PdfPCell dataCell = new PdfPCell();
			dataCell.setBorderWidthBottom(0);
			dataCell.setBorderWidthTop(0);
			dataCell.setBorderWidthLeft(0);
			dataCell.setBorderWidthRight(0);
			dataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			String objCode;
			if(myobj instanceof TridasObjectEx) objCode = ((TridasObjectEx)myobj).getLabCode(); else objCode = myobj.getTitle();	
			dataCell.setPhrase(new Phrase(objCode, bodyFont));
			tbl.addCell(dataCell);
			
			// Search for elements associated with this object
			System.out.println("Starting search for elements associated with " + myobj.getTitle().toString());
			SearchParameters sp = new SearchParameters(SearchReturnObject.ELEMENT);		
			sp.addSearchConstraint(SearchParameterName.SAMPLEBOXID, SearchOperator.EQUALS, b.getIdentifier().getValue());
			sp.addSearchConstraint(SearchParameterName.OBJECTID, SearchOperator.EQUALS, myobj.getIdentifier().getValue());
			EntitySearchResource<TridasElement> resource = new EntitySearchResource<TridasElement>(sp);
			resource.setProperty(CorinaResourceProperties.ENTITY_REQUEST_FORMAT, CorinaRequestFormat.SUMMARY);
			CorinaResourceAccessDialog dialog2 = new CorinaResourceAccessDialog(resource);
			resource.query();
			dialog2.setVisible(true);
			if(!dialog2.isSuccessful()) 
			{ 	
				System.out.println("oopsey doopsey.  Error getting elements");
				return;
			}
			//XMLDebugView.showDialog();
			List<TridasElement> elements = resource.getAssociatedResult();
			TridasComparator numSorter = new TridasComparator(TridasComparator.Type.TITLES, 
					TridasComparator.NullBehavior.NULLS_LAST, 
					TridasComparator.CompareBehavior.AS_NUMBERS_THEN_STRINGS);
			Collections.sort(elements, numSorter);	
			
			// Loop through elements 
			Integer smpCnt = 0;
			ArrayList<String> numlist = new ArrayList<String>();
			for(TridasElement myelem : elements)
			{
				// Add element title to string
				if(myelem.getTitle()!=null) 
				{
					String mytitle = myelem.getTitle();
					numlist.add(mytitle);
				}

				// Grab associated samples and add count to running total
				List<TridasSample> samples = myelem.getSamples(); 
				smpCnt += samples.size();
			}
						
			
			// Add element names to second column
			dataCell.setPhrase(new Phrase(hyphenSummarize(numlist), bodyFont));
			tbl.addCell(dataCell);
			
			// Add sample count to third column
			dataCell.setPhrase(new Phrase(smpCnt.toString(), bodyFont));
			dataCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(dataCell);
			
		}
		
				
		
		headerCell.setBorderWidthBottom(headerLineWidth);
		headerCell.setBorderWidthTop(headerLineWidth);
		headerCell.setBorderWidthLeft(0);
		headerCell.setBorderWidthRight(0);
		
		headerCell.setPhrase(new Phrase(" ", bodyFont));
		tbl.addCell(headerCell);	
		headerCell.setPhrase(new Phrase("Grand Total", bodyFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl.addCell(headerCell);	
		headerCell.setPhrase(new Phrase(b.getSampleCount().toString(), bodyFont));
		tbl.addCell(headerCell);
		
		// Add table to document
		document.add(tbl);		
	}
		
	
	public String hyphenSummarize(List<String> lst)
	{

		String returnStr = "";
		
		/*
		if (none==true)
		{
			for(String item : lst)
			{
				returnStr += item + ", ";
				
			}
			
			if (returnStr.length()>2) returnStr = returnStr.substring(0, returnStr.length()-2);
			return returnStr;
		}*/


		Integer lastnum = null; 
		Boolean inSeq = false;
		
		for(String item : lst)
		{
			if (containsOnlyNumbers(item))
			{
				// Contains only numbers
				if(lastnum==null)
				{
					// Lastnum is null so just add item to return string and continue to the next in list
					returnStr += ", " + item;
					lastnum = Integer.valueOf(item);
					continue;
				}
				else
				{
					if(inSeq==true)
					{
						if(isNextInSeq(lastnum, Integer.valueOf(item)))
						{
							// Keep going!
							inSeq = true;
							lastnum = Integer.valueOf(item);
							continue;
						}
						else
						{
							// 
							returnStr += "-" + lastnum.toString() + ", " + item;
							inSeq = false;
							lastnum = Integer.valueOf(item);
							continue;
							
						}
					}
					else
					{
						// Not in sequence yet
						
						if(isNextInSeq(lastnum, Integer.valueOf(item)))
						{
							// Keep going!
							inSeq = true;
							lastnum = Integer.valueOf(item);
							continue;
						}
						else
						{
							returnStr += ", " + item;
							lastnum = Integer.valueOf(item);
							continue;
						}
					}
						
					
				}
			}
			else
			{
				// Contains some chars so just add as comma delimated string to return string
				returnStr += ", " + item;
				lastnum = null;
				inSeq = null;
			}
			
			
			
			
		}
		
		returnStr = returnStr.substring(2, returnStr.length());
		return returnStr;
	}
	
	private Boolean isNextInSeq(Integer seqnum, Integer curnum)
	{
		if(seqnum+1==curnum)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
		
    /**
     * This method checks if a String contains only numbers
     */
    public boolean containsOnlyNumbers(String str) {
        
        //It can't contain only numbers if it's null or empty...
        if (str == null || str.length() == 0)
            return false;
        
        for (int i = 0; i < str.length(); i++) {

            //If we find a non-digit character we return false.
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        
        return true;
    }
	
	
	/**
	 * iText paragraph containing created and lastmodified timestamps
	 * 
	 * @return Paragraph
	 */
	private Paragraph getTimestampPDF(WSIBox b)
	{
		// Set up calendar
		Date createdTimestamp = b.getCreatedTimestamp().getValue()
				.toGregorianCalendar().getTime();
		Date lastModifiedTimestamp = b.getLastModifiedTimestamp()
				.getValue().toGregorianCalendar().getTime();
		
		Date nowTimestamp = new Date();
		
		DateFormat df1 = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
		
		Paragraph p = new Paragraph();

	
		p.add(new Chunk("Created: ", subSectionFont));
		p.add(new Chunk(df1.format(createdTimestamp), bodyFont));
		//p.add(new Chunk("\nLast Modified: ", subSectionFont));
		//p.add(new Chunk(df1.format(lastModifiedTimestamp), bodyFont));
		p.add(new Chunk("\nLabel updated: ", subSectionFont));
		p.add(new Chunk(df1.format(nowTimestamp), bodyFont));

		
		return p;
		
	}
	

	
	/**
	 * Get the font style to use in the table based on column number
	 * @param col column number
	 * @return Font
	 */
	private  Font getTableFont(int col)
	{
		
		if (col==0)	{
			return tableHeaderFont;
		}
		else {
			return bodyFont;
		}
				
	}
	
	private void getComments(WSIBox b) throws DocumentException
	{
	
		Paragraph p = new Paragraph();
		p.setLeading(0, 1.2f);
		
		p.add(new Chunk("Comments: \n", subSectionFont));
		if(b.getComments()!=null){
			p.add(new Chunk(b.getComments(), bodyFont));
		}
		else{
			p.add(new Chunk("No comments recorded", bodyFont));
		}
		
		document.add(p);
	}

	/**
	 * Function for printing or viewing series report
	 * @param printReport Boolean
	 * @param vmid String
	 */
	public void getLabel(Boolean printReport)
	{
			
		if(printReport) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			this.generateBoxLabel(output);
			
			try {
				PrintablePDF pdf = PrintablePDF.fromByteArray(output.toByteArray());

				// true means show printer dialog, false means just print using the default printer
				pdf.print(true);
			} catch (Exception e) {
				e.printStackTrace();
				Alert.error("Printing error", "An error occured during printing.\n  See error log for further details.");
			}
		}
		else {
			// probably better to use a chooser dialog here...
			try {
				File outputFile = File.createTempFile("boxlabel", ".pdf");
				FileOutputStream output = new FileOutputStream(outputFile);
				
				this.generateBoxLabel(output);

				App.platform.openFile(outputFile);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				Alert.error("Error", "An error occurred while generating the box label.\n  See error log for further details.");
				return;
			}
		}
		
	}
	
	/**
	 * Function for printing or viewing series report
	 * @param printReport Boolean
	 * @param vmid String
	 */
	public static void getLabel(Boolean printReport, String vmid)
	{
		
		String domain = "dendro.cornell.edu/dev/";
		Sample samp = null;
		
		try {
			samp = PrintReportFramework.getSampleForID(domain, vmid);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		// create the box label
		BoxLabel label = new BoxLabel(samp);		
		
		if(printReport) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			label.generateBoxLabel(output);
			
			try {
				PrintablePDF pdf = PrintablePDF.fromByteArray(output.toByteArray());

				// true means show printer dialog, false means just print using the default printer
				pdf.print(true);
			} catch (Exception e) {
				e.printStackTrace();
				Alert.error("Printing error", "An error occured during printing.\n  See error log for further details.");
			}
		}
		else {
			// probably better to use a chooser dialog here...
			try {
				File outputFile = File.createTempFile("boxlabel", ".pdf");
				FileOutputStream output = new FileOutputStream(outputFile);
				
				label.generateBoxLabel(output);

				App.platform.openFile(outputFile);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				Alert.error("Error", "An error occurred while generating the box label.\n  See error log for further details.");
				return;
			}
		}
		
	}
	

	
	public static void main(String[] args) {
		String domain = "dendro.cornell.edu/dev/";
		String measurementID = "c15449db-9834-5271-a699-9217d42919c1";
		String filename = "output.pdf";
		
	    App.platform = new Platform();
	    App.platform.init();	    
		App.init(null, null);
		Sample samp = null;
		
		BoxLabel label = new BoxLabel(samp);
		label.getLabel(false, measurementID);
		
	}
	
}