/*******************************************************************************
 * Copyright (C) 2010 Lucas Madar and Peter Brewer
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
 *     Lucas Madar
 *     Peter Brewer
 ******************************************************************************/
package edu.cornell.dendro.corina.util.labels.ui;

import javax.swing.JDialog;

import edu.cornell.dendro.corina.ui.Builder;

public class PrintingDialog extends JDialog{

	private static final long serialVersionUID = 1428573868641877953L;
	private PrintSettings lp;
		
    public PrintingDialog(java.awt.Frame parent, boolean modal, PrintSettings.PrintType lt) {
        super(parent, modal);
        lp = new PrintSettings(lt, this);
        
        this.setTitle("Print " + lt.toString().toLowerCase() );
        setIconImage(Builder.getApplicationIcon());
		this.setContentPane(lp);
		this.pack();
    }
		
	public static void boxLabelDialog()
	{
		PrintingDialog.main(PrintSettings.PrintType.BOX);
	}
	
	public static void sampleLabelDialog()
	{
		PrintingDialog.main(PrintSettings.PrintType.SAMPLE);
	}
	
	public static void proSheetPrintingDialog()
	{
		PrintingDialog.main(PrintSettings.PrintType.PROSHEET);
		
	}
	
    public static void main(final PrintSettings.PrintType lt) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	PrintingDialog dialog = new PrintingDialog(null, true, lt);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {

                    }
                });
                
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
               
            }
        });
    }

}
