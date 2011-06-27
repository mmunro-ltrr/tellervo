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
/*
 * LabelPrintingUI.java
 *
 * Created on August 26, 2009, 11:01 AM
 */

package edu.cornell.dendro.corina.util.labels.ui;


/**
 *
 * @author  peterbrewer
 */
public class PrintSettingsUI extends javax.swing.JPanel {

	private static final long serialVersionUID = 3484086245445229624L;

	/** Creates new form LabelPrintingUI */
    public PrintSettingsUI() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnPreview = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        tabContent = new javax.swing.JPanel();
        tabLayout = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();

        btnPreview.setText("Preview");

        btnPrint.setText("Print");

        org.jdesktop.layout.GroupLayout tabLabelsLayout = new org.jdesktop.layout.GroupLayout(tabContent);
        tabContent.setLayout(tabLabelsLayout);
        tabLabelsLayout.setHorizontalGroup(
            tabLabelsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 472, Short.MAX_VALUE)
        );
        tabLabelsLayout.setVerticalGroup(
            tabLabelsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 286, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Content", tabContent);

        org.jdesktop.layout.GroupLayout tabLayoutLayout = new org.jdesktop.layout.GroupLayout(tabLayout);
        tabLayout.setLayout(tabLayoutLayout);
        tabLayoutLayout.setHorizontalGroup(
            tabLayoutLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 472, Short.MAX_VALUE)
        );
        tabLayoutLayout.setVerticalGroup(
            tabLayoutLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 286, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Layout", tabLayout);

        btnCancel.setText("Cancel");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap(239, Short.MAX_VALUE)
                .add(btnCancel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnPreview)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnPrint)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnPrint)
                    .add(btnPreview)
                    .add(btnCancel))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnCancel;
    protected javax.swing.JButton btnPreview;
    protected javax.swing.JButton btnPrint;
    protected javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JPanel tabContent;
    protected javax.swing.JPanel tabLayout;
    // End of variables declaration//GEN-END:variables
    
}
