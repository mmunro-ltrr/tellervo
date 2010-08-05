/*
 * Ui_PreferencesPanel.java
 *
 * Created on October 17, 2008, 11:07 AM
 */

package edu.cornell.dendro.corina.prefs;

import edu.cornell.dendro.corina.core.App;
import edu.cornell.dendro.corina.hardware.SerialMeasuringDeviceConstants;
import edu.cornell.dendro.corina.prefs.wrappers.FormatWrapper;
import edu.cornell.dendro.corina.ui.I18n;

/**
 *
 * @author  lucasm
 */
public class Ui_PreferencesPanel extends javax.swing.JPanel {

    /** Creates new form Ui_PreferencesPanel */
    public Ui_PreferencesPanel() {
        initComponents();
        panelTestComms.setEnabled(false);
        this.btnStartMeasuring.setEnabled(false);
        this.txtComCheckLog.setEnabled(false);
        internationalizeComponents();
    }

    private void internationalizeComponents()
    {
    	// main buttons
    	this.btnOk.setText(I18n.getText("general.ok"));
    	this.btnResetAll.setText(I18n.getText("preferences.resetAll"));
    	this.btnCancel.setText(I18n.getText("general.cancel"));

        // First tab
    	this.propertiesTabs.setTitleAt(0, I18n.getText("preferences.network"));
    	this.panelWebservice.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), I18n.getText("preferences.webservice")));
        this.panelNetworkConnections.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), I18n.getText("preferences.networkConnection")));
        this.panelEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), I18n.getText("preferences.email")));   	
        this.lblWSURL.setText(I18n.getText("general.url")+":");
        this.lblSMTPServer.setText(I18n.getText("preferences.smtpServer")+":");
        this.lblProxyPort.setText(I18n.getText("general.port")+":");
        this.lblProxyPort1.setText(I18n.getText("general.port")+":");
        this.lblProxyServer.setText(I18n.getText("preferences.httpProxy")+":");
        this.lblProxyServer1.setText(I18n.getText("preferences.httpSecureProxy")+":");
        this.btnDefaultProxy.setText(I18n.getText("preferences.defaultProxy"));
        this.btnNoProxy.setText(I18n.getText("preferences.directConnection"));
        this.btnManualProxy.setText(I18n.getText("preferences.useManualProxy"));
        this.btnReloadDictionary.setText(I18n.getText("preferences.reloadDictionary"));
        
        // Second tab
        this.panelHardware.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), I18n.getText("preferences.measuringPlatform")));
        this.panelTestComms.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), I18n.getText("preferences.checkConnection")));        
    	this.propertiesTabs.setTitleAt(1, I18n.getText("preferences.hardware"));
        this.lblPlatformType.setText(I18n.getText("general.type")+":");
        this.lblPort.setText(I18n.getText("general.port")+":");
        this.lblPlatformUnits.setText(I18n.getText("preferences.units")+":");
        this.lblBaud.setText(I18n.getText("preferences.baud")+":");
        this.lblDatabits.setText(I18n.getText("preferences.databits")+":");
        this.lblStopbits.setText(I18n.getText("preferences.stopbits")+":");
        this.lblParity.setText(I18n.getText("preferences.parity")+":");
        this.btnStartMeasuring.setText(I18n.getText("menus.edit.start_measuring"));
        
        // Third tab
    	this.propertiesTabs.setTitleAt(2, I18n.getText("preferences.statistics"));
    	this.panelCOFECHA.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), I18n.getText("preferences.cofechaIntegration")));
        this.panelNumberFormats.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), I18n.getText("preferences.numberFormats")));
        this.panelSigScores.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), I18n.getText("preferences.sigScores")));
        this.chkEnableCOFECHA.setText(I18n.getText("preferences.enableCOFECHA"));
        this.lblCOFECHAPath.setText(I18n.getText("preferences.pathToExecutable")+":");
        this.btnBrowseCOFECHA.setText(I18n.getText("general.browse"));
        this.lblTScore.setText(I18n.getText("statistics.tscore")+":");
        this.lblRValue.setText(I18n.getText("statistics.rvalue")+":");
        this.lblTrend.setText(I18n.getText("statistics.trend")+":");
        this.lblDScore.setText(I18n.getText("statistics.dscore")+":");
        this.lblWJ.setText(I18n.getText("statistics.weiserjahre")+":");
        this.lblMinOverlap.setText(I18n.getText("preferences.minYearsOverlap")+":");
        this.lblMinOverlapDScore.setText(I18n.getText("preferences.minYearsOverlapForDScore")+":");
        this.chkHighlightSig.setText(I18n.getText("preferences.highlightSignificantYears")+":");
        this.lblHighlightColor.setText(I18n.getText("preferences.highlightColor")+":");
                
    	// Fourth tab
    	this.propertiesTabs.setTitleAt(3, I18n.getText("preferences.appearance"));
        this.panelEditor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), I18n.getText("preferences.sampleEditor")));
        this.panelCharts.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), I18n.getText("preferences.charts")));
        this.panelUI.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), I18n.getText("preferences.ui")));
        this.lblDisplayUnits.setText(I18n.getText("preferences.units")+":");
        this.lblTextColor.setText(I18n.getText("preferences.textColor")+":");
        this.lblEditorBGColor.setText(I18n.getText("preferences.backgroundColor")+":");
        this.btnFont.setText(I18n.getText("preferences.font")+":");
        this.chkShowEditorGrid.setText(I18n.getText("preferences.gridlines"));
        this.lblAxisCursorColor.setText(I18n.getText("preferences.axisCursorColor")+":");
        this.lblChartBGColor.setText(I18n.getText("preferences.backgroundColor")+":");
        this.lblGridColor.setText(I18n.getText("preferences.gridColor")+":");
        this.chkShowChartGrid.setText(I18n.getText("preferences.gridlines"));

        
    	
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        proxyButtonGroup = new javax.swing.ButtonGroup();
        propertiesTabs = new javax.swing.JTabbedPane();
        panelNetworkConnections = new javax.swing.JPanel();
        panelWebservice = new javax.swing.JPanel();
        lblWSURL = new javax.swing.JLabel();
        txtWSURL = new javax.swing.JTextField();
        btnReloadDictionary = new javax.swing.JButton();
        panelEmail = new javax.swing.JPanel();
        lblSMTPServer = new javax.swing.JLabel();
        txtSMTPServer = new javax.swing.JTextField();
        panelProxy = new javax.swing.JPanel();
        lblProxyServer = new javax.swing.JLabel();
        txtProxyURL = new javax.swing.JTextField();
        lblProxyPort = new javax.swing.JLabel();
        spnProxyPort = new javax.swing.JSpinner();
        lblProxyServer1 = new javax.swing.JLabel();
        txtProxyURL1 = new javax.swing.JTextField();
        lblProxyPort1 = new javax.swing.JLabel();
        spnProxyPort1 = new javax.swing.JSpinner();
        btnDefaultProxy = new javax.swing.JRadioButton();
        btnNoProxy = new javax.swing.JRadioButton();
        btnManualProxy = new javax.swing.JRadioButton();
        panelHardware = new javax.swing.JPanel();
        panelPlatform = new javax.swing.JPanel();
        cboPlatformType = new javax.swing.JComboBox();
        lblPlatformType = new javax.swing.JLabel();
        lblPort = new javax.swing.JLabel();
        cboPort = new javax.swing.JComboBox();
        cboPlatformUnits = new javax.swing.JComboBox();
        lblPlatformUnits = new javax.swing.JLabel();
        lblBaud = new javax.swing.JLabel();
        lblDatabits = new javax.swing.JLabel();
        lblStopbits = new javax.swing.JLabel();
        lblParity = new javax.swing.JLabel();
        cboBaud = new javax.swing.JComboBox();
        cboDatabits = new javax.swing.JComboBox();
        cboStopbits = new javax.swing.JComboBox();
        cboParity = new javax.swing.JComboBox();
        panelTestComms = new javax.swing.JPanel();
        btnStartMeasuring = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComCheckLog = new javax.swing.JTextArea();
        panelStatistics = new javax.swing.JPanel();
        panelCOFECHA = new javax.swing.JPanel();
        chkEnableCOFECHA = new javax.swing.JCheckBox();
        lblCOFECHAPath = new javax.swing.JLabel();
        txtCOFECHAPath = new javax.swing.JTextField();
        btnBrowseCOFECHA = new javax.swing.JButton();
        panelNumberFormats = new javax.swing.JPanel();
        cboTScore = new javax.swing.JComboBox();
        lblTScore = new javax.swing.JLabel();
        lblRValue = new javax.swing.JLabel();
        cboRValue = new javax.swing.JComboBox();
        lblTrend = new javax.swing.JLabel();
        cboTrend = new javax.swing.JComboBox();
        lblDScore = new javax.swing.JLabel();
        cboDScore = new javax.swing.JComboBox();
        lblWJ = new javax.swing.JLabel();
        cboWJ = new javax.swing.JComboBox();
        panelSigScores = new javax.swing.JPanel();
        spnMinOverlap = new javax.swing.JSpinner();
        lblMinOverlap = new javax.swing.JLabel();
        lblMinOverlapDScore = new javax.swing.JLabel();
        spnMinOverlapDScore = new javax.swing.JSpinner();
        chkHighlightSig = new javax.swing.JCheckBox();
        cboHighlightColor = new javax.swing.JComboBox();
        lblHighlightColor = new javax.swing.JLabel();
        panelAppearance = new javax.swing.JPanel();
        panelEditor = new javax.swing.JPanel();
        cboDisplayUnits = new javax.swing.JComboBox();
        cboTextColor = new javax.swing.JComboBox();
        cboEditorBGColor = new javax.swing.JComboBox();
        btnFont = new javax.swing.JButton();
        chkShowEditorGrid = new javax.swing.JCheckBox();
        lblDisplayUnits = new javax.swing.JLabel();
        lblTextColor = new javax.swing.JLabel();
        lblEditorBGColor = new javax.swing.JLabel();
        lblFont = new javax.swing.JLabel();
        lblShowEditorGrid = new javax.swing.JLabel();
        panelCharts = new javax.swing.JPanel();
        lblAxisCursorColor = new javax.swing.JLabel();
        lblChartBGColor = new javax.swing.JLabel();
        cboChartBGColor = new javax.swing.JComboBox();
        cboAxisCursorColor = new javax.swing.JComboBox();
        lblGridColor = new javax.swing.JLabel();
        cboGridColor = new javax.swing.JComboBox();
        lblShowChartGrid = new javax.swing.JLabel();
        chkShowChartGrid = new javax.swing.JCheckBox();
        panelUI = new javax.swing.JPanel();
        scrollPaneUIDefaults = new javax.swing.JScrollPane();
        panelButtons = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnResetAll = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        seperatorButtons = new javax.swing.JSeparator();

        propertiesTabs.setMinimumSize(new java.awt.Dimension(659, 576));

        panelWebservice.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Web service"));

        lblWSURL.setText("URL:");

        txtWSURL.setText("https://dendro.cornell.edu");
        txtWSURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWSURLActionPerformed(evt);
            }
        });

        btnReloadDictionary.setText("Force Dictionary Reload");

        org.jdesktop.layout.GroupLayout panelWebserviceLayout = new org.jdesktop.layout.GroupLayout(panelWebservice);
        panelWebservice.setLayout(panelWebserviceLayout);
        panelWebserviceLayout.setHorizontalGroup(
            panelWebserviceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelWebserviceLayout.createSequentialGroup()
                .addContainerGap()
                .add(lblWSURL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelWebserviceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btnReloadDictionary)
                    .add(txtWSURL, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelWebserviceLayout.setVerticalGroup(
            panelWebserviceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelWebserviceLayout.createSequentialGroup()
                .add(panelWebserviceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblWSURL)
                    .add(txtWSURL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnReloadDictionary)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Email"));

        lblSMTPServer.setText("SMTP Server:");

        txtSMTPServer.setText("appsmtp.mail.cornell.edu");

        org.jdesktop.layout.GroupLayout panelEmailLayout = new org.jdesktop.layout.GroupLayout(panelEmail);
        panelEmail.setLayout(panelEmailLayout);
        panelEmailLayout.setHorizontalGroup(
            panelEmailLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelEmailLayout.createSequentialGroup()
                .addContainerGap()
                .add(lblSMTPServer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(txtSMTPServer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelEmailLayout.setVerticalGroup(
            panelEmailLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelEmailLayout.createSequentialGroup()
                .add(panelEmailLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblSMTPServer)
                    .add(txtSMTPServer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelProxy.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Network Connection"));

        lblProxyServer.setText("HTTP Proxy:");
        lblProxyServer.setEnabled(false);

        txtProxyURL.setEnabled(false);

        lblProxyPort.setText("Port:");
        lblProxyPort.setEnabled(false);

        spnProxyPort.setEnabled(false);

        lblProxyServer1.setText("HTTPS Proxy:");
        lblProxyServer1.setEnabled(false);

        txtProxyURL1.setEnabled(false);

        lblProxyPort1.setText("Port:");
        lblProxyPort1.setEnabled(false);

        spnProxyPort1.setEnabled(false);

        proxyButtonGroup.add(btnDefaultProxy);
        btnDefaultProxy.setText("Use system default proxy settings");
        btnDefaultProxy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDefaultProxyActionPerformed(evt);
            }
        });

        proxyButtonGroup.add(btnNoProxy);
        btnNoProxy.setText("Direct connection");

        proxyButtonGroup.add(btnManualProxy);
        btnManualProxy.setText("Use manual proxy settings:");

        org.jdesktop.layout.GroupLayout panelProxyLayout = new org.jdesktop.layout.GroupLayout(panelProxy);
        panelProxy.setLayout(panelProxyLayout);
        panelProxyLayout.setHorizontalGroup(
            panelProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelProxyLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelProxyLayout.createSequentialGroup()
                        .add(panelProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(btnManualProxy)
                            .add(btnNoProxy)
                            .add(btnDefaultProxy))
                        .add(299, 299, 299))
                    .add(panelProxyLayout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(panelProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(lblProxyServer)
                            .add(lblProxyServer1))
                        .add(18, 18, 18)
                        .add(panelProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(txtProxyURL, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .add(txtProxyURL1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(panelProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, lblProxyPort)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, lblProxyPort1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(panelProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(spnProxyPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(spnProxyPort1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .add(23, 23, 23))
        );
        panelProxyLayout.setVerticalGroup(
            panelProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelProxyLayout.createSequentialGroup()
                .add(btnDefaultProxy)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnNoProxy)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnManualProxy)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(spnProxyPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblProxyPort)
                    .add(txtProxyURL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblProxyServer))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelProxyLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(spnProxyPort1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblProxyPort1)
                    .add(txtProxyURL1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblProxyServer1))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout panelNetworkConnectionsLayout = new org.jdesktop.layout.GroupLayout(panelNetworkConnections);
        panelNetworkConnections.setLayout(panelNetworkConnectionsLayout);
        panelNetworkConnectionsLayout.setHorizontalGroup(
            panelNetworkConnectionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelNetworkConnectionsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelNetworkConnectionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, panelWebservice, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(panelProxy, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, panelEmail, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelNetworkConnectionsLayout.setVerticalGroup(
            panelNetworkConnectionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelNetworkConnectionsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelWebservice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelProxy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );

        propertiesTabs.addTab("Network", panelNetworkConnections);

        panelPlatform.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Measuring Platform"));

        
        new FormatWrapper(cboPlatformType, Prefs.SERIAL_DEVICE, App.prefs.getPref(Prefs.SERIAL_DEVICE, SerialMeasuringDeviceConstants.NONE), SerialMeasuringDeviceConstants.ALL_DEVICES);

        lblPlatformType.setText("Type:");

        lblPort.setText("Port:");

        cboPort.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "COM1", "COM2", "COM3", "COM4" }));

        cboPlatformUnits.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1/100th mm ", "1/1000th mm" }));
        cboPlatformUnits.setEnabled(false);

        lblPlatformUnits.setText("Units:");

        lblBaud.setText("Baud:");

        lblDatabits.setText("Databits:");

        lblStopbits.setText("Stopbits:");

        lblParity.setText("Parity:");

        cboBaud.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "9600", "4800", "2400", "1200", "300", "110" }));
        cboBaud.setEnabled(false);

        cboDatabits.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "8" }));
        cboDatabits.setEnabled(false);

        cboStopbits.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2" }));
        cboStopbits.setEnabled(false);

        cboParity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "None" }));
        cboParity.setEnabled(false);

        org.jdesktop.layout.GroupLayout panelPlatformLayout = new org.jdesktop.layout.GroupLayout(panelPlatform);
        panelPlatform.setLayout(panelPlatformLayout);
        panelPlatformLayout.setHorizontalGroup(
            panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelPlatformLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblPlatformType)
                    .add(lblPort)
                    .add(lblPlatformUnits)
                    .add(lblBaud)
                    .add(lblDatabits, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(29, 29, 29)
                .add(panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(cboPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cboPlatformType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 313, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cboPlatformUnits, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(panelPlatformLayout.createSequentialGroup()
                        .add(panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cboBaud, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(cboDatabits, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(53, 53, 53)
                        .add(panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lblStopbits)
                            .add(lblParity))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cboParity, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(cboStopbits, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(171, Short.MAX_VALUE))
        );
        panelPlatformLayout.setVerticalGroup(
            panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelPlatformLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblPlatformType)
                    .add(cboPlatformType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cboPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblPort))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblPlatformUnits)
                    .add(cboPlatformUnits, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cboBaud, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblBaud)
                    .add(lblStopbits)
                    .add(cboStopbits, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelPlatformLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblDatabits)
                    .add(cboDatabits, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblParity)
                    .add(cboParity, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTestComms.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Check Connection"));
        panelTestComms.setEnabled(false);

        btnStartMeasuring.setText("Start Measuring");

        txtComCheckLog.setColumns(20);
        txtComCheckLog.setRows(5);
        jScrollPane1.setViewportView(txtComCheckLog);

        org.jdesktop.layout.GroupLayout panelTestCommsLayout = new org.jdesktop.layout.GroupLayout(panelTestComms);
        panelTestComms.setLayout(panelTestCommsLayout);
        panelTestCommsLayout.setHorizontalGroup(
            panelTestCommsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelTestCommsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelTestCommsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .add(btnStartMeasuring))
                .addContainerGap())
        );
        panelTestCommsLayout.setVerticalGroup(
            panelTestCommsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelTestCommsLayout.createSequentialGroup()
                .addContainerGap()
                .add(btnStartMeasuring)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout panelHardwareLayout = new org.jdesktop.layout.GroupLayout(panelHardware);
        panelHardware.setLayout(panelHardwareLayout);
        panelHardwareLayout.setHorizontalGroup(
            panelHardwareLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelHardwareLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelHardwareLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelTestComms, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(panelPlatform, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelHardwareLayout.setVerticalGroup(
            panelHardwareLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelHardwareLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelPlatform, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(panelTestComms, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        propertiesTabs.addTab("Hardware", panelHardware);

        panelCOFECHA.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "COFECHA Integration"));

        chkEnableCOFECHA.setText("Enable COFECHA integration (requires restart)");
        chkEnableCOFECHA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEnableCOFECHAActionPerformed(evt);
            }
        });

        lblCOFECHAPath.setText("Path to executable:");
        lblCOFECHAPath.setEnabled(false);

        txtCOFECHAPath.setEnabled(false);

        btnBrowseCOFECHA.setText("Browse");
        btnBrowseCOFECHA.setEnabled(false);

        org.jdesktop.layout.GroupLayout panelCOFECHALayout = new org.jdesktop.layout.GroupLayout(panelCOFECHA);
        panelCOFECHA.setLayout(panelCOFECHALayout);
        panelCOFECHALayout.setHorizontalGroup(
            panelCOFECHALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelCOFECHALayout.createSequentialGroup()
                .addContainerGap()
                .add(panelCOFECHALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelCOFECHALayout.createSequentialGroup()
                        .add(lblCOFECHAPath)
                        .add(18, 18, 18)
                        .add(txtCOFECHAPath, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnBrowseCOFECHA))
                    .add(chkEnableCOFECHA))
                .addContainerGap())
        );
        panelCOFECHALayout.setVerticalGroup(
            panelCOFECHALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelCOFECHALayout.createSequentialGroup()
                .addContainerGap()
                .add(chkEnableCOFECHA)
                .add(8, 8, 8)
                .add(panelCOFECHALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblCOFECHAPath)
                    .add(btnBrowseCOFECHA)
                    .add(txtCOFECHAPath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        panelNumberFormats.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Number Formats"));

        cboTScore.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.5", "0.49", "0.492", "0.4915", "0.49152", "49%", "49.2%", "49.15%", "49.152%" }));
        cboTScore.setSelectedIndex(1);

        lblTScore.setText("T-score:");

        lblRValue.setText("R-value:");

        cboRValue.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.5", "0.49", "0.492", "0.4915", "0.49152", "49%", "49.2%", "49.15%", "49.152%" }));
        cboRValue.setSelectedIndex(1);

        lblTrend.setText("Trend:");

        cboTrend.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.5", "0.49", "0.492", "0.4915", "0.49152", "49%", "49.2%", "49.15%", "49.152%" }));
        cboTrend.setSelectedIndex(1);

        lblDScore.setText("D-score:");

        cboDScore.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.5", "0.49", "0.492", "0.4915", "0.49152", "49%", "49.2%", "49.15%", "49.152%" }));
        cboDScore.setSelectedIndex(1);

        lblWJ.setText("Weiserjahre:");

        cboWJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.5", "0.49", "0.492", "0.4915", "0.49152", "49%", "49.2%", "49.15%", "49.152%" }));
        cboWJ.setSelectedIndex(1);

        org.jdesktop.layout.GroupLayout panelNumberFormatsLayout = new org.jdesktop.layout.GroupLayout(panelNumberFormats);
        panelNumberFormats.setLayout(panelNumberFormatsLayout);
        panelNumberFormatsLayout.setHorizontalGroup(
            panelNumberFormatsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelNumberFormatsLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(panelNumberFormatsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, lblRValue, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, lblTrend, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, lblDScore, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, lblWJ, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, lblTScore, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelNumberFormatsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(cboRValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cboTScore, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cboTrend, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cboDScore, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cboWJ, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelNumberFormatsLayout.setVerticalGroup(
            panelNumberFormatsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelNumberFormatsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelNumberFormatsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblTScore)
                    .add(cboTScore, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelNumberFormatsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblRValue)
                    .add(cboRValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelNumberFormatsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblTrend)
                    .add(cboTrend, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelNumberFormatsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblDScore)
                    .add(cboDScore, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelNumberFormatsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblWJ)
                    .add(cboWJ, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        panelSigScores.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Significant Scores"));

        lblMinOverlap.setText("Minimum years overlap:");

        lblMinOverlapDScore.setText("Minimum overlap for D-Score:");

        chkHighlightSig.setText("Highlight significant scores");

        cboHighlightColor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Black", "Red", "Orange" }));

        lblHighlightColor.setText("Highlight color:");

        org.jdesktop.layout.GroupLayout panelSigScoresLayout = new org.jdesktop.layout.GroupLayout(panelSigScores);
        panelSigScores.setLayout(panelSigScoresLayout);
        panelSigScoresLayout.setHorizontalGroup(
            panelSigScoresLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelSigScoresLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelSigScoresLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelSigScoresLayout.createSequentialGroup()
                        .add(panelSigScoresLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, lblMinOverlapDScore, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, lblMinOverlap, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(panelSigScoresLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(spnMinOverlapDScore, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .add(spnMinOverlap, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                    .add(chkHighlightSig)
                    .add(panelSigScoresLayout.createSequentialGroup()
                        .add(lblHighlightColor)
                        .add(18, 18, 18)
                        .add(cboHighlightColor, 0, 174, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelSigScoresLayout.setVerticalGroup(
            panelSigScoresLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelSigScoresLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelSigScoresLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblMinOverlap)
                    .add(spnMinOverlap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelSigScoresLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblMinOverlapDScore)
                    .add(spnMinOverlapDScore, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(chkHighlightSig)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelSigScoresLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblHighlightColor)
                    .add(cboHighlightColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout panelStatisticsLayout = new org.jdesktop.layout.GroupLayout(panelStatistics);
        panelStatistics.setLayout(panelStatisticsLayout);
        panelStatisticsLayout.setHorizontalGroup(
            panelStatisticsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelStatisticsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelStatisticsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelStatisticsLayout.createSequentialGroup()
                        .add(panelNumberFormats, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(panelSigScores, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(panelCOFECHA, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelStatisticsLayout.setVerticalGroup(
            panelStatisticsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelStatisticsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelCOFECHA, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelStatisticsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(panelSigScores, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(panelNumberFormats, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(116, Short.MAX_VALUE))
        );

        propertiesTabs.addTab("Statistics", panelStatistics);

        panelEditor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Sample editor"));

        cboDisplayUnits.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1/100mm", "Microns" }));

        cboTextColor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Black", "Grey", "White" }));

        cboEditorBGColor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Black", "Grey", "White" }));

        btnFont.setText("Times New Roman 12pt");

        chkShowEditorGrid.setSelected(true);
        chkShowEditorGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowEditorGridActionPerformed(evt);
            }
        });

        lblDisplayUnits.setText("Display units:");

        lblTextColor.setText("Text color:");

        lblEditorBGColor.setText("Background color:");

        lblFont.setText("Font:");

        lblShowEditorGrid.setText("Gridlines:");

        org.jdesktop.layout.GroupLayout panelEditorLayout = new org.jdesktop.layout.GroupLayout(panelEditor);
        panelEditor.setLayout(panelEditorLayout);
        panelEditorLayout.setHorizontalGroup(
            panelEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelEditorLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(lblEditorBGColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .add(lblTextColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lblDisplayUnits, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lblFont, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lblShowEditorGrid, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(chkShowEditorGrid)
                    .add(btnFont, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, cboTextColor, 0, 404, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, cboEditorBGColor, 0, 404, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, cboDisplayUnits, 0, 404, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelEditorLayout.setVerticalGroup(
            panelEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelEditorLayout.createSequentialGroup()
                .add(panelEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblDisplayUnits, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cboDisplayUnits, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cboTextColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblTextColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblEditorBGColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cboEditorBGColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnFont)
                    .add(lblFont, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(chkShowEditorGrid)
                    .add(lblShowEditorGrid))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCharts.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Charts"));

        lblAxisCursorColor.setText("Axis/cursor color:");

        lblChartBGColor.setText("Background color:");

        cboChartBGColor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Black", "Grey", "White" }));

        cboAxisCursorColor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Black", "Grey", "White" }));

        lblGridColor.setText("Gridline color:");

        cboGridColor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Turquoise", "Grey", "White" }));

        lblShowChartGrid.setText("Draw gridlines:");

        chkShowChartGrid.setSelected(true);
        chkShowChartGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowChartGridActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout panelChartsLayout = new org.jdesktop.layout.GroupLayout(panelCharts);
        panelCharts.setLayout(panelChartsLayout);
        panelChartsLayout.setHorizontalGroup(
            panelChartsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelChartsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelChartsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(lblShowChartGrid, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lblGridColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lblChartBGColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lblAxisCursorColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 139, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelChartsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(chkShowChartGrid)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, cboChartBGColor, 0, 405, Short.MAX_VALUE)
                    .add(panelChartsLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cboGridColor, 0, 405, Short.MAX_VALUE))
                    .add(cboAxisCursorColor, 0, 405, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelChartsLayout.setVerticalGroup(
            panelChartsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelChartsLayout.createSequentialGroup()
                .add(panelChartsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cboAxisCursorColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblAxisCursorColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelChartsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cboChartBGColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblChartBGColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelChartsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cboGridColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblGridColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelChartsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(chkShowChartGrid)
                    .add(lblShowChartGrid))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelUI.setBorder(javax.swing.BorderFactory.createTitledBorder("User interface"));

        org.jdesktop.layout.GroupLayout panelUILayout = new org.jdesktop.layout.GroupLayout(panelUI);
        panelUI.setLayout(panelUILayout);
        panelUILayout.setHorizontalGroup(
            panelUILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelUILayout.createSequentialGroup()
                .addContainerGap()
                .add(scrollPaneUIDefaults, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelUILayout.setVerticalGroup(
            panelUILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelUILayout.createSequentialGroup()
                .add(scrollPaneUIDefaults, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout panelAppearanceLayout = new org.jdesktop.layout.GroupLayout(panelAppearance);
        panelAppearance.setLayout(panelAppearanceLayout);
        panelAppearanceLayout.setHorizontalGroup(
            panelAppearanceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelAppearanceLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelAppearanceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, panelUI, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, panelEditor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, panelCharts, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelAppearanceLayout.setVerticalGroup(
            panelAppearanceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelAppearanceLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelEditor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelCharts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(panelUI, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        propertiesTabs.addTab("Appearance", panelAppearance);

        btnOk.setText("OK");

        btnResetAll.setText("Reset all to default");

        btnCancel.setText("Cancel");

        seperatorButtons.setBackground(new java.awt.Color(153, 153, 153));
        seperatorButtons.setOpaque(true);

        org.jdesktop.layout.GroupLayout panelButtonsLayout = new org.jdesktop.layout.GroupLayout(panelButtons);
        panelButtons.setLayout(panelButtonsLayout);
        panelButtonsLayout.setHorizontalGroup(
            panelButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .add(btnResetAll)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 357, Short.MAX_VALUE)
                .add(btnOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnCancel)
                .addContainerGap())
            .add(seperatorButtons, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
        );
        panelButtonsLayout.setVerticalGroup(
            panelButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelButtonsLayout.createSequentialGroup()
                .add(seperatorButtons, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnCancel)
                    .add(btnOk)
                    .add(btnResetAll))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(propertiesTabs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                .addContainerGap())
            .add(panelButtons, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(propertiesTabs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelButtons, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void chkShowEditorGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowEditorGridActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_chkShowEditorGridActionPerformed

private void chkShowChartGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowChartGridActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_chkShowChartGridActionPerformed

private void txtWSURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWSURLActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtWSURLActionPerformed

private void chkEnableCOFECHAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEnableCOFECHAActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_chkEnableCOFECHAActionPerformed

private void btnDefaultProxyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDefaultProxyActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_btnDefaultProxyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
   protected javax.swing.JButton btnBrowseCOFECHA;
   protected javax.swing.JButton btnCancel;
   protected javax.swing.JRadioButton btnDefaultProxy;
   protected javax.swing.JButton btnFont;
   protected javax.swing.JRadioButton btnManualProxy;
   protected javax.swing.JRadioButton btnNoProxy;
   protected javax.swing.JButton btnOk;
   protected javax.swing.JButton btnReloadDictionary;
   protected javax.swing.JButton btnResetAll;
   protected javax.swing.JButton btnStartMeasuring;
   protected javax.swing.JComboBox cboAxisCursorColor;
   protected javax.swing.JComboBox cboBaud;
   protected javax.swing.JComboBox cboChartBGColor;
   protected javax.swing.JComboBox cboDScore;
   protected javax.swing.JComboBox cboDatabits;
   protected javax.swing.JComboBox cboDisplayUnits;
   protected javax.swing.JComboBox cboEditorBGColor;
   protected javax.swing.JComboBox cboGridColor;
   protected javax.swing.JComboBox cboHighlightColor;
   protected javax.swing.JComboBox cboParity;
   protected javax.swing.JComboBox cboPlatformType;
   protected javax.swing.JComboBox cboPlatformUnits;
   protected javax.swing.JComboBox cboPort;
   protected javax.swing.JComboBox cboRValue;
   protected javax.swing.JComboBox cboStopbits;
   protected javax.swing.JComboBox cboTScore;
   protected javax.swing.JComboBox cboTextColor;
   protected javax.swing.JComboBox cboTrend;
   protected javax.swing.JComboBox cboWJ;
   protected javax.swing.JCheckBox chkEnableCOFECHA;
   protected javax.swing.JCheckBox chkHighlightSig;
   protected javax.swing.JCheckBox chkShowChartGrid;
   protected javax.swing.JCheckBox chkShowEditorGrid;
   protected javax.swing.JScrollPane jScrollPane1;
   protected javax.swing.JLabel lblAxisCursorColor;
   protected javax.swing.JLabel lblBaud;
   protected javax.swing.JLabel lblCOFECHAPath;
   protected javax.swing.JLabel lblChartBGColor;
   protected javax.swing.JLabel lblDScore;
   protected javax.swing.JLabel lblDatabits;
   protected javax.swing.JLabel lblDisplayUnits;
   protected javax.swing.JLabel lblEditorBGColor;
   protected javax.swing.JLabel lblFont;
   protected javax.swing.JLabel lblGridColor;
   protected javax.swing.JLabel lblHighlightColor;
   protected javax.swing.JLabel lblMinOverlap;
   protected javax.swing.JLabel lblMinOverlapDScore;
   protected javax.swing.JLabel lblParity;
   protected javax.swing.JLabel lblPlatformType;
   protected javax.swing.JLabel lblPlatformUnits;
   protected javax.swing.JLabel lblPort;
   protected javax.swing.JLabel lblProxyPort;
   protected javax.swing.JLabel lblProxyPort1;
   protected javax.swing.JLabel lblProxyServer;
   protected javax.swing.JLabel lblProxyServer1;
   protected javax.swing.JLabel lblRValue;
   protected javax.swing.JLabel lblSMTPServer;
   protected javax.swing.JLabel lblShowChartGrid;
   protected javax.swing.JLabel lblShowEditorGrid;
   protected javax.swing.JLabel lblStopbits;
   protected javax.swing.JLabel lblTScore;
   protected javax.swing.JLabel lblTextColor;
   protected javax.swing.JLabel lblTrend;
   protected javax.swing.JLabel lblWJ;
   protected javax.swing.JLabel lblWSURL;
   protected javax.swing.JPanel panelAppearance;
   protected javax.swing.JPanel panelButtons;
   protected javax.swing.JPanel panelCOFECHA;
   protected javax.swing.JPanel panelCharts;
   protected javax.swing.JPanel panelEditor;
   protected javax.swing.JPanel panelEmail;
   protected javax.swing.JPanel panelHardware;
   protected javax.swing.JPanel panelNetworkConnections;
   protected javax.swing.JPanel panelNumberFormats;
   protected javax.swing.JPanel panelPlatform;
   protected javax.swing.JPanel panelProxy;
   protected javax.swing.JPanel panelSigScores;
   protected javax.swing.JPanel panelStatistics;
   protected javax.swing.JPanel panelTestComms;
   protected javax.swing.JPanel panelUI;
   protected javax.swing.JPanel panelWebservice;
   protected javax.swing.JTabbedPane propertiesTabs;
   protected javax.swing.ButtonGroup proxyButtonGroup;
   protected javax.swing.JScrollPane scrollPaneUIDefaults;
   protected javax.swing.JSeparator seperatorButtons;
   protected javax.swing.JSpinner spnMinOverlap;
   protected javax.swing.JSpinner spnMinOverlapDScore;
   protected javax.swing.JSpinner spnProxyPort;
   protected javax.swing.JSpinner spnProxyPort1;
   protected javax.swing.JTextField txtCOFECHAPath;
   protected javax.swing.JTextArea txtComCheckLog;
   protected javax.swing.JTextField txtProxyURL;
   protected javax.swing.JTextField txtProxyURL1;
   protected javax.swing.JTextField txtSMTPServer;
   protected javax.swing.JTextField txtWSURL;
    // End of variables declaration//GEN-END:variables

}
