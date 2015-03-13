package org.tellervo.desktop.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tellervo.desktop.core.App;
import org.tellervo.desktop.gui.SaveableDocument;
import org.tellervo.desktop.gui.widgets.TitlelessButton;
import org.tellervo.desktop.prefs.PrefsListener;
import org.tellervo.desktop.sample.Sample;
import org.tellervo.desktop.sample.SampleEvent;
import org.tellervo.desktop.sample.SampleListener;
import org.tellervo.desktop.ui.Alert;
import org.tellervo.desktop.ui.Builder;
import org.tridas.interfaces.ITridasSeries;
import org.tridas.io.AbstractDendroFileReader;
import org.tridas.io.TridasIO;
import org.tridas.io.exceptions.ConversionWarningException;
import org.tridas.io.exceptions.InvalidDendroFileException;
import org.tridas.io.util.UnitUtils;
import org.tridas.schema.NormalTridasUnit;
import org.tridas.schema.TridasDerivedSeries;
import org.tridas.schema.TridasElement;
import org.tridas.schema.TridasMeasurementSeries;
import org.tridas.schema.TridasObject;
import org.tridas.schema.TridasProject;
import org.tridas.schema.TridasRadius;
import org.tridas.schema.TridasSample;
import org.tridas.schema.TridasTridas;
import org.tridas.schema.TridasUnit;
import org.tridas.schema.TridasValues;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public abstract class AbstractEditor extends JFrame implements PrefsListener, SaveableDocument, SampleListener, ActionListener {

	private static final long serialVersionUID = 1L;
	protected final static Logger log = LoggerFactory.getLogger(AbstractEditor.class);

	private JPanel contentPane;

	protected SeriesDataMatrix dataView;
	protected DefaultListModel<Sample> samplesModel;
	protected JList<Sample> lstSamples;
	protected JPanel dataPanel;
	protected JTabbedPane tabbedPane;
	private EditorActions actions;
	private TellervoMenuBar menuBar;
	
	protected Window parent;
	protected NormalTridasUnit unitsIfNotSpecified = NormalTridasUnit.MICROMETRES;
	protected AbstractDendroFileReader reader;
	protected File file;
	protected String fileType;
	protected JButton btnAdd;
	protected JButton btnRemove;


	public AbstractEditor(Sample sample) {

		init();
		samplesModel.addElement(sample);
		itemSelected();

	}

	public AbstractEditor(ArrayList<Sample> samples) {

		init();

		for (Sample sample : samples) {
			samplesModel.addElement(sample);
		}
		itemSelected();
	}

	public AbstractEditor() {

		init();
	}

	/**
	 * Initalise the GUI
	 */
	protected void init() {
		if (!App.isInitialized())
			App.init();

		setTitle("Tellervo");

		actions = new EditorActions(this);

		this.setIconImage(Builder.getApplicationIcon());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 851, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[424px,grow,fill]",
				"[20px:20.00px][32px:32.00px:32px][214px,grow,fill]"));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.0);
		contentPane.add(splitPane, "cell 0 2,grow");

		JPanel workspacePanel = new JPanel();
		splitPane.setLeftComponent(workspacePanel);

		samplesModel = new DefaultListModel<Sample>();

		// model.addElement(sampleList);

		workspacePanel.setLayout(new MigLayout("", "[125.00,grow,fill][32.00][20.00,leading]", "[][235px,grow,baseline][fill]"));
		
		workspacePanel.setMinimumSize(new Dimension(240,10));
		
				btnRemove = new JButton();
				btnRemove.setActionCommand("RemoveSample");
				btnRemove.addActionListener(this);
				
						btnAdd = new JButton();
						btnAdd.setActionCommand("AddSample");
						btnAdd.addActionListener(this);
						
						JLabel lblSeries = new JLabel("Series:");
						workspacePanel.add(lblSeries, "cell 0 0");
						btnAdd.setIcon(Builder.getIcon("edit_add.png", 16));
						workspacePanel.add(btnAdd, "cell 1 0");
				btnRemove.setIcon(Builder.getIcon("cancel.png", 16));
				workspacePanel.add(btnRemove, "cell 2 0");

		JScrollPane scrollPane = new JScrollPane();
		workspacePanel.add(scrollPane, "cell 0 1 3 1,grow");

		lstSamples = new JList<Sample>(samplesModel);
		scrollPane.setViewportView(lstSamples);
		lstSamples.setValueIsAdjusting(true);

		lstSamples.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstSamples.setLayoutOrientation(JList.VERTICAL);
		lstSamples.setVisibleRowCount(10);
		
		JPanel panel = new JPanel();
		workspacePanel.add(panel, "cell 0 2 3 1,grow");
		panel.setLayout(new MigLayout("", "[][grow]", "[]"));
		
		JLabel lblSortBy = new JLabel("Sort:");
		panel.add(lblSortBy, "cell 0 0,alignx trailing");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Manually", "Title (asc.)", "Title (desc.)", "Start year (asc.)", "Start year (desc.)", "End year (asc.)", "End year (desc.)"}));
		panel.add(comboBox, "cell 1 0,growx");

		lstSamples.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				itemSelected();
			}

		});

		JPanel panelMain = new JPanel();
		splitPane.setRightComponent(panelMain);
		panelMain.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill]"));

		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		panelMain.add(tabbedPane, "cell 0 0,grow");

		dataPanel = new JPanel();
		tabbedPane.addTab("Data", Builder.getIcon("data.png", 16), dataPanel, null);
		dataPanel.setLayout(new BorderLayout(0, 0));
		dataPanel.setMinimumSize(new Dimension(800, 300));

		initMenu();
		initToolbar();

	}

	protected void initMenu() {
		
		menuBar = new TellervoMenuBar(actions);
		contentPane.add(menuBar, "cell 0 0,growx,aligny top");

	}

	protected void initToolbar() {

		JToolBar toolBar = new JToolBar();

		// File Buttons
		AbstractButton fileOpen = new TitlelessButton(actions.fileOpenAction);
		toolBar.add(fileOpen);

		AbstractButton save = new TitlelessButton(actions.fileSaveAction);
		toolBar.add(save);

		AbstractButton fileexport = new TitlelessButton(actions.fileExportDataAction);
		toolBar.add(fileexport);

		AbstractButton print = new TitlelessButton(actions.filePrintAction);
		toolBar.add(print);

		// Edit Buttons
		AbstractButton measure = new TitlelessButton(actions.editMeasureAction);
		toolBar.add(measure);

		// Initialize data grid button
		AbstractButton initGrid = new TitlelessButton(actions.editInitGridAction);
		toolBar.add(initGrid);

		// Remarks Button
		AbstractButton toggleRemarks = new TitlelessButton(actions.remarkAction);
		toolBar.add(toggleRemarks);

		// Admin Buttons
		toolBar.addSeparator();
		AbstractButton launchMetadb = new TitlelessButton(actions.adminMetaDBAction);
		toolBar.add(launchMetadb);

		// s Buttons
		toolBar.addSeparator();
		AbstractButton truncate = new TitlelessButton(actions.toolsTruncateAction);
		toolBar.add(truncate);

		// Graph Buttons
		toolBar.addSeparator();
		AbstractButton graph = new TitlelessButton(actions.graphSeriesAction);
		toolBar.add(graph);

		contentPane.add(toolBar, "cell 0 1,growx,aligny top");

	}

	/**
	 * Instruct the GUI to stop measuring and hide the measurement panel etc.
	 */
	public void stopMeasuring() {
		if(dataView!=null) dataView.stopMeasuring();
	}

	/**
	 * Toggle between measuring and not-measuring mode
	 */
	public void toggleMeasuring() {
		if(dataView!=null) dataView.toggleMeasuring();

	}

	/**
	 * Get the SeriesDataMatrix tab for this editor
	 * 
	 * @return
	 */
	public SeriesDataMatrix getSeriesDataMatrix() {
		return dataView;
	}

	/**
	 * Get the currently selected sample
	 * 
	 * @return
	 */
	public Sample getSample() {
		try {
			if (samplesModel != null && samplesModel.size() > 0) {
				if (lstSamples.getSelectedIndex() != -1) {
					return (Sample) samplesModel.get(lstSamples.getSelectedIndex());
				} else {
					return (Sample) samplesModel.get(0);
				}

			}
		} catch (Exception e) {

		}

		return null;

	}
	
	public void addSample(Sample s)
	{
		samplesModel.addElement(s);
	}

	protected void setTitle() {

		if (getSample() != null) {
			this.setTitle(getSample().getDisplayTitle() + " - Tellervo");
		} else {
			this.setTitle("Tellervo");
		}
	}

	


	/**
	 * A sample was selected from the list
	 */
	public abstract void itemSelected();

	
	/**
	 * Parse a legacy data file to extract data for display in the editor
	 */
	protected void parseFile()
	{
		// Create a reader based on the file type supplied
		
		reader = TridasIO.getFileReader(fileType);
		if(reader==null) 
		{
			Alert.error(parent, "Error", "Unknown file type");
			return;
		}
		
		// Try and load the file
		try {
			reader.loadFile(file.getAbsolutePath());
		} catch (IOException e) {
			Alert.errorLoading(file.getAbsolutePath(), e);
			return;
		} catch (InvalidDendroFileException e) {
			Alert.error(parent, "Error", "The selected file is not a valid "+fileType+ " file.\nPlease check and try again");
			return;
		}
		catch(NullPointerException e)
		{
			Alert.error(parent, "Invalid File", e.getLocalizedMessage());
		}

		TridasTridas tc = reader.getTridasContainer();
		
		Boolean hideWarningsFlag = false;
		for(TridasProject p : tc.getProjects())
		{
			for(TridasObject o : p.getObjects())
			{
				for(TridasElement e : o.getElements())
				{
					
					for(TridasSample s : e.getSamples())
					{
						for(TridasRadius r : s.getRadiuses())
						{
							for(TridasMeasurementSeries ms : r.getMeasurementSeries())
							{
								Sample sample = EditorFactory.createSampleFromSeries(ms, e, file, fileType, hideWarningsFlag);	
								if(sample==null)
								{
									hideWarningsFlag=true;
								}
								else
								{
									samplesModel.addElement(sample);
								}
								
							}
						}
					}
				}
			}
			
			for(TridasDerivedSeries ds : p.getDerivedSeries())
			{
				Sample sample = EditorFactory.createSampleFromSeries(ds, null, file, fileType, hideWarningsFlag);
				
				if(sample==null)
				{
					hideWarningsFlag=true;
				}
				else
				{
					samplesModel.addElement(sample);
				}
				
			}
			
		}
		
		
		Boolean unitsSet = false;
		for(ITridasSeries ser : getSeries())
		{
			for(TridasValues  tv : ser.getValues())
			{	
				if(tv.isSetUnit())
				{
					if(tv.getUnit().isSetNormalTridas())
					{
						unitsSet = true;
					}
				}	
			}
		}

		if(unitsSet==false && samplesModel.size()>0)
		{
			Object[] possibilities = {"1/1000th mm", 
					"1/100th mm",
					"1/50th mm",
					"1/20th mm",
					"1/10th mm"};
			Object s = JOptionPane.showInputDialog(
			                    parent,
			                    "One or more series has no units defined.\n"
			                    + "Please specify units below:",
			                    "Set Units",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    possibilities,
			                    "1/1000th mm");

			if (s.equals("1/1000th mm")) 
			{
			   unitsIfNotSpecified = NormalTridasUnit.MICROMETRES;
			}
			else if (s.equals("1/100th mm")) 
			{
				   unitsIfNotSpecified = NormalTridasUnit.HUNDREDTH_MM;
			}
			else if (s.equals("1/50th mm")) 
			{
				   unitsIfNotSpecified = NormalTridasUnit.FIFTIETH_MM;
			}	
			else if (s.equals("1/20th mm")) 
			{
				   unitsIfNotSpecified = NormalTridasUnit.TWENTIETH_MM;
			}	
			else if (s.equals("1/10th mm")) 
			{
				   unitsIfNotSpecified = NormalTridasUnit.TENTH_MM;
			}		
			else
			{
				Alert.error(parent, "Error", "Invalid measurement units specified");
				return;
			}
		}
		
		for(int n=0; n< samplesModel.getSize(); n++)
		{
			Sample sample = samplesModel.get(n);
			ITridasSeries series = sample.getSeries();
			
			try {				
				for(int i=0; i < series.getValues().size(); i++)
				{
					TridasValues tv = series.getValues().get(i);
					
					if(tv.isSetUnit())
					{
						if(!tv.getUnit().isSetNormalTridas())
						{
							tv.getUnit().setNormalTridas(unitsIfNotSpecified);
						}
					}	
					else
					{
						TridasUnit unit = new TridasUnit();
						unit.setNormalTridas(unitsIfNotSpecified);
						tv.setUnit(unit);
						tv.setUnitless(null);
					}
					
					tv = UnitUtils.convertTridasValues(NormalTridasUnit.MICROMETRES, tv, true);
					
					TridasUnit unit = new TridasUnit();
					unit.setNormalTridas(NormalTridasUnit.MICROMETRES);
					tv.setUnit(unit);
					series.getValues().set(i,tv);
				}
				
			} catch (NumberFormatException e) {
				Alert.error(parent, "Error", "One or more data values are not numbers.");
				return;
			} catch (ConversionWarningException e) {
				Alert.error(parent, "Error", "Error converting units");
				return;
			}
		}
	}
	
	
	/**
	 * Get list of series for each sample in imported file 
	 * 
	 * @return
	 */
	public ArrayList<ITridasSeries> getSeries()
	{
		
		ArrayList<ITridasSeries> serList = new ArrayList<ITridasSeries>();
		
		for(int n=0; n< samplesModel.getSize(); n++)
		{
			Sample s = samplesModel.get(n);
			serList.add(s.getSeries());
		}
		
		return serList;
	}
	
	
	/**
	 * Get list of samples from the imported file 
	 * 
	 * @return
	 */
	public ArrayList<Sample> getSamples()
	{
		ArrayList<Sample> samplesList = new ArrayList<Sample>();
		
		for(int n=0; n< samplesModel.getSize(); n++)
		{
			Sample s = samplesModel.get(n);
			samplesList.add(s);
		}
		
		return samplesList;	
	}
	
	@Override
	public void sampleRedated(SampleEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sampleDataChanged(SampleEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sampleMetadataChanged(SampleEvent e) {
		this.lstSamples.repaint();
		
	}

	@Override
	public void sampleElementsChanged(SampleEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sampleDisplayUnitsChanged(SampleEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sampleDisplayCalendarChanged(SampleEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void measurementVariableChanged(SampleEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
