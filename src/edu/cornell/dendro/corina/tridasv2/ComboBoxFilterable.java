package edu.cornell.dendro.corina.tridasv2;

import java.awt.Color;
import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.tridas.schema.ControlledVoc;

import edu.cornell.dendro.corina.ui.FilterableComboBoxModel;
import edu.cornell.dendro.corina.ui.FocusablePopup;

public class ComboBoxFilterable extends JComboBox {

	/** The popup that shows a search box */
	private FocusablePopup searchPopup;
	/** The search panel that holds the text field */
	private JPanel searchPanel;
	/** The text field for entering in search data */
	private ForcableJTextField searchField;

	private FilterableComboBoxModel model;
	private ContainsFilter filter;
	
	boolean isPopupShowing;
	boolean isComboPopupShowing;

	public ComboBoxFilterable(Object[] data) {
		super();
	
		model = new FilterableComboBoxModel(Arrays.asList(data));
		setModel(model);
		
		filter = new ContainsFilter();

		isPopupShowing = isComboPopupShowing = false;

		// keep track of when the combo box popup dropdown menu exists
		// get rid of the search menu when it goes away
		addPopupMenuListener(new PopupMenuListener() {
		      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		    	  isComboPopupShowing = false;
		    	  
		    	  if(isPopupShowing) {
		    		  cleanupPopup(false);
		    	  }
		      }
		      public void popupMenuCanceled(PopupMenuEvent e) {
		    	  isComboPopupShowing = false;
		      }
		      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		    	  isComboPopupShowing = true;
		      }
	    });
		
		// Bring up the search dialog when someone presses a key
		addKeyListener(new KeyAdapter() {
			
			public void keyTyped(KeyEvent e) {	
				
				// ignore it if the combo popup menu isn't showing
				if(!isComboPopupShowing)
					ComboBoxFilterable.this.showPopup();
				
				// is control or alt pressed? Ignore anything, then
				if((e.getModifiers() & (KeyEvent.CTRL_DOWN_MASK|KeyEvent.ALT_DOWN_MASK)) != 0)
					return;

				// close popup on escape
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if(isPopupShowing) {
						cleanupPopup(true);
						e.consume();
					}
					return;
				}
				
				char c = e.getKeyChar();
				
				// ignore non-alphanumeric keys
				if(!Character.isLetterOrDigit(c) && !(c == ' '))
					return;
				
				if(!isPopupShowing) {
					Point p;
				
					try {
						p = ComboBoxFilterable.this.getLocationOnScreen();
					} catch (IllegalComponentStateException icse) {
						// component isn't on screen, shouldn't happen??
						return;
					}

					initPopup(p);				
					searchField.setText("");
					searchPopup.show();
					searchField.requestFocus(true);
					isPopupShowing = true;
				}
				
				searchField.forceProcessKeyEvent(e);
			}
		});
	}
	
	private void initPopup(Point p) {
		if(searchPopup != null) {
			isPopupShowing = false;
			searchPopup.hide();
		}
		
		if(searchPanel == null)
			initSearchPanel();
		
		searchPopup = FocusablePopup.getPopup(this, 
				searchPanel, p.x, p.y - (20 + searchPanel.getPreferredSize().height));
	}
	
	/**
	 * Remove the popup from the screen
	 * @param resetFilter remove a filter, if true
	 */
	private void cleanupPopup(boolean resetFilter) {
		if(isPopupShowing) {
			searchPopup.hide();
			isPopupShowing = false;
		}
		
		if(resetFilter) {
			filter.setFilterText("");
			model.setFilter(filter);
		}
	}
	
	/**
	 * Create the JPanel that our search text box resides in
	 */
	private void initSearchPanel() {
		searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
		searchPanel.setBorder(BorderFactory.createLineBorder(Color.RED.darker().darker(), 1));
		
		searchPanel.add(Box.createHorizontalStrut(4));
		searchPanel.add(new JLabel("Search: "));

		searchField = new ForcableJTextField(20);
		searchPanel.add(searchField);
		
		// notify when search field contents change
		searchField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				searchFieldChanged();
			}

			public void insertUpdate(DocumentEvent e) {
				searchFieldChanged();
			}

			public void removeUpdate(DocumentEvent e) {
				searchFieldChanged();
			}
		});
		
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// remove the search box if someone presses escape
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cleanupPopup(true);
					e.consume();
					return;
				}
				
				// forward enter key onto combo box
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					ComboBoxFilterable.this.processKeyEvent(e);
					return;
				}
			}
		});
	}

	/**
	 * Called when the text in the search field changes
	 */
	private void searchFieldChanged() {
		filter.setFilterText(searchField.getText());
		model.setFilter(filter);
	}

	/**
	 * Subclass of JTextField that lets us force keypresses directly
	 */
	private static class ForcableJTextField extends JTextField {
		public ForcableJTextField(int size) {
			super(size);
		}

		public void forceProcessKeyEvent(KeyEvent k) {
			super.processKeyEvent(k);
		}
	}

	/**
	 * A filter that checks for string contents, case insensitive
	 */
	private static class ContainsFilter implements FilterableComboBoxModel.Filter {
		private String filterText = "";
		
		public void setFilterText(String filterText) {
			this.filterText = filterText.toLowerCase();
		}
		
		public boolean accept(Object obj) {
			// no filter? everything's good!
			if(filterText.length() == 0)
				return true;

			// skip not present things
			if(obj instanceof NotPresent)
				return false;
			
			String val = objectToString(obj).toLowerCase();
			return val.contains(filterText);
		}		
		
		private String objectToString(Object o) {
			// durr...
			if(o instanceof String) {
				return (String) o;
			}
			else if(o.getClass().isEnum()) {
				try {
					Method method = o.getClass().getMethod("value", (Class<?>[]) null);
					return method.invoke(o, new Object[] {}).toString();
				} catch (Exception e) {
					// fall through
				}
			}
			else if(o instanceof ControlledVoc) {
				return ((ControlledVoc)o).getNormal();
			}
			
			return o.toString();
		}
	}
}
