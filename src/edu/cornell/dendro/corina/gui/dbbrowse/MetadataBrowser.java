package edu.cornell.dendro.corina.gui.dbbrowse;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.tridas.interfaces.ITridas;
import org.tridas.schema.BaseSeries;
import org.tridas.schema.TridasElement;
import org.tridas.schema.TridasMeasurementSeries;
import org.tridas.schema.TridasObject;
import org.tridas.schema.TridasRadius;
import org.tridas.schema.TridasSample;
import org.tridas.util.TridasObjectEx;

import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheet;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.propertysheet.PropertySheetTableModel;

import edu.cornell.dendro.corina.core.App;
import edu.cornell.dendro.corina.gui.dbbrowse.CorinaCodePanel.ObjectListMode;
import edu.cornell.dendro.corina.gui.dbbrowse.TridasTreeViewPanel.TreeDepth;
import edu.cornell.dendro.corina.tridasv2.ui.CorinaPropertySheetTable;
import edu.cornell.dendro.corina.tridasv2.ui.TridasPropertyEditorFactory;
import edu.cornell.dendro.corina.tridasv2.ui.TridasPropertyRendererFactory;
import edu.cornell.dendro.corina.tridasv2.ui.TridasMetadataPanel.EditType;
import edu.cornell.dendro.corina.tridasv2.ui.support.TridasEntityDeriver;
import edu.cornell.dendro.corina.tridasv2.ui.support.TridasEntityProperty;


/**
 *
 * @author  peterbrewer
 */
public class MetadataBrowser extends javax.swing.JDialog implements PropertyChangeListener, TridasSelectListener {
    
	private static final long serialVersionUID = 8940640945613031936L;
	private TridasTreeViewPanel treepanel;
	/** Our property sheet panel (contains table and description) */
	private PropertySheetPanel propertiesPanel;
	/** Our properties table */
	private CorinaPropertySheetTable propertiesTable;
	
    public MetadataBrowser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupGui();
       
 
        pack();
        
    }
    
    public void setupGui()
    {
    	// Set up tree panel
    	treepanel = new TridasTreeViewPanel(TreeDepth.SERIES, true, "View metadata");
    	treepanel.addTridasSelectListener(this);
    	leftPane.add(treepanel, BorderLayout.CENTER);
    	
    	// Set up metadata panel
    	initPropertiesPanel();
    	rightPane.add(propertiesTable, BorderLayout.CENTER);
    	
    	// Set up dialog
    	setTitle("Metadata Browser");
    	this.btnOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
    	
    	setLocationRelativeTo(null);
    	
    	
    }
    
	private void initPropertiesPanel() {
		propertiesTable = new CorinaPropertySheetTable();
		
		propertiesPanel = new PropertySheetPanel(propertiesTable);

		// keep property collapse/expand states
		propertiesPanel.setRestoreToggleStates(true);
		
		propertiesPanel.setToolBarVisible(false);
		propertiesPanel.setDescriptionVisible(true);
		
		propertiesPanel.setMode(PropertySheet.VIEW_AS_CATEGORIES);
		
		propertiesPanel.getTable().setRowHeight(24);
		propertiesPanel.getTable().setRendererFactory(new TridasPropertyRendererFactory());
		propertiesPanel.getTable().setEditorFactory(new TridasPropertyEditorFactory());
		
	}
	
	public void setEntity(ITridas entity, EditType type)
	{
		
		// derive a property list
        List<TridasEntityProperty> properties = TridasEntityDeriver.buildDerivationList(type.getType());
        Property[] propArray = properties.toArray(new Property[properties.size()]);
        
        // set properties and load from entity
		propertiesPanel.setProperties(propArray);
		
		propertiesPanel.readFromObject(entity);
	}
	
	
	
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        leftPane = new java.awt.Panel();
        rightPane = new java.awt.Panel();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        splitPane.setDividerLocation(400);
        splitPane.setDividerSize(10);
        splitPane.setOneTouchExpandable(true);

        leftPane.setLayout(new java.awt.BorderLayout());
        splitPane.setLeftComponent(leftPane);

        rightPane.setLayout(new java.awt.BorderLayout());
        splitPane.setRightComponent(rightPane);

        btnOk.setText("OK");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, splitPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                    .add(btnOk))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(splitPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .add(18, 18, 18)
                .add(btnOk)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnOk;
    protected java.awt.Panel leftPane;
    protected java.awt.Panel rightPane;
    protected javax.swing.JSplitPane splitPane;
    // End of variables declaration//GEN-END:variables

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entitySelected(TridasSelectEvent event) {
		
		try {
			ITridas entity = event.getEntity();
			
			if(entity instanceof TridasObject)
			{
				this.setEntity(entity, EditType.OBJECT);
			}
			else if (entity instanceof TridasElement)
			{
				this.setEntity(entity, EditType.ELEMENT);
			}
			else if (entity instanceof TridasSample)
			{
				this.setEntity(entity, EditType.SAMPLE);
			}
			else if (entity instanceof TridasRadius)
			{
				this.setEntity(entity, EditType.RADIUS);
			}
			else if (entity instanceof TridasMeasurementSeries)
			{
				this.setEntity(entity, EditType.MEASUREMENT_SERIES);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
    
}
