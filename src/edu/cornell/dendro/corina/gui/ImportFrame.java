/**
 * 
 */
package edu.cornell.dendro.corina.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.cornell.dendro.corina.editor.Editor;
import edu.cornell.dendro.corina.sample.Sample;
import edu.cornell.dendro.corina.sample.SampleAdapter;
import edu.cornell.dendro.corina.sample.SampleEvent;
import edu.cornell.dendro.corina.tridasv2.ui.TridasImportPanel;
import edu.cornell.dendro.corina.util.Center;
import edu.cornell.dendro.corina.util.LegacySampleExtractor;

/**
 * @author Lucas Madar
 *
 */
public class ImportFrame extends XFrame {
	private static final long serialVersionUID = 1L;

	private LegacySampleExtractor extractor;
	private TridasImportPanel importer;

	private JButton okButton;
	
	private Sample s;
	
	/**
	 * @param owner
	 */
	public ImportFrame(Sample s) {
		super();
	
		this.s = s;
		
		extractor = new LegacySampleExtractor(s);
		importer = new TridasImportPanel(s, extractor);
		
		setTitle("Importing " + s.getDisplayTitle());
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout());
		add(importer, BorderLayout.CENTER);
		add(makeButtonPanel(), BorderLayout.SOUTH);
		
		pack();
		Center.center(this);
		
		okButton.setEnabled(false);
		
		s.addSampleListener(new SampleAdapter() {
			@Override
			public void sampleMetadataChanged(SampleEvent e) {				
				if(importer.importCompleted())
					okButton.setEnabled(true);
				else
					okButton.setEnabled(false);
			}			
		});
	}
	
	private JPanel makeButtonPanel() {
		JButton cancelButton = new JButton("Cancel");
		okButton = new JButton("Import");
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Editor(s).setVisible(true);
			}
		});
		
		return Layout.buttonLayout(null, cancelButton, okButton);
	}
}
