package edu.cornell.dendro.corina.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.cornell.dendro.corina.gui.FileDialog;
import edu.cornell.dendro.corina.gui.UserCancelledException;
import edu.cornell.dendro.corina.sample.ElementList;
import edu.cornell.dendro.corina.ui.I18n;

public class GraphElementsPanel extends JPanel {
	public GraphElementsPanel(List samples, GraphWindow gwindow) {
		super(new BorderLayout());
		
		this.window = gwindow;
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		
		listmodel = new DefaultListModel();
		list = new JList(listmodel);
		list.setBorder(BorderFactory.createTitledBorder("Elements list"));
		topPanel.add(list);
		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				window.listSelectionChanged();

				boolean enabled = list.getSelectedIndex() < 0 ? false : true;

				removeButton.setEnabled(enabled);
				colorselect.setEnabled(enabled);
			}
		});
		
		colorselect = new ColorComboBox();
		colorselect.setBorder(BorderFactory.createTitledBorder("Selected Element Color"));
		topPanel.add(colorselect);
		
		colorselect.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				window.setActiveColor(colorselect.getSelectedColor());
			}
		});
		
		add(topPanel, BorderLayout.NORTH);
		loadSamples(samples);
				
	    JPanel addButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    add(addButtonContainer, BorderLayout.CENTER);
	    
	    addButton = new JButton("Add...");
	    addButtonContainer.add(addButton);
	    addButton.addActionListener(new AbstractAction() {
	    	public void actionPerformed(ActionEvent ae) {
	    		ElementList ss = null;
	    		try {
	    			ss = FileDialog.showMulti(I18n.getText("plot"));
	    		} catch (UserCancelledException uce) {
	    			return;
	    		}

	    		window.add(ss);
	    	}
	    });

	    removeButton = new JButton("Remove");
	    removeButton.setEnabled(false);
	    addButtonContainer.add(removeButton);
	    removeButton.addActionListener(new AbstractAction() {
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
	
	public void loadSamples(List samples) {
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
	private ColorComboBox colorselect;
}
