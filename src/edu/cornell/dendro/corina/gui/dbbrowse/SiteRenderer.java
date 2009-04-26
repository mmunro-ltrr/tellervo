package edu.cornell.dendro.corina.gui.dbbrowse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import edu.cornell.dendro.corina.tridasv2.TridasObjectEx;

/**
 * A quick and dirty class to render stars in a combo box
 */
public class SiteRenderer implements ListCellRenderer {
	public SiteRenderer() {
	}
	
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		JPanel c = new JPanel();
		
		if(isSelected)
			c.setBackground(list.getSelectionBackground());
		else
			c.setBackground(index % 2 == 0 ? DBBrowser.ODD_ROW_COLOR
					: Color.white);	

		if(value instanceof TridasObjectEx) {
			TridasObjectEx site = (TridasObjectEx) value;
			
			JLabel lblCode = new JLabel(site.getLabCode());
			JLabel lblName = new JLabel(site.getTitle());
			
			Font font = lblCode.getFont();
			lblCode.setFont(font.deriveFont(Font.BOLD));
			lblName.setFont(font.deriveFont(font.getSize() - 2.0f));
			
			BoxLayout layout = new BoxLayout(c, BoxLayout.Y_AXIS);
			c.setLayout(layout);
			
			Integer seriesCount = site.getSeriesCount();
			if(seriesCount != null) {
				String countStr = seriesCount + "/" + site.getChildSeriesCount();
				Font countFont = font.deriveFont(font.getSize() - 1.0f);

				lblCode.setText(lblCode.getText() + "   " + countStr);
			}
			
			c.add(lblCode);
			c.add(lblName);		
		} else if(value instanceof String) {
			JLabel lblCode = new JLabel((String) value);
			JLabel lblName = new JLabel((String) value);
			
			// yellow background
			if(!isSelected)
				c.setBackground(new Color(255, 255, 200));
			
			Font font = lblCode.getFont();
			lblCode.setFont(font.deriveFont(Font.BOLD));
			lblName.setFont(font.deriveFont(font.getSize() - 2.0f));
			
			BoxLayout layout = new BoxLayout(c, BoxLayout.Y_AXIS);
			c.setLayout(layout);
			
			c.add(lblCode);
			c.add(lblName);
		}
		
		return c;
	}
}
