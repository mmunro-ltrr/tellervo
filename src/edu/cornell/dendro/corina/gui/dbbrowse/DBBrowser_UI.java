package edu.cornell.dendro.corina.gui.dbbrowse;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JDialog;

public class DBBrowser_UI extends JDialog {

	/** A return status code - returned if Cancel button has been pressed */
	public static final int RET_CANCEL = 0;
	/** A return status code - returned if OK button has been pressed */
	public static final int RET_OK = 1;
	public static final Color ODD_ROW_COLOR = new Color(236, 243, 254);

	/**
	 * @param args the command line arguments
	 */
	public static void zzzmain(String args[]) {
	    java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            DBBrowser dialog = new DBBrowser(new javax.swing.JFrame(), true);
	            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
	                public void windowClosing(java.awt.event.WindowEvent e) {
	                    System.exit(0);
	                }
	            });
	            dialog.setVisible(true);
	        }
	    });
	}

	private javax.swing.JPanel browsePanel;
	private javax.swing.JTabbedPane browseSearchPane;
	protected javax.swing.JPanel extraButtonPanel;
	protected javax.swing.JButton btnCancel;
	protected javax.swing.JButton btnInvertSelect;
	protected javax.swing.JButton btnOk;
	protected javax.swing.JButton btnAdd;
	protected javax.swing.JButton btnRemove;
	private javax.swing.JButton btnAddAll;
	private javax.swing.JButton btnRemoveAll;

	/** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
	public int getReturnStatus() {
	    return returnStatus;
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	protected void initComponents() {
	
	    btnOk = new javax.swing.JButton();
	    btnCancel = new javax.swing.JButton();
	    btnAdd = new javax.swing.JButton();
	    btnRemove = new javax.swing.JButton();
	    jSplitPane1 = new javax.swing.JSplitPane();
	    panelBrowseBy = new javax.swing.JPanel();
	    browseSearchPane = new javax.swing.JTabbedPane();
	    browsePanel = new javax.swing.JPanel();
	    extraButtonPanel = new javax.swing.JPanel();
	    cboBrowseBy = new javax.swing.JComboBox();
	    jScrollPane1 = new javax.swing.JScrollPane();
	    lstSites = new javax.swing.JList();
	    txtFilterInput = new javax.swing.JTextField();
	    searchPanel = new javax.swing.JPanel();
	    jPanel1 = new javax.swing.JPanel();
	    workArea = new javax.swing.JPanel();
	    tblAvailMeas = new javax.swing.JTable();
	    tblChosenMeas = new javax.swing.JTable();
	    btnSelectAll = new javax.swing.JButton();
	    btnSelectNone = new javax.swing.JButton();
	    btnInvertSelect = new javax.swing.JButton();
	
	    setTitle("Measurement Browser");
	
	    btnOk.setText("OK");
	    btnCancel.setText("Cancel");
	
	    jSplitPane1.setBorder(null);
	    jSplitPane1.setDividerLocation(250);
	    jSplitPane1.setResizeWeight(0.2);
	    jSplitPane1.setFocusable(false);
	
	    browseSearchPane.setEnabled(false);
	
	    cboBrowseBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All Regions", "Mediterranean", "Greece", "Italy", "Turkey", "United States" }));
	    cboBrowseBy.setToolTipText("Select a region to filter the sites list below");
	
	    lstSites.setModel(new javax.swing.AbstractListModel() {
	        String[] strings = { "Aezanoi", "Afyon", "Yenikapi", "Yumuktepe" };
	        public int getSize() { return strings.length; }
	        public Object getElementAt(int i) { return strings[i]; }
	    });
	    lstSites.setToolTipText("List of sites.  Select one or more to see the available datasets in the right hand table");
	    jScrollPane1.setViewportView(lstSites);
	
	    txtFilterInput.setToolTipText("Begin typing to filter the sites list above");
	
	    org.jdesktop.layout.GroupLayout browsePanelLayout = new org.jdesktop.layout.GroupLayout(browsePanel);
	    browsePanel.setLayout(browsePanelLayout);
	    browsePanelLayout.setHorizontalGroup(
	        browsePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	        .add(cboBrowseBy, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
	        .add(txtFilterInput, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
	    );
	    browsePanelLayout.setVerticalGroup(
	        browsePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	        .add(browsePanelLayout.createSequentialGroup()
	            .add(cboBrowseBy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
	            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	            .add(txtFilterInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
	    );
	
	    browseSearchPane.addTab("Browse", browsePanel);
	
	    org.jdesktop.layout.GroupLayout searchPanelLayout = new org.jdesktop.layout.GroupLayout(searchPanel);
	    searchPanel.setLayout(searchPanelLayout);
	    searchPanelLayout.setHorizontalGroup(
	        searchPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	        .add(0, 129, Short.MAX_VALUE)
	    );
	    searchPanelLayout.setVerticalGroup(
	        searchPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	        .add(0, 443, Short.MAX_VALUE)
	    );
	
	    browseSearchPane.addTab("Search", searchPanel);
	
	    org.jdesktop.layout.GroupLayout panelBrowseByLayout = new org.jdesktop.layout.GroupLayout(panelBrowseBy);
	    panelBrowseBy.setLayout(panelBrowseByLayout);
	    panelBrowseByLayout.setHorizontalGroup(
	        panelBrowseByLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	        .add(org.jdesktop.layout.GroupLayout.TRAILING, browseSearchPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
	    );
	    panelBrowseByLayout.setVerticalGroup(
	        panelBrowseByLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	        .add(browseSearchPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
	    );
	
	    jSplitPane1.setLeftComponent(panelBrowseBy);
	
	    tblAvailMeas.setModel(new javax.swing.table.DefaultTableModel(
	        new Object [][] {
	            {"Direct", "ABC", "Pinus nigra", "1", "26/3/2008", "1950", "1999"},
	            {"Sum", "ABC", "Pinus nigra", "4", "26/3/2008", "1304", "2001"},
	            {"Chronology", "[3 sites]", "Pinus nigra", "13", "26/3/2008", "1304", "2005"},
	            {"Chronology", "[3 sites]", "[2 species]", "5", "20/3/2008", "1850", "1953"}
	        },
	        new String [] {
	            "Type", "Site name", "Taxon", "No. of Measurements", "Last modified", "Begin Date", "End Date"
	        }
	    ) {
	        Class[] types = new Class [] {
	            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
	        };
	
	        public Class getColumnClass(int columnIndex) {
	            return types [columnIndex];
	        }
	    });
	    tblAvailMeas.setDragEnabled(true);
	    tblAvailMeas.setRowSelectionAllowed(false);
	    //resultsScrollPane.setViewportView(tblAvailMeas);
	
	    org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
	    jPanel1.setLayout(jPanel1Layout);
	    jPanel1Layout.setHorizontalGroup(
	        jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	        .add(workArea, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
	    );
	    jPanel1Layout.setVerticalGroup(
	        jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	        .add(workArea, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
	    );
	
	    jSplitPane1.setRightComponent(jPanel1);
	
	    btnSelectAll.setText("All");
	    btnSelectAll.setToolTipText("Select all measurements in the table");
	
	    btnSelectNone.setText("None");
	    btnSelectNone.setToolTipText("Unselected all the measurements in the table ");
	
	    btnInvertSelect.setText("Invert");
	    btnInvertSelect.setToolTipText("Invert the selected items in the table");        
	    
	    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
	    getContentPane().setLayout(layout);
	    layout.setHorizontalGroup(
	        layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	        .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
	            .addContainerGap()
	            .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
	            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
	                .add(btnInvertSelect)
	                .add(btnSelectNone)
	                .add(btnSelectAll)
	                .add(extraButtonPanel)
	                .add(btnOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                .add(btnCancel))
	            .addContainerGap())
	    );
	
	    layout.linkSize(new java.awt.Component[] {btnCancel, btnOk, btnSelectAll, btnSelectNone, btnInvertSelect}, org.jdesktop.layout.GroupLayout.HORIZONTAL);
	
	    layout.setVerticalGroup(
	        layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	        .add(layout.createSequentialGroup()
	            .addContainerGap()
	            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
	                .add(layout.createSequentialGroup()
	                    .add(btnSelectAll)
	                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	                    .add(btnSelectNone)
	                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	                    .add(btnInvertSelect)
	                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	                    .add(extraButtonPanel)
	                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 332, Short.MAX_VALUE)
	                    .add(btnOk)
	                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	                    .add(btnCancel)))
	            .addContainerGap())
	    );
	
	    pack();
	}// </editor-fold>//GEN-END:initComponents

	protected javax.swing.JButton btnSelectAll;
	protected javax.swing.JButton btnSelectNone;
	protected javax.swing.JComboBox cboBrowseBy;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSplitPane jSplitPane1;
	protected javax.swing.JTextField txtFilterInput;
	protected javax.swing.JList lstSites;
	private javax.swing.JPanel panelBrowseBy;
	protected javax.swing.JPanel workArea;
	private javax.swing.JPanel searchPanel;
	protected javax.swing.JTable tblAvailMeas;
	protected javax.swing.JTable tblChosenMeas;
	protected int returnStatus = RET_CANCEL;

	public DBBrowser_UI() throws HeadlessException {
		super();
	}

	public DBBrowser_UI(Frame owner) throws HeadlessException {
		super(owner);
	}

	public DBBrowser_UI(Dialog owner) throws HeadlessException {
		super(owner);
	}

	public DBBrowser_UI(Frame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
	}

	public DBBrowser_UI(Frame owner, String title) throws HeadlessException {
		super(owner, title);
	}

	public DBBrowser_UI(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
	}

	public DBBrowser_UI(Dialog owner, String title) throws HeadlessException {
		super(owner, title);
	}

	public DBBrowser_UI(Frame owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
	}

	public DBBrowser_UI(Dialog owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
	}

	public DBBrowser_UI(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	public DBBrowser_UI(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
	}

}