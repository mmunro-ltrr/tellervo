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
package edu.cornell.dendro.corina;

import java.util.List;

// the view of a table
public class DecadalTable {

    @SuppressWarnings("unchecked")
	private List data; 
    private Range range;
    
    @SuppressWarnings("unchecked")
	DecadalTable(List data, Range range) {
        this.setData(data);
        this.setRange(range);
    }
    
    public Object getValue(int x, int y) {
        if (x == 0) {
          // TODO: WRITEME
        }
        return null;
    }

    public Year getYear(int x, int y) {
        // WRITE ME
        return null;
    }

    private int minRow=0, maxRow=-1;
    public int getRowCount() {
        return (maxRow - minRow + 1);
    }

    // for empty cells; default is null (gasp)
    private Object voidObject = null;
    public void setVoidObject(Object o) {
        voidObject = o;
    }
    public Object getVoidObject() {
        return voidObject;
    }

	@SuppressWarnings("unchecked")
	public void setData(List data) {
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public List getData() {
		return data;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public Range getRange() {
		return range;
	}
}
