/*
 * HelpWiki.java
 *
 * Created on September 17, 2008, 2:08 PM
 */

package edu.cornell.dendro.corina.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mozilla.browser.MozillaPanel;
import org.mozilla.browser.MozillaPanel.VisibilityMode;

/**
 *
 * @author  peterbrewer
 */
public class HelpWiki extends XFrame {
	private static final long serialVersionUID = 1L;

	/** Creates new form HelpWiki */
    public HelpWiki(String page) {
        super();
        
        if(page == null || page == "") 
        	page = "http://dendro.cornell.edu/corina-manual/UserGuideContents";
        

		
        
        mozillaPanel = new MozillaPanel(VisibilityMode.FORCED_HIDDEN, VisibilityMode.DEFAULT);
        mozillaPanel.setUpdateTitle(true);
        
        
        // apparently, have to stick it in a dummy holder panel
        JPanel holder = new JPanel(new BorderLayout());
        holder.add(mozillaPanel, BorderLayout.CENTER);
        setContentPane(holder);
        
        setTitle("Corina Help Wiki");

        // die on close!
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		pack();
		
        // make the frame medium to start off with
		// but make it maximized
        setSize(500, this.getToolkit().getScreenSize().height);
		//setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        mozillaPanel.load(page);
        
    }
        
    /**
     * Show the help window!
     */
	public static void showHelp() {
		HelpWiki dialog = new HelpWiki(null);
		dialog.setVisible(true);
	}
	
    /**
     * Show the help window!
     */
	public static void showHelp(String page) {
		// See if we have access to mozilla libs
		try {
			// this loads the DLL...
			Class.forName("org.mozilla.browser");
		}
		catch (Exception e) {
			// driver not installed...
			System.out.println("No mozilla - no help");
			System.out.println(e.toString());
			return;
		}
		catch (Error e) {
			// native interface not installed...
			System.out.println("No mozilla - no help");
			System.out.println(e.toString());
			return;
		}
		
		HelpWiki dialog = new HelpWiki(page);
		dialog.setVisible(true);
		dialog.setAlwaysOnTop(true);
	}	
    
	private MozillaPanel mozillaPanel;
}