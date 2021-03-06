/*******************************************************************************
 * Copyright (C) 2011 Peter Brewer.
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
package org.tellervo.desktop.gui.dbbrowse;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

import org.jdesktop.swingx.JXTable;
import org.tellervo.desktop.ui.Builder;
import org.tellervo.desktop.ui.I18n;




/**
 * GUI class for browsing series in the database.
 *   
 * @author  peterbrewer
 * @see DBBrowser
 */
public class DBBrowser_UI extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	
	/** A return status code - returned if Cancel button has been pressed */
	public static final int RET_CANCEL = 0;
	/** A return status code - returned if OK button has been pressed */
	public static final int RET_OK = 1;
	protected int returnStatus = RET_CANCEL;
	protected JXTable tblAvailMeas;
	protected JXTable tblChosenMeas;
	protected JButton btnAdd;
	protected JButton btnRemove;
	
    /** Creates new form DBBrowser_UI */
    public DBBrowser_UI(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    /**
     * Initilize components of the gui
     */
    protected void doInitComponents(){
    	
    	setIconImage(Builder.getApplicationIcon());
    	  	
    	
    	// Dynamic components
	    btnAdd = new javax.swing.JButton();
	    btnRemove = new javax.swing.JButton();
	    tblAvailMeas = new JXTable();
	    tblChosenMeas = new JXTable();
	    // Standard components
    	initComponents();
    	
    	
    	
    	// Hide ribbon 
	    panelRibbon.setVisible(false);
	    
	    // Hide preview button
	    btnPreview.setVisible(false);

	    // Make sure components are using I18n
        internationalizeComponents();
         
    }
    
	/** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
	public int getReturnStatus() {
	    return returnStatus;
	}
    
    /**
     *  Setup I18n for gui components
     */
    private void internationalizeComponents()
    {
    	this.setTitle(I18n.getText("dbbrowser"));
    	btnCancel.setText(I18n.getText("general.cancel"));
    	btnOk.setText(I18n.getText("general.ok"));    
    	//oldBrowsePanel.setName(I18n.getText("dbbrowser.browse"));
    	searchPanel.setName(I18n.getText("dbbrowser.search"));
    	btnOptions.setText(I18n.getText("dbbrowser.showOptions"));
    	lblSeriesType.setText(I18n.getText("dbbrowser.seriesType"));
    	btnSelectAll.setToolTipText(I18n.getText("dbbrowser.selectAll"));
    	btnSelectNone.setToolTipText(I18n.getText("dbbrowser.selectNone"));
    	btnInvertSelect.setToolTipText(I18n.getText("dbbrowser.selectInvert"));
    	btnAdd.setToolTipText(I18n.getText("dbbrowser.addToSelected"));
    	btnRemove.setToolTipText(I18n.getText("dbbrowser.removeFromSelected"));
    	tabbedPane.setTitleAt(0, I18n.getText("general.browse"));
    	tabbedPane.setTitleAt(1, I18n.getText("general.search"));
    	cboSeriesType.setModel(new javax.swing.DefaultComboBoxModel(
    			new String[]
    			           { I18n.getText("dbbrowser.all"), 
    					I18n.getText("dbbrowser.rawOnly"),
    					I18n.getText("dbbrowser.derivedOnly")}
    			));
    	btnTogByMe.setText(I18n.getText("dbbrowser.createdByMe"));
    	btnTogMostRecent.setText(I18n.getText("dbbrowser.mostRecentVersionsOnly"));
    }
	
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton7 = new javax.swing.JButton();
        panelBottomButtons = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnOptions = new javax.swing.JButton();
        panelRibbon = new javax.swing.JPanel();
        cboSeriesType = new javax.swing.JComboBox();
        lblSeriesType = new javax.swing.JLabel();
        btnTogByMe = new javax.swing.JToggleButton();
        btnTogMostRecent = new javax.swing.JToggleButton();
        mainPanel = new javax.swing.JPanel();
        listTableSplit = new javax.swing.JSplitPane();
        tabbedPane = new javax.swing.JTabbedPane();
        browsePanel = new javax.swing.JPanel();
        searchPanel = new javax.swing.JPanel();
        workArea = new javax.swing.JPanel();
        extraButtonPanel = new javax.swing.JPanel();
        btnSelectAll = new javax.swing.JButton();
        btnSelectNone = new javax.swing.JButton();
        btnInvertSelect = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();

        jButton7.setText("jButton7");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnOk.setText("OK");

        btnCancel.setText("Cancel");

        btnOptions.setText("Show options");

        org.jdesktop.layout.GroupLayout panelBottomButtonsLayout = new org.jdesktop.layout.GroupLayout(panelBottomButtons);
        panelBottomButtons.setLayout(panelBottomButtonsLayout);
        panelBottomButtonsLayout.setHorizontalGroup(
            panelBottomButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelBottomButtonsLayout.createSequentialGroup()
                .add(btnOptions)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 434, Short.MAX_VALUE)
                .add(btnCancel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBottomButtonsLayout.setVerticalGroup(
            panelBottomButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelBottomButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelBottomButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnCancel)
                    .add(btnOk)
                    .add(btnOptions))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cboSeriesType.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        cboSeriesType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Raw only", "Derived only" }));
        cboSeriesType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSeriesTypeActionPerformed(evt);
            }
        });

        lblSeriesType.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        lblSeriesType.setText("Series types:");

        btnTogByMe.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        btnTogByMe.setText("Created by me");

        btnTogMostRecent.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        btnTogMostRecent.setText("Most recent versions only");
        btnTogMostRecent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTogMostRecentActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout panelRibbonLayout = new org.jdesktop.layout.GroupLayout(panelRibbon);
        panelRibbon.setLayout(panelRibbonLayout);
        panelRibbonLayout.setHorizontalGroup(
            panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelRibbonLayout.createSequentialGroup()
                .addContainerGap(446, Short.MAX_VALUE)
                .add(panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(lblSeriesType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnTogMostRecent))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(btnTogByMe, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .add(cboSeriesType, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelRibbonLayout.setVerticalGroup(
            panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelRibbonLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cboSeriesType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblSeriesType))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnTogByMe)
                    .add(btnTogMostRecent))
                .addContainerGap())
        );

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        listTableSplit.setBorder(null);
        listTableSplit.setDividerLocation(480);
        listTableSplit.setDividerSize(11);
        listTableSplit.setOneTouchExpandable(true);

        org.jdesktop.layout.GroupLayout browsePanelLayout = new org.jdesktop.layout.GroupLayout(browsePanel);
        browsePanel.setLayout(browsePanelLayout);
        browsePanelLayout.setHorizontalGroup(
            browsePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 279, Short.MAX_VALUE)
        );
        browsePanelLayout.setVerticalGroup(
            browsePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 432, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Browse", browsePanel);

        org.jdesktop.layout.GroupLayout searchPanelLayout = new org.jdesktop.layout.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 279, Short.MAX_VALUE)
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 432, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Search", searchPanel);
        //tabbedPane.addTab("Search2", staticSearchPanel);

        listTableSplit.setLeftComponent(tabbedPane);

        org.jdesktop.layout.GroupLayout workAreaLayout = new org.jdesktop.layout.GroupLayout(workArea);
        workArea.setLayout(workAreaLayout);
        workAreaLayout.setHorizontalGroup(
            workAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 343, Short.MAX_VALUE)
        );
        workAreaLayout.setVerticalGroup(
            workAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 478, Short.MAX_VALUE)
        );

        listTableSplit.setRightComponent(workArea);

        extraButtonPanel.setMaximumSize(new java.awt.Dimension(25, 32767));
        extraButtonPanel.setMinimumSize(new java.awt.Dimension(25, 0));
        extraButtonPanel.setPreferredSize(new java.awt.Dimension(25, 0));

        btnSelectAll.setToolTipText("Select all");
        btnSelectAll.setPreferredSize(null);

        btnSelectNone.setToolTipText("Select none");

        btnInvertSelect.setToolTipText("Invert selection");

        btnPreview.setToolTipText("Invert selection");

        org.jdesktop.layout.GroupLayout extraButtonPanelLayout = new org.jdesktop.layout.GroupLayout(extraButtonPanel);
        extraButtonPanel.setLayout(extraButtonPanelLayout);
        extraButtonPanelLayout.setHorizontalGroup(
            extraButtonPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnInvertSelect, 0, 0, Short.MAX_VALUE)
            .add(btnSelectNone, 0, 0, Short.MAX_VALUE)
            .add(btnSelectAll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
            .add(btnPreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
        );
        extraButtonPanelLayout.setVerticalGroup(
            extraButtonPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(extraButtonPanelLayout.createSequentialGroup()
                .add(btnSelectAll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnSelectNone)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnInvertSelect)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnPreview)
                .addContainerGap(344, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(listTableSplit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(extraButtonPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 48, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, listTableSplit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, extraButtonPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(panelBottomButtons, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .add(panelRibbon, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(panelRibbon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(2, 2, 2)
                .add(panelBottomButtons, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboSeriesTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSeriesTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSeriesTypeActionPerformed

    private void btnTogMostRecentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTogMostRecentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTogMostRecentActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DBBrowser_UI dialog = new DBBrowser_UI(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JPanel browsePanel;
    protected javax.swing.JButton btnCancel;
    protected javax.swing.JButton btnInvertSelect;
    protected javax.swing.JButton btnOk;
    protected javax.swing.JButton btnOptions;
    protected javax.swing.JButton btnPreview;
    protected javax.swing.JButton btnSelectAll;
    protected javax.swing.JButton btnSelectNone;
    protected javax.swing.JToggleButton btnTogByMe;
    protected javax.swing.JToggleButton btnTogMostRecent;
    protected javax.swing.JComboBox cboSeriesType;
    protected javax.swing.JPanel extraButtonPanel;
    protected javax.swing.JButton jButton7;
    protected javax.swing.JLabel lblSeriesType;
    protected javax.swing.JSplitPane listTableSplit;
    protected javax.swing.JPanel mainPanel;
    protected javax.swing.JPanel panelBottomButtons;
    protected javax.swing.JPanel panelRibbon;
    protected javax.swing.JPanel searchPanel;
    protected javax.swing.JPanel staticSearchPanel;
    protected javax.swing.JTabbedPane tabbedPane;
    protected javax.swing.JPanel workArea;
    // End of variables declaration//GEN-END:variables
    
	public DBBrowser_UI() throws HeadlessException {
		super();
	}


	public DBBrowser_UI(Dialog owner) throws HeadlessException {
		super(owner);
	}

	public DBBrowser_UI(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
	}

	
	public DBBrowser_UI(Frame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
	}
	
	public DBBrowser_UI(Window owner, boolean modal) throws HeadlessException {
		super(owner);
		this.setModal(modal);
	}
    
}
