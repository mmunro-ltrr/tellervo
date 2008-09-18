package edu.cornell.dendro.corina.editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import edu.cornell.dendro.corina.gui.newui.BaseEditorPanel;
import edu.cornell.dendro.corina.gui.newui.EditorPanelFactory;
import edu.cornell.dendro.corina.gui.newui.MeasurementModifyPanel;
import edu.cornell.dendro.corina.gui.newui.WizardChildMonitor;
import edu.cornell.dendro.corina.sample.Sample;
import edu.cornell.dendro.corina.site.GenericIntermediateObject;

public class IntermediateEditorDialog extends JDialog implements WizardChildMonitor {
	private BaseEditorPanel<GenericIntermediateObject> editor;
	private Sample sample;
	private String metaKey;
	
	private JButton okButton;
	
	public IntermediateEditorDialog(Frame parent, Sample s, String metaKey) {
		super(parent, true);
		
		this.sample = s;
		this.metaKey = metaKey;

		initialize();
	}

	private void initialize() {
		// set up our editor panel
		if(metaKey == null) {
			editor = new MeasurementModifyPanel(sample);
		}
		else {
			GenericIntermediateObject obj = (GenericIntermediateObject) sample.getMeta(metaKey);
			editor = (BaseEditorPanel<GenericIntermediateObject>) EditorPanelFactory.createPanelForClass(obj.getClass());
			editor.setDefaultsFrom(obj); // copy defaults
			editor.setUpdateObject(obj); // update this object
			editor.populate("Lab Prefix-");
		}
		
		// now, design the dialog
		JPanel content = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		buttonPanel.setAlignmentX(RIGHT_ALIGNMENT);
		
		okButton = new JButton("Apply");
		JButton cancelButton = new JButton("Cancel");
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		content.add(editor, BorderLayout.CENTER);
		content.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(content);
		
		pack();
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editor.commit();
				
				if(editor.didSucceed()) {
					if(metaKey != null)
						sample.setMeta(metaKey, editor.getNewObject());
					sample.fireSampleMetadataChanged();
					dispose();
				}
			}
		});

		// cancel just quits.
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		editor.setWizardToNotify(this);
		notifyChildFormStateChanged();
	}

	public void notifyChildFormStateChanged() {
		boolean readyToGo = editor.isFormValidated();
		
		okButton.setEnabled(readyToGo);
	}
}
