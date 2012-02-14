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
package org.tellervo.desktop.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.ValidationEventCollector;

import org.tellervo.desktop.core.App;
import org.tellervo.desktop.gui.dbbrowse.DBBrowser;
import org.tellervo.desktop.io.view.ImportView;
import org.tellervo.desktop.prefs.Prefs.PrefKey;
import org.tellervo.desktop.sample.Element;
import org.tellervo.desktop.sample.ElementList;
import org.tellervo.desktop.sample.Sample;
import org.tellervo.desktop.ui.Alert;
import org.tellervo.desktop.util.openrecent.OpenRecent;
import org.tellervo.desktop.util.openrecent.SeriesDescriptor;
import org.tridas.io.AbstractDendroFileReader;
import org.tridas.io.DendroFileFilter;
import org.tridas.io.TridasIO;
import org.tridas.io.exceptions.InvalidDendroFileException;
import org.tridas.io.formats.tridas.TridasWriter;
import org.tridas.io.util.TridasUtils;
import org.tridas.schema.TridasMeasurementSeries;
import org.tridas.schema.TridasTridas;


public class GraphElementsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public GraphElementsPanel(List<Graph> samples, GraphWindow gwindow) {
		super(new BorderLayout());
		
		this.window = gwindow;
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		
		// Create panel to hold legend
		JPanel legendPanel = new JPanel();
		legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
		legendPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Components"));
		topPanel.add(legendPanel);
		
		// Create list of series
		listmodel = new DefaultListModel();
		list = new JList(listmodel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		legendPanel.add(list);
		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				window.listSelectionChanged();

				boolean enabled = list.getSelectedIndex() < 0 ? false : true;

				removeButton.setEnabled(enabled);
				colorselect.setEnabled(enabled);
			}
		});
		
		JPanel colorSelectorPanel = new JPanel();
		colorSelectorPanel.setLayout(new BoxLayout(colorSelectorPanel, BoxLayout.X_AXIS));
		JLabel lblColor = new JLabel();
		lblColor.setText("Selected color:");
		colorselect = new ColorComboBox();
		colorSelectorPanel.add(lblColor);
		colorSelectorPanel.add(colorselect);
		legendPanel.add(colorSelectorPanel);
		
		colorselect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				window.setActiveColor(colorselect.getSelectedColor());
			}
		});
		
		add(topPanel, BorderLayout.NORTH);
		loadSamples(samples);
				
	    JPanel addButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    add(addButtonContainer, BorderLayout.CENTER);
	    
	    addButton = new JButton("Add series...");
	    addButtonContainer.add(addButton);
	    addButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		Window ancestor = SwingUtilities.getWindowAncestor(GraphElementsPanel.this);
	    		
				DBBrowser browser; 
				
				if(ancestor instanceof Dialog)
					browser = new DBBrowser((Dialog) ancestor, true, true);
				else if(ancestor == null || window instanceof Frame)
					browser = new DBBrowser((Frame) ancestor, true, true);
				else
					throw new IllegalStateException("GraphElementsPanel has no real parents!");
	    		
	    		browser.setVisible(true);
	    		
	    		if(browser.getReturnStatus() == DBBrowser.RET_OK) {
	    			ElementList ss = browser.getSelectedElements();
	    			
	    			for(Element e : ss) {
	    				// load it
	    				Sample s;
	    				try {
	    					s = e.load();
	    				} catch (IOException ioe) {
	    					Alert.error("Error Loading Sample",
	    							"Can't open this file: " + ioe.getMessage());
	    					continue;
	    				}

	    				OpenRecent.sampleOpened(new SeriesDescriptor(s));
	    				
	    				// add it to graph
	    				window.add(s);
	    			}
	    		}	
	    	}
	    });
	    
	

	    removeButton = new JButton("Remove");
	    removeButton.setEnabled(false);
	    addButtonContainer.add(removeButton);
	    removeButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		window.remove(getSelectedIndex());
	    	}
	    });
	}
	
	public void setSelectedIndex(int idx) {
		list.setSelectedIndex(idx);
	}
	
	public void setColor(Color c) {
		colorselect.setColor(c);
	}
	
	public int getSelectedIndex() {
		return list.getSelectedIndex();
	}
	
	public void loadSamples(List<Graph> samples) {
		listmodel.clear();
		for(int i = 0; i < samples.size(); i++) {
			Graph cg = (Graph) samples.get(i);
			
			listmodel.addElement(cg.graph.toString());
		}
		list.revalidate();
	}
	
	private DefaultListModel listmodel;
	private JList list;
	private GraphWindow window;
	private JButton addButton;
	private JButton removeButton;
	private JButton btnAddFile;
	private ColorComboBox colorselect;
}