/**
 * 
 */
package edu.cornell.dendro.corina.metadata;

import edu.cornell.dendro.corina.webdbi.ResourceEvent;
import edu.cornell.dendro.corina.webdbi.ResourceEventListener;
import edu.cornell.dendro.corina.core.App;
import edu.cornell.dendro.corina.dictionary.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * @author Lucas Madar
 *
 */
public class DictionaryMetadataField extends MetadataField implements
		ResourceEventListener {
	
	public DictionaryMetadataField(String variableName, String dictionaryName, boolean editable) {
		super(variableName, editable);
		
		App.dictionary.addResourceEventListener(this);
		
		this.dictionaryName = dictionaryName;
		this.hasSetValues = true;
		
		myDictionary = new ArrayList<DictionaryElement>(App.dictionary.getDictionary(dictionaryName));
		if(myDictionary == null)
			System.out.println("Null dictionary for " + variableName + " [" + dictionaryName + "]");
		loadTables();
	}

	private String dictionaryName;
	private volatile List<DictionaryElement> myDictionary; 
	
	private void loadTables() {
		// do we need to do anything here? I think this function can probably be deleted!
	}
	
	@Override
	public String[] getValues() {
		return null;
	}
	
	@Override
	public int getListSize() {
		if(myDictionary == null)
			return 0;
		
		return myDictionary.size();
	}
	
	@Override
	public String getListItemValue(int index) {
		return myDictionary.get(index).getId();
	}

	@Override
	public String getListItemDescription(int index) {
		return myDictionary.get(index).getValue() + " [dict: " + getListItemValue(index) + "]";
	}
	
	public void resourceChanged(ResourceEvent re) {
		if(re.getEventType() == ResourceEvent.RESOURCE_QUERY_COMPLETE) {
			myDictionary.clear();
			myDictionary.addAll(App.dictionary.getDictionary(dictionaryName));
			loadTables();
		}
	}
}
