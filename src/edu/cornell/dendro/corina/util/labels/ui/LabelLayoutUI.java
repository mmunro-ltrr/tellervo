
/*
 * LabelLayoutUI.java
 *
 * Created on August 26, 2009, 11:05 AM
 */


package edu.cornell.dendro.corina.util.labels.ui;

/**
 *
 * @author  peterbrewer
 */
public class LabelLayoutUI extends javax.swing.JPanel {
    
    /** Creates new form LabelLayoutUI */
    public LabelLayoutUI() {
        initComponents();
        jLabel1.setEnabled(false);
        cboLabelName.setEnabled(false);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cboLabelName = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        panelLabelDetails = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        spnLabelGap = new javax.swing.JSpinner();
        spnLabelWidth = new javax.swing.JSpinner();
        spnLabelHeight = new javax.swing.JSpinner();
        spnSideMargin = new javax.swing.JSpinner();
        spnTopMargin = new javax.swing.JSpinner();
        cboPageSize = new javax.swing.JComboBox();

        cboLabelName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[Custom]", "Avery 6579", "Avery 6575" }));

        jLabel1.setText("Label name:");

        panelLabelDetails.setEnabled(false);

        jLabel2.setText("Page size:");
        jLabel2.setEnabled(false);

        jLabel3.setText("Top margin:");
        jLabel3.setEnabled(false);

        jLabel4.setText("Side margin:");
        jLabel4.setEnabled(false);

        jLabel5.setText("Label height:");
        jLabel5.setEnabled(false);

        jLabel6.setText("Label width:");
        jLabel6.setEnabled(false);

        jLabel7.setText("Label gap:");
        jLabel7.setEnabled(false);

        spnLabelGap.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(15.0f), Float.valueOf(0.0f), Float.valueOf(50.0f), Float.valueOf(0.1f)));
        spnLabelGap.setEnabled(false);

        spnLabelWidth.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(15.0f), Float.valueOf(0.0f), Float.valueOf(50.0f), Float.valueOf(0.1f)));
        spnLabelWidth.setEnabled(false);

        spnLabelHeight.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(15.0f), Float.valueOf(0.0f), Float.valueOf(50.0f), Float.valueOf(0.1f)));
        spnLabelHeight.setEnabled(false);

        spnSideMargin.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(15.0f), Float.valueOf(0.0f), Float.valueOf(50.0f), Float.valueOf(0.1f)));
        spnSideMargin.setEnabled(false);

        spnTopMargin.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(15.0f), Float.valueOf(0.0f), Float.valueOf(50.0f), Float.valueOf(0.1f)));
        spnTopMargin.setEnabled(false);

        cboPageSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Letter", "A4" }));
        cboPageSize.setEnabled(false);

        org.jdesktop.layout.GroupLayout panelLabelDetailsLayout = new org.jdesktop.layout.GroupLayout(panelLabelDetails);
        panelLabelDetails.setLayout(panelLabelDetailsLayout);
        panelLabelDetailsLayout.setHorizontalGroup(
            panelLabelDetailsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelLabelDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelLabelDetailsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(jLabel3)
                    .add(jLabel4))
                .add(33, 33, 33)
                .add(panelLabelDetailsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, spnSideMargin)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, spnTopMargin)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, cboPageSize, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(18, 18, 18)
                .add(panelLabelDetailsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel5)
                    .add(jLabel6)
                    .add(jLabel7))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelLabelDetailsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, panelLabelDetailsLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(spnLabelGap, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, spnLabelWidth, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .add(spnLabelHeight, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelLabelDetailsLayout.setVerticalGroup(
            panelLabelDetailsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelLabelDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelLabelDetailsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(cboPageSize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5)
                    .add(spnLabelHeight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelLabelDetailsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(spnTopMargin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel6)
                    .add(spnLabelWidth, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelLabelDetailsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(spnSideMargin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7)
                    .add(spnLabelGap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .add(38, 38, 38)
                .add(cboLabelName, 0, 256, Short.MAX_VALUE)
                .addContainerGap())
            .add(panelLabelDetails, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(20, 20, 20)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(cboLabelName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelLabelDetails, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JComboBox cboLabelName;
    protected javax.swing.JComboBox cboPageSize;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JLabel jLabel2;
    protected javax.swing.JLabel jLabel3;
    protected javax.swing.JLabel jLabel4;
    protected javax.swing.JLabel jLabel5;
    protected javax.swing.JLabel jLabel6;
    protected javax.swing.JLabel jLabel7;
    protected javax.swing.JPanel panelLabelDetails;
    protected javax.swing.JSpinner spnLabelGap;
    protected javax.swing.JSpinner spnLabelHeight;
    protected javax.swing.JSpinner spnLabelWidth;
    protected javax.swing.JSpinner spnSideMargin;
    protected javax.swing.JSpinner spnTopMargin;
    // End of variables declaration//GEN-END:variables
    
}
