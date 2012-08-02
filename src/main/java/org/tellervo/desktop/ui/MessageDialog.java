package org.tellervo.desktop.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MessageDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final String title;
	private final String message;
	private final DialogType type;
	private Window parent;
	
	enum DialogType {
		INFO,
		WARNING,
		ERROR
	}

	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	public MessageDialog(String title, String message) {
		this.title = title;
		this.message = message;
		this.type = DialogType.INFO;
		setupGui();
	}
	
	/**
	 * Create the dialog.
	 */
	public MessageDialog(String title, String message, DialogType type) {
		this.title = title;
		this.message = message;
		this.type = type;
		setupGui();
	}
	
	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	public MessageDialog(Window parent, String title, String message) {
		this.title = title;
		this.message = message;
		this.type = DialogType.INFO;
		this.parent = parent;
		setupGui();
	}
	
	/**
	 * Create the dialog.
	 */
	public MessageDialog(Window parent, String title, String message, DialogType type) {
		this.title = title;
		this.message = message;
		this.type = type;
		this.parent = parent;
		setupGui();
	}
	
	private void setupGui()
	{
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setMinimumSize(new Dimension(360,175));
		//setBounds(100, 100, 386, 310);
		this.setIconImage(Builder.getApplicationIcon());
		
		this.setTitle(title);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[70px:70:70][grow]", "[64:64:64,grow]"));
		{
			JLabel lblIcon = new JLabel("");
			
			if(type.equals(DialogType.INFO))
			{
				lblIcon.setIcon(Builder.getIcon("info.png", 64));
			}
			if(type.equals(DialogType.ERROR))
			{
				lblIcon.setIcon(Builder.getIcon("error.png", 64));
			}
			if(type.equals(DialogType.WARNING))
			{
				lblIcon.setIcon(Builder.getIcon("warn.png", 64));
			}
			contentPanel.add(lblIcon, "cell 0 0, hidemode 2");
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(null);
			contentPanel.add(panel, "cell 1 0,grow");
			panel.setLayout(new MigLayout("", "[110px,grow]", "[25px,grow]"));
			{
				JTextArea textArea = new JTextArea();
				textArea.setText(message);
				textArea.setEditable(false);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				textArea.setFont(new Font("Dialog", Font.PLAIN, 12));
				textArea.setEditable(false);
				textArea.setBorder(new EmptyBorder(5, 5, 5, 5));
				textArea.setBackground((Color) null);
				
				panel.add(textArea, "cell 0 0,growx,wmin 10");
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
						
					}
					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}

		pack();
		this.setLocationRelativeTo(parent);

	}


}