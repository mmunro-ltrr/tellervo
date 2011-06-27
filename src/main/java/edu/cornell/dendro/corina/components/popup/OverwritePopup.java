/*******************************************************************************
 * Copyright (C) 2011 Peter Brewer
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Peter Brewer
 ******************************************************************************/
package edu.cornell.dendro.corina.components.popup;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import edu.cornell.dendro.corina.ui.I18n;

/**
 * @author Daniel Murphy
 *
 */
@SuppressWarnings("serial")
public class OverwritePopup extends JDialog {

	private final OverwritePopupModel model;
	private JLabel label;
	private JButton btnOverwrite;
	private JButton btnRename;
	private JButton btnIgnore;
	private JCheckBox applyAll;
	
	public OverwritePopup(Frame argParent, OverwritePopupModel argModel, boolean argModal){
		super(argParent, argModal);
		model = argModel;
		initGui();
		linkModel();
		addListeners();
		pack();
	}
	
	private void initGui(){
		
		getContentPane().setLayout(new GridLayout(2, 1));
		setTitle(I18n.getText("popup.overwrite.title"));
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		label = new JLabel();
		panel_1.add(label);
		
		applyAll = new JCheckBox(I18n.getText("popup.overwrite.all"));
		panel_1.add(applyAll, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnOverwrite = new JButton(I18n.getText("popup.overwrite.overwrite"));
		panel.add(btnOverwrite);
		
		btnRename = new JButton(I18n.getText("popup.overwrite.rename"));
		panel.add(btnRename);
		
		btnIgnore = new JButton(I18n.getText("popup.overwrite.ignore"));
		panel.add(btnIgnore);
	}
	
	private void linkModel(){
		label.setText(model.getMessage());
	}
	
	private void addListeners(){
		applyAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent argE) {
				if(applyAll.isSelected()){
					model.setApplyToAll(true);
				}else{
					model.setApplyToAll(false);
				}
			}
		});
		
		btnOverwrite.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent argE) {
				model.setResponse(OverwritePopupModel.OVERWRITE);
				setVisible(false);
			}
		});
		
		btnRename.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent argE) {
				model.setResponse(OverwritePopupModel.RENAME);
				setVisible(false);
			}
		});
		
		btnIgnore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent argE) {
				model.setResponse(OverwritePopupModel.IGNORE);
				setVisible(false);
			}
		});
	}
}
