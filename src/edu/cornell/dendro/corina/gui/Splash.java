// Copyright (c) 2004-2005 Aaron Hamid.  All rights reserved.
// See license in COPYING.txt distributed with this file and available online at http://www.gnu.org/licenses/gpl.txt

package edu.cornell.dendro.corina.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import edu.cornell.dendro.corina.gui.ProgressMeter.ProgressEvent;
import edu.cornell.dendro.corina.util.Center;

/**
 * @author Aaron
 */
public class Splash extends JDialog implements ProgressMeter.ProgressListener {
	private BufferedImage img;
	private JProgressBar progress = new JProgressBar();
	private JLabel label = new JLabel();
	protected Container progressPanel;

	public Splash() {
		this(null, null);
	}

	public Splash(String title) {
		this(title, null);
	}

	public Splash(BufferedImage img) {
		this(null, img);
	}

	public Splash(String title, BufferedImage img) {
		
		setUndecorated(true);

		// make the content pane
		ImagePanel content = new ImagePanel(img, ImagePanel.ACTUAL);

		//content.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		//content.setLayout(new BorderLayout());
		//content.setBackground(Color.white);
	
		setContentPane(content);
				
		progressPanel = new Container();
		progressPanel.setLayout(new GridLayout(2, 1));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		progressPanel.add(label);	
		progressPanel.add(progress);
		label.setVisible(false);
		
		
		
		getContentPane().add(progressPanel, BorderLayout.SOUTH);	
		pack();
				
		/* really? this behavior sucks! 
		window.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent me) {
				window.dispose();
			}

			public void mousePressed(MouseEvent me) { 
			}

			public void mouseReleased(MouseEvent me) {
			}

			public void mouseEntered(MouseEvent me) { 
			}

			public void mouseExited(MouseEvent me) { 
			}
		});
		*/
	}

	public void closeProgress(ProgressEvent event) {
		dispose();
	}

	public void displayProgress(ProgressEvent event) {
		Center.center(this);
		stateChanged(event);
		setVisible(true);
		toFront();
	}

	public void stateChanged(ProgressEvent event) {
		String newnote = event.getNote();
		if (newnote != null) {
			if (!newnote.equals(label.getText())) {
				label.setText(newnote);
			}
			if (!label.isVisible())
				label.setVisible(true);
		} else {
			if (label.isVisible())
				label.setVisible(false);
		}
		progress.setMinimum(event.getMinimum());
		progress.setMaximum(event.getMaximum());
		progress.setValue(event.getValue());
	}
}