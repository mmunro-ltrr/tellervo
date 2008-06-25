// Copyright (c) 2004-2005 Aaron Hamid.  All rights reserved.
// See license in COPYING.txt distributed with this file and available online at http://www.gnu.org/licenses/gpl.txt

package edu.cornell.dendro.corina.core;

import edu.cornell.dendro.corina.gui.ProgressMeter;
import edu.cornell.dendro.corina.logging.CorinaLog;
import edu.cornell.dendro.corina.logging.Logging;
import edu.cornell.dendro.corina.platform.Platform;
import edu.cornell.dendro.corina.prefs.Prefs;
import edu.cornell.dendro.corina.site.SiteList;
import edu.cornell.dendro.corina.dictionary.Dictionary;

/**
 * Contextual state of the app; holds references to all "subsystems".
 * Originally was going to be named AppContext, but shortened because it
 * has to be referenced frequently.
 * @author Aaron Hamid
 */
public class App {
  public static Prefs prefs;
  public static Platform platform;
  public static Logging logging;
  public static Dictionary dictionary;
  public static SiteList sites;

  private static final CorinaLog log = new CorinaLog(App.class);
  private static boolean initialized;

  public static synchronized void init(ProgressMeter meter) {
    // throwing an error instead of simply ignoring it
    // will point out bad design and/or bugs
    if (initialized) throw new IllegalStateException("AppContext already initialized.");

    log.debug("initializing App");

    if (meter != null) {
      meter.setMaximum(5);
      meter.setNote("Initializing Logging...");
    }

    logging = new Logging();
    logging.init();
    if (meter != null) {
      meter.setProgress(1);
      meter.setNote("Initializing Platform...");
    }
    
    platform = new Platform();
    platform.init();
    
    if (meter != null) {
      meter.setProgress(2);
    }

    // <init prefs>
    //prefs = new Prefs();
    //prefs.init();

    // if the user hasn't specified a parser with
    // -Dorg.xml.sax.driver=..., use crimson.
    if (System.getProperty("org.xml.sax.driver") == null)
      System.setProperty("org.xml.sax.driver", "org.apache.crimson.parser.XMLReaderImpl");
    // xerces: "org.apache.xerces.parsers.SAXParser"
    // gnu/jaxp: "gnu.xml.aelfred2.SAXDriver"

    // migrate old prefs (!!!)
    // WAS: Migrate.migrate();

    // load properties -- messagedialog here is UGLY!
    if (meter != null) {
      meter.setNote("Initializing Preferences...");
    }
    prefs = new Prefs();
    prefs.init();
    if (meter != null) {
      meter.setProgress(3);
    }
    /*try {
      //Prefs.init();
    } catch (IOException ioe) {
      JOptionPane.showMessageDialog(null,
          "While trying to load preferences:\n" + ioe.getMessage(),
          "Corina: Error Loading Preferences", JOptionPane.ERROR_MESSAGE);
    }*/

    if (meter != null) {
        meter.setNote("Initializing Dictionary...");
    }
    dictionary = new Dictionary();
    // dictionary.query();
    if (meter != null) {
      meter.setProgress(4);
    }

    if (meter != null) {
        meter.setNote("Initializing Site List...");
    }
    sites = new SiteList();
  //  sites.query();
    if (meter != null) {
      meter.setProgress(5);
    }

    initialized = true;   
  }

  public static boolean isInitialized() {
    return initialized;
  }

  public static synchronized void destroy(ProgressMeter meter) {
    // throwing an error instead of simply ignoring it
    // will point out bad design and/or bugs
    if (!initialized) throw new IllegalStateException("AppContext already destroyed.");
  }
}