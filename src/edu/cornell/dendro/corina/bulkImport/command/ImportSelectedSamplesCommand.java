/**
 * Created at Aug 20, 2010, 6:27:07 PM
 */
package edu.cornell.dendro.corina.bulkImport.command;

import java.awt.Window;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;
import org.tridas.schema.TridasElement;
import org.tridas.schema.TridasRadius;
import org.tridas.schema.TridasSample;

import com.dmurph.mvc.MVCEvent;
import com.dmurph.mvc.control.ICommand;

import edu.cornell.dendro.corina.bulkImport.model.BulkImportModel;
import edu.cornell.dendro.corina.bulkImport.model.ElementModel;
import edu.cornell.dendro.corina.bulkImport.model.IBulkImportSingleRowModel;
import edu.cornell.dendro.corina.bulkImport.model.SampleModel;
import edu.cornell.dendro.corina.bulkImport.model.SampleTableModel;
import edu.cornell.dendro.corina.bulkImport.model.SingleRadiusModel;
import edu.cornell.dendro.corina.bulkImport.model.SingleSampleModel;
import edu.cornell.dendro.corina.schema.CorinaRequestType;
import edu.cornell.dendro.corina.ui.Alert;
import edu.cornell.dendro.corina.ui.I18n;
import edu.cornell.dendro.corina.wsi.corina.CorinaResourceAccessDialog;
import edu.cornell.dendro.corina.wsi.corina.resources.EntityResource;

/**
 * @author Daniel
 *
 */
public class ImportSelectedSamplesCommand implements ICommand {
	
	
	
	/**
	 * @see com.dmurph.mvc.control.ICommand#execute(com.dmurph.mvc.MVCEvent)
	 */
	@Override
	public void execute(MVCEvent argEvent) {
		BulkImportModel model = BulkImportModel.getInstance();
		SampleModel smodel = model.getSampleModel();
		SampleTableModel tmodel = smodel.getTableModel();
		
		ArrayList<IBulkImportSingleRowModel> selected = new ArrayList<IBulkImportSingleRowModel>();
		tmodel.getSelected(selected);
		
		// here is where we verify they contain required info
		HashSet<String> requiredMessages = new HashSet<String>();
		ArrayList<SingleSampleModel> incompleteModels = new ArrayList<SingleSampleModel>();
		
		HashSet<String> definedProps = new HashSet<String>();
		for(IBulkImportSingleRowModel srm : selected){
			SingleSampleModel som = (SingleSampleModel) srm;
			definedProps.clear();
			for(String s : SingleSampleModel.TABLE_PROPERTIES){
				if(som.getProperty(s) != null){
					definedProps.add(s);
				}
			}
			if(smodel.isRadiusWithSample()){
				for(String s : SingleRadiusModel.PROPERTIES){
					if(som.getRadiusModel().getProperty(s) != null){
						definedProps.add(s);
					}
				}
			}
			boolean incomplete = false;
			
			// object 
			if(!definedProps.contains(SingleSampleModel.ELEMENT)){
				requiredMessages.add("Cannot import without a parent element.");
				incomplete = true;
			}
			
			// type
			if(!definedProps.contains(SingleSampleModel.TYPE)){
				requiredMessages.add("Sample must contain a type.");
				incomplete = true;
			}
			
			// title
			if(!definedProps.contains(SingleSampleModel.TITLE)){
				requiredMessages.add("Sample must have a title.");
				incomplete = true;
			}
			
			if(smodel.isRadiusWithSample()){
				if(!definedProps.contains(SingleRadiusModel.TITLE)){
					requiredMessages.add("Radius title must be populated.");
					incomplete = true;
				}
			}
			
			
			if(incomplete){
				incompleteModels.add(som);
			}
		}
		
		if(!incompleteModels.isEmpty()){
			StringBuilder message = new StringBuilder();
			message.append("Please correct the following errors:\n");
			message.append(StringUtils.join(requiredMessages, "\n"));
			JOptionPane.showConfirmDialog(model.getMainView(), message.toString(), "Importing Errors", JOptionPane.OK_OPTION);
			return;
		}
		
		// now we actually create the models
		for(IBulkImportSingleRowModel srm : selected){
			SingleSampleModel som = (SingleSampleModel) srm;
			TridasSample origSample = new TridasSample();
			
			if(!som.isDirty()){
				System.out.println("Object isn't dirty, not saving/updating: "+som.getProperty(SingleSampleModel.TITLE).toString());
			}
			
			som.populateToTridasSample(origSample);
			TridasElement parentObject = (TridasElement) som.getProperty(SingleSampleModel.ELEMENT);
			
			// sample first
			EntityResource<TridasSample> sampleResource;
			if(origSample.getIdentifier() != null){
				sampleResource = new EntityResource<TridasSample>(origSample, CorinaRequestType.UPDATE, TridasSample.class);
			}else{
				sampleResource = new EntityResource<TridasSample>(origSample, parentObject, TridasSample.class);
			}
			
			
			// set up a dialog...
			Window parentWindow = SwingUtilities.getWindowAncestor(model.getMainView());
			CorinaResourceAccessDialog dialog = CorinaResourceAccessDialog.forWindow(parentWindow, sampleResource);
			
			sampleResource.query();
			dialog.setVisible(true);
			
			if(!dialog.isSuccessful()) { 
				JOptionPane.showMessageDialog(BulkImportModel.getInstance().getMainView(), I18n.getText("error.savingChanges") + "\r\n" +
						I18n.getText("error") +": " + dialog.getFailException().getLocalizedMessage(),
						I18n.getText("error"), JOptionPane.ERROR_MESSAGE);
				continue;
			}
			som.populateFromTridasSample(sampleResource.getAssociatedResult());
			som.setDirty(false);
			tmodel.setSelected(som, false);
			
			// add to imported list or update existing
			if(origSample.getIdentifier() != null){
				TridasSample found = null;
				for(TridasSample tox : model.getSampleModel().getImportedList()){
					if(tox.getIdentifier().getValue().equals(origSample.getIdentifier().getValue())){
						found = tox;
						break;
					}
				}
				if(found == null){
					Alert.error("Error updating model", "Couldn't find the object in the model to update, please report bug.");
				}else{
					sampleResource.getAssociatedResult().copyTo(found);
				}
			}
			else{
				model.getSampleModel().getImportedList().add(sampleResource.getAssociatedResult());
			}
			
			if(som.getRadiusModel() != null){
				// now lets do the radius
				TridasRadius origRadius = new TridasRadius();
				som.getRadiusModel().populateToTridasRadius(origRadius);
				
				TridasSample parentSample = sampleResource.getAssociatedResult();
				
				// sample first
				EntityResource<TridasRadius> radiusResource;
				if(origRadius.getIdentifier() != null){
					radiusResource = new EntityResource<TridasRadius>(origRadius, CorinaRequestType.UPDATE, TridasRadius.class);
				}else{
					radiusResource = new EntityResource<TridasRadius>(origRadius, parentSample, TridasRadius.class);
				}
				
				
				// set up a dialog...
				parentWindow = SwingUtilities.getWindowAncestor(model.getMainView());
				dialog = CorinaResourceAccessDialog.forWindow(parentWindow, radiusResource);
				
				radiusResource.query();
				dialog.setVisible(true);
				
				if(!dialog.isSuccessful()) { 
					JOptionPane.showMessageDialog(BulkImportModel.getInstance().getMainView(), I18n.getText("error.savingChanges") + "\r\n" +
							I18n.getText("error") +": " + dialog.getFailException().getLocalizedMessage(),
							I18n.getText("error"), JOptionPane.ERROR_MESSAGE);
					continue;
				}
				som.getRadiusModel().populateFromTridasRadius(radiusResource.getAssociatedResult());
				som.getRadiusModel().setDirty(false);
				tmodel.setSelected(som, false);
			}
		}
	}
}
