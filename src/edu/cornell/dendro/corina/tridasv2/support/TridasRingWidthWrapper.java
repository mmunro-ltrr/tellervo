package edu.cornell.dendro.corina.tridasv2.support;

import java.util.AbstractList;
import java.util.List;

import org.tridas.schema.NormalTridasVariable;
import org.tridas.schema.TridasValue;
import org.tridas.schema.TridasValues;

/**
 * A "better" way of handling the data/count lists: 
 * Wrap completely around the TridasValue list!
 * 
 * Count handling is a little sketchy in places where we can add/remove data, 
 * but this should never be an issue as there's no reason people should
 * be able to modify series with non-trivial counts.
 * 
 * @author Lucas Madar
 */

public final class TridasRingWidthWrapper {
	private List<TridasValue> values;
	private DataWrapper data;
	private CountWrapper count;

	private final class CountWrapper extends AbstractList<Integer> {
		/** The index that we've "added" up to */
		private int countIndex;
		
		/** Are all the counts 1? Safety check, not 100% accurate */
		private boolean countsAreAllOne;

		public CountWrapper() {
			// start out with a 'clean slate'
			clear();
		}
				
		// we should never change a count in place, should we?
		// this is why I don't override set

		@Override
		public void add(int index, Integer element) {
			if(index < 0 || index > countIndex) 
				throw new IndexOutOfBoundsException("Index " + index + 
						" not in count list (max " + countIndex + ")");

			// this might rely on our values list being in a proper state
			// which is dangerous. Oh well.
			TridasValue tridasValue = values.get(index);
			tridasValue.setCount(element);
			
			// not null or one? we have a real count
			if(element != null && element != 1)
				countsAreAllOne = false;
		}

		@Override
		public void clear() {
			// don't really do anything, just ensure our 'add' starts back at zero
			countIndex = 0;
			// mark all our counts as 'one' by default
			countsAreAllOne = true;
		}

		@Override
		public Integer get(int index) {
			TridasValue tridasValue = values.get(index);
		
			// if no count is present, count = 1
			return tridasValue.isSetCount() ? tridasValue.getCount() : 1;
		}

		@Override
		public Integer remove(int index) {
			// we must 'remove' a value that doesn't exist...
			if(index < 0 || index >= countIndex) 
				throw new IndexOutOfBoundsException("Index " + index + 
						" not in count list (max " + countIndex + ")");
			
			// basic sanity check, but not safe though!
			if(!countsAreAllOne)
				throw new IllegalStateException("Removing a value from a non-trivial count list");
			
			// this is totally unsafe, but should probably never be used
			countIndex--;
			
			return 1;
		}
		
		@Override
		public int size() {
			return countIndex;
		}	
	}
	
	/**
	 * Class that emulates a data value list around TridasValue
	 * Implemented for legacy reasons - in the future, please
	 * get rid of this gross, nasty kludge
	 */
	private final class DataWrapper extends AbstractList<Number> {
		// get the indexes to where we want them
		public DataWrapper() {
			reindex();
		}
		
		/**
		 * Get the 'Number' represented by a tridasValue
		 * @param tridasValue
		 * @return a Number
		 * @throws NumberFormatException if the value is not valid
		 */
		private final Number tridasValueAsNumber(TridasValue tridasValue) {
			String value = tridasValue.getValue();
			
			// lack of a number is invalid!
			if(value == null)
				throw new NumberFormatException();
		
			// decimal = Double, otherwise Integer
			if(value.indexOf('.') < 0)
				return Integer.valueOf(value);
			else
				return Double.valueOf(value);
		}
		
		/**
		 * Re-index the tridas values list
		 * we have to do this if we insert a value in the middle
		 */
		public final void reindex() {
			int idx = 0;
			
			for(TridasValue value : values) {
				value.setIndex("i" + idx);
				idx++;
			}
		}
		
		@Override
		public Number get(int index) {
			TridasValue tridasValue = values.get(index);
			
			return tridasValueAsNumber(tridasValue);
		}
		
		@Override
		public void clear() {
			// clearing the data is the same as clearing the values list...
			values.clear();			
		}
		
		@Override
		public void add(int index, Number element) {
			TridasValue newValue = new TridasValue();
			newValue.setValue(element.toString());
			newValue.setIndex("i" + index);
			
			// are we inserting somewhere other than at the end of the list?
			if(index != size()) {
				// well, now we have to reindex!
				reindex();
			}
			
			values.add(index, newValue);
		}

		@Override
		public Number remove(int index) {
			return tridasValueAsNumber(values.remove(index));
		}

		@Override
		public Number set(int index, Number element) {
			TridasValue tridasValue = values.get(index);
			Number ret = tridasValueAsNumber(tridasValue);
			
			tridasValue.setValue(element.toString());
			
			return ret;
		}

		@Override
		public int size() {
			return values.size();
		}
	}
	
	/**
	 * Create a new ring width wrapper around these values
	 * 
	 * @param tridasValues
	 * @param usesCounts
	 */
	public TridasRingWidthWrapper(TridasValues tridasValues) {
		// sanity check
		if(!tridasValues.getVariable().isSetNormalTridas() ||
				tridasValues.getVariable().getNormalTridas() != NormalTridasVariable.RING_WIDTH) 
			throw new IllegalArgumentException("RingWidthWrapper only works on Tridas Ring Widths");
		
		this.values = tridasValues.getValues();
		
		data = new DataWrapper();
		count = new CountWrapper();
	}
	
	public void setData(List<Number> in) {
		data.clear();

		// clearing the data? just bail out now...
		if(in == null)
			return;
		
		for(Number n : in)
			data.add(n);
		
		// get the indexes into our format
		data.reindex();
	}
	
	public final List<Number> getData() {
		return data;
	}
	
	public void setCount(List<Integer> in) {
		count.clear();

		// clearing the data? just bail out now...
		if(in == null)
			return;
		
		for(Integer n : in)
			count.add(n);
	}
	
	public final List<Integer> getCount() {
		return count;
	}
	
	public boolean hasCount() {
		return count.size() > 0;
	}
}
