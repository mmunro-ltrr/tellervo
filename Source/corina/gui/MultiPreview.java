//
// This file is part of Corina.
// 
// Corina is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// Corina is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Corina; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Copyright 2001 Ken Harris <kbh7@cornell.edu>
//

package corina.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import corina.Element;
import corina.Preview;
import corina.Previewable;
import corina.Sample;
import corina.core.App;
import corina.cross.Grid;
import corina.formats.WrongFiletypeException;
import corina.ui.Builder;

public class MultiPreview extends JPanel implements PropertyChangeListener {

    // gui
    private JPanel preview;
    private JButton add, remove;
    private ElementsPanel panel;
    private JFileChooser chooser;

    // data
    private File file;
    private List set;

    // because only FileDialog knows what happens when a file is double-clicked...blah
    public void addClicked() {
	// add to set
	set.add(new Element(file.getPath()));

	// update view
	panel.update();
    }

    public void hook(JFileChooser f) {
	chooser = f;
	chooser.addPropertyChangeListener(this);
    }

    public MultiPreview(List ss) {
	// boilerplate
	set = ss;

	// gui -- layout
	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	setMinimumSize(new Dimension(480, 360)); // ??
	setPreferredSize(new Dimension(400, 200)); // at least...

	// left panel
	JPanel left = new JPanel();
	left.setMaximumSize(new Dimension(180, 640));
	left.setLayout(new BorderLayout(6, 6));
	add(left);

	// left: preview
	preview = new JPanel(new BorderLayout());
	preview.setMinimumSize(new Dimension(240, 100));
	left.add(preview, BorderLayout.CENTER);

	// left: button panel
	JPanel buttons = new JPanel();
	buttons.setLayout(new GridLayout(0, 1, 6, 6));
	buttons.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6)); // top-left-bottom-right
	left.add(buttons, BorderLayout.SOUTH);

	// left: buttons (add)
        add = Builder.makeButton("add");
	if (!App.platform.isMac())
	    add.setIcon(Builder.getIcon("Forward16.gif"));
	add.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent ae) {
		    // add to set
		    set.add(new Element(file.getPath())); // NullPointerException if no file selected!

		    // update view
		    panel.update();
		}
	    });
	buttons.add(add);

	// left: buttons (remove)
        remove = Builder.makeButton("remove");
	if (!App.platform.isMac())
	    remove.setIcon(Builder.getIcon("Back16.gif"));
	remove.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent ae) {
		    panel.removeSelectedRows();
		}
	    });
	buttons.add(remove);

	// left: buttons (ok)
	JButton okay = Builder.makeButton("ok");
	okay.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent ae) {
		    // if (set.size() == 0) // okay with zero samples is like cancel
		    // set = null;
		    chooser.cancelSelection();
		}
	    });
	buttons.add(okay);

	// left: buttons (cancel)
	JButton cancel = Builder.makeButton("cancel");
	cancel.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent ae) {
		    set = null;
		    chooser.cancelSelection();
		}
	    });
	buttons.add(cancel);

	// right: elements panel (table)
	panel = new ElementsPanel(set);
	add(panel);
    }

    // PropertyChangeListener helper
    // (REFACTOR: use SamplePreview here!)
    private void loadSample() {
	// no file?
        if (file == null)
            return;
 
	try {
	    Previewable s=null;

	    // new: loop to find a Previewable
	    try {
		s = new Grid(file.getPath());
	    } catch (WrongFiletypeException wfte) {
		s = new Sample(file.getPath());
	    } // but can't string catches here ... darn

	    // get preview, and show it.
	    showPreview(s.getPreview());

	} catch (WrongFiletypeException wfte) {
	    showPreview(new Preview.NotDendroDataPreview());

	} catch (IOException ioe) {
	    showPreview(new Preview.ErrorLoadingPreview(ioe));
	}
    }

    // implements PropertyChangeListener
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();
        if (prop.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
            file = (File) e.getNewValue();
            if (isShowing()) {
                loadSample();
                repaint();
            }
        }
    }

    private void showPreview(Preview p) {
	// remove old value, and put new preview component up.
	if (preview.getComponentCount() > 0)
	    preview.remove(0);
	PreviewComponent pc = new PreviewComponent(p);
	preview.add(pc, BorderLayout.CENTER);

	// beat swing with a stick until it repaints me.
	preview.invalidate();
	preview.validate();
    }

    // get the result
    public List getSamples() {
	return set;
    }
}
