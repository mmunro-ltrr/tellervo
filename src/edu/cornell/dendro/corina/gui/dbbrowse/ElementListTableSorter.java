package edu.cornell.dendro.corina.gui.dbbrowse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JTable;

import edu.cornell.dendro.corina.formats.Metadata;
import edu.cornell.dendro.corina.gui.SortedHeaderArrowRenderer;
import edu.cornell.dendro.corina.sample.BaseSample;
import edu.cornell.dendro.corina.sample.Element;
import edu.cornell.dendro.corina.tridasv2.LabCode;

public class ElementListTableSorter extends MouseAdapter {
	private ElementListTableModel model;
	private JTable table;
	private SortedHeaderArrowRenderer headerRenderer;
	
	private int lastSortedCol = -1;
	
	public ElementListTableSorter(ElementListTableModel model, JTable table) {
		this.table = table;
		this.model = model;	
		
		headerRenderer = new SortedHeaderArrowRenderer(table, null);
		table.getTableHeader().setDefaultRenderer(headerRenderer);
	}
	
	public void setUnsorted() {
		lastSortedCol = -1;
		
		// remove any arrow we have lying around
		table.getTableHeader().repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		int col = table.getColumnModel().getColumnIndexAtX(me.getX());
		
		// sanity check
		if(col < 0)
			return;

		sortOnColumn(col, false);
	}
	
	/**
	 * Force a re-sort
	 */
	public void reSort() {
		// nothing to sort by :(
		if(lastSortedCol < 0)
			return;
		
		int col = lastSortedCol;
		boolean reverse = headerRenderer.isReversed();
		
		lastSortedCol = -1;
		sortOnColumn(col, reverse);
	}
	
	/**
	 * Force a sort...
	 * @param col the column to sort on
	 * @param reverse force reverse
	 */
	public void sortOnColumn(int col, boolean reverse) {
		if(col == lastSortedCol) {
			Collections.reverse(model.getElements());
			headerRenderer.setReversed(!headerRenderer.isReversed());
		}
		else {
			Collections.sort(model.getElements(), new ElementListTableColumnComparator(model, col));
			
			headerRenderer.setSortColumn(col);
			headerRenderer.setReversed(false);
			
			lastSortedCol = col;
			
			if(reverse)
				Collections.reverse(model.getElements());
		}
	
		
		// notify the model and repaint the header
		model.fireTableDataChanged();
		table.getTableHeader().repaint();
		
	}
		
	private class ElementListTableColumnComparator implements Comparator<Element> {
		private int column;
		private ElementListTableModel model;
		
		public ElementListTableColumnComparator(ElementListTableModel model, int column) {
			this.column = column;
			this.model = model;
		}
		
		private Object getValue(Element e) {
			// special case for lab code...
			if(column == 0) {
				try {
					BaseSample bs = e.loadBasic();
					LabCode code = bs.getMeta(Metadata.LABCODE, LabCode.class);
					
					if(code != null)
						return code;
					
					// fall through to default below...
				}
				catch (Exception ex) {
					// doesn't happen!
					return null;
				}
			}
			
			return model.getColumnValueForElement(e, column);
		}
		
		@SuppressWarnings("unchecked")
		public int compare(Element e1, Element e2) {
			Object o1 = getValue(e1);
			Object o2 = getValue(e2);

			// nicely handle nulls
			if(o1 == null && o2 == null)
				return 0;
			if(o1 == null)
				return -1;
			if(o2 == null)
				return +1;
			
			// try and have it compare itself
			if(o1 instanceof Comparable) 
				return ((Comparable) o1).compareTo(o2);
			
			// fallback on lame string compares
			return o1.toString().compareToIgnoreCase(o2.toString());
		}
	}
}
