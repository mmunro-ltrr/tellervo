/**
 * Created at Oct 1, 2010, 4:19:36 PM
 */
package org.tellervo.desktop.components.table;

import org.tellervo.desktop.tridasv2.ui.DefaultCellRendererEx;
import org.tridas.schema.TridasElement;

import com.l2fprod.common.swing.renderer.DefaultCellRenderer;


/**
 * @author Daniel
 *
 */
public class TridasElementRenderer extends DefaultCellRendererEx {

	private static final long serialVersionUID = 1L;

	/**
	 * @see com.l2fprod.common.swing.renderer.DefaultCellRenderer#convertToString(java.lang.Object)
	 */
	@Override
	protected String convertToString(Object argValue) {
		TridasElement e = (TridasElement) argValue;
		if(argValue == null){
			return "";
		}
		return e.getTitle();
	}
}
