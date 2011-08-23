/*******************************************************************************
 * Copyright (C) 2011 Peter Brewer.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Peter Brewer
 ******************************************************************************/
package edu.cornell.dendro.corina.gis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import net.miginfocom.swing.MigLayout;
import edu.cornell.dendro.corina.core.App;
import edu.cornell.dendro.corina.gui.Help;
import edu.cornell.dendro.corina.prefs.Prefs.PrefKey;
import edu.cornell.dendro.corina.ui.Alert;
import edu.cornell.dendro.corina.ui.I18n;
import gov.nasa.worldwind.event.RenderingExceptionListener;

/**
 *
 * @author  peterbrewer
 */
@SuppressWarnings("serial")
public class GrfxWarning extends javax.swing.JPanel implements ActionListener {
    
	JPanel mapPanel;
	protected EventListenerList listenerList = new EventListenerList();
	
	
    /** Creates new form GrfxWarning */
    public GrfxWarning() {
        initComponents();
        setupGui();
    }
    
    public GrfxWarning(JPanel mapPanel) {
        this.mapPanel = mapPanel;
    	initComponents();
        setupGui();
    }
    
    private void setupGui()
    {
    	this.txtGrfxWarning.setContentType("text/html");
    	this.txtGrfxWarning.setEditable(false);
    	this.txtGrfxWarning.setText("<html><font color=\"red\"><center><b>Mapping disabled</b></center></font><p>The 3D globe mapping in Corina requires an OpenGL 3D capable graphics card.  Either your graphics card is not supported or you do not have the proper graphics drivers installed.  Most graphics cards manufactured since 2006 are supported. </p></html>");
    	
    	this.btnRetry.addActionListener(this);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtGrfxWarning = new javax.swing.JTextPane();
        btnRetry = new javax.swing.JButton();

        setAlignmentX(1.0F);
        setLayout(new MigLayout("", "[390px,grow]", "[153.00px,top][25px][grow]"));

        jScrollPane1.setAlignmentY(1.0F);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setHorizontalScrollBar(null);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(390, 180));
        jScrollPane1.setViewportView(txtGrfxWarning);

        add(jScrollPane1, "cell 0 0,alignx center,aligny bottom");
        
        btnMoreInformation = new JButton("More Information");
        add(btnMoreInformation, "flowx,cell 0 1,alignx center");
        btnMoreInformation.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					URI uri = new URI("http://worldwindcentral.com/wiki/Video_Card_Compatibility");
					Help.openPage(uri);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Alert.error("Error", "Help page cannot be found.");
				}
			}
        	
        });

        btnRetry.setText("Re-test 3D Graphics");
        btnRetry.setAlignmentX(0.5F);
        btnRetry.setAlignmentY(1.0F);
        add(btnRetry, "cell 0 1,alignx center,aligny bottom");
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnRetry;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTextPane txtGrfxWarning;
    private JButton btnMoreInformation;
    // End of variables declaration//GEN-END:variables
	
    
    @Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnRetry)
		{
			App.prefs.setBooleanPref(PrefKey.OPENGL_FAILED, false);
			if(mapPanel==null)
			{
				final GISFrame map = new GISFrame(true);
				map.setVisible(true);
				
	            map.wwMapPanel.getWwd().addRenderingExceptionListener(new RenderingExceptionListener()
	            {
	                public void exceptionThrown(Throwable t)
	                {
	                	GrfxWarning.this.fireActionEvent(new ActionEvent(btnRetry, 1001, "fail"));
	                	map.dispose();
	                	Alert.message(I18n.getText("preferences.testFailed"), I18n.getText("preferences.grfxTestFailed"));
	                	return;
	                }
	                
	            });
	            
	            this.fireActionEvent(new ActionEvent(btnRetry, 1001, "pass"));
				
			}
			else
			{
				GISPanel wwMapPanel = new GISPanel(new Dimension(300,300), false, TridasMarkerLayerBuilder.getMarkerLayerForAllSites());
				mapPanel.setLayout(new BorderLayout());
				mapPanel.add(wwMapPanel, BorderLayout.CENTER);
			}
		}

		
	}
    
	public void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}
    
    public void removeTableModelListener(ActionListener l) {
    	listenerList.remove(ActionListener.class, l);
    }
    
    public void fireActionEvent(ActionEvent e) {
    	
    	Object[] listeners = listenerList.getListenerList();
    	for (int i = listeners.length-2; i>=0; i-=2) {
    	    if (listeners[i]==ActionListener.class) {
    		((ActionListener)listeners[i+1]).actionPerformed(e);
    	    }
    	}
    }
    
    
}
