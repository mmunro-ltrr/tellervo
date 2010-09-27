/**
ss * Created at Aug 23, 2010, 3:35:03 AM
 */
package edu.cornell.dendro.corina.bulkImport.model;

import java.math.BigDecimal;

import org.tridas.schema.Date;
import org.tridas.schema.TridasElement;

import edu.cornell.dendro.corina.schema.WSIBoxDictionary;
import edu.cornell.dendro.corina.schema.WSISampleTypeDictionary;

/**
 * @author Daniel
 *
 */
public class SampleTableModel extends AbstractBulkImportTableModel {
	private static final long serialVersionUID = 2L;
	
	public SampleTableModel(SampleModel argModel){
		super(argModel);
	}
	
	/**
	 * @see edu.cornell.dendro.corina.bulkImport.model.AbstractBulkImportTableModel#getColumnClass(java.lang.String)
	 */
	public Class<?> getColumnClass(String argColumn){
		if(argColumn.equals(SingleSampleModel.TYPE)){
			return WSISampleTypeDictionary.class;
		}else if(argColumn.equals(SingleSampleModel.IMPORTED)){
			return Boolean.class;
		}else if(argColumn.equals(SingleSampleModel.SAMPLING_DATE)){
			return Date.class;
		}else if(argColumn.equals(SingleSampleModel.KNOTS)){
			return Boolean.class;
		}else if(argColumn.equals(SingleRadiusModel.AZIMUTH)){
			return BigDecimal.class;
		}else if(argColumn.equals(SingleSampleModel.ELEMENT)){
			return TridasElement.class;
		}else if(argColumn.equals(SingleSampleModel.BOX)){
			return WSIBoxDictionary.class;
		}
		return null;
	}
	
	/**
	 * @see edu.cornell.dendro.corina.bulkImport.model.AbstractBulkImportTableModel#setValueAt(java.lang.Object, java.lang.String, edu.cornell.dendro.corina.bulkImport.model.IBulkImportSingleRowModel, int)
	 */
	@Override
	public void setValueAt(Object argAValue, String argColumn, IBulkImportSingleRowModel argModel, int argRowIndex) {
		argModel.setProperty(argColumn, argAValue);
	}
}