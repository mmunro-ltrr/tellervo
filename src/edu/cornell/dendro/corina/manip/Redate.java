// 
// This file is part of Corina.
// 
// Corina is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// Corina is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Corina; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Copyright 2001 Ken Harris <kbh7@cornell.edu>
//

package edu.cornell.dendro.corina.manip;

import edu.cornell.dendro.corina.Range;
import edu.cornell.dendro.corina.sample.Sample;
import edu.cornell.dendro.corina.tridasv2.GenericFieldUtils;
import edu.cornell.dendro.corina.ui.I18n;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CannotRedoException;

import org.tridas.interfaces.ITridasSeries;
import org.tridas.schema.TridasDating;
import org.tridas.schema.TridasInterpretation;

public class Redate extends AbstractUndoableEdit {
	private static final long serialVersionUID = 1L;

	// redate-force-relative (do i ever even use this?)
	public static Redate redate(Sample s, Range r) {
		return new Redate(s, r, getSampleDating(s));
		// (shouldn't this be a constant somewhere?)
	}

	public static Redate redate(Sample s, Range r, TridasDating dating) {
		return new Redate(s, r, dating);
	}
	
	private static TridasDating getSampleDating(Sample s) {
		ITridasSeries series = s.getSeries();
		TridasDating dating = null;
		
		if(series.isSetInterpretation() && series.getInterpretation().isSetDating())
			dating = series.getInterpretation().getDating();
		
		return dating;
	}
	
	// ----------------------------------------

	private Redate(Sample s, Range range, TridasDating dating) {
		this.s = s;
		this.oldRange = s.getRange();
		this.oldDating = getSampleDating(s);
		this.oldMod = s.isModified();

		oldHadInterpretation = s.getSeries().isSetInterpretation();
		
		this.newRange = range;
		this.newDating = dating;

		// do it a first time -- can't just call redo() because
		// that calls super.redo() (REFACTOR)
		s.setRange(newRange);
		if(!oldHadInterpretation) {
			TridasInterpretation interpretation = new TridasInterpretation();
			s.getSeries().setInterpretation(interpretation);
		}
		
		s.getSeries().getInterpretation().setDating(dating);
		
		// Set the interpretation first year field to new value.  
		// Required for redating in place.
		s.getSeries().getInterpretation().setFirstYear(range.getStart().tridasYearValue());
		
		s.fireSampleRedated();
		s.setModified();
		s.fireSampleMetadataChanged(); // for mod flag
	}

	// undo
	private Sample s;
	private Range oldRange, newRange;
	private TridasDating oldDating, newDating;
	private boolean oldHadInterpretation;
	private boolean oldMod;

	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		s.setRange(oldRange);
		
		if(oldHadInterpretation)
			s.getSeries().getInterpretation().setDating(oldDating);
		else
			s.getSeries().setInterpretation(null);
		
		s.fireSampleRedated();
		if (!oldMod) {
			s.clearModified();
			s.fireSampleMetadataChanged(); // for mod flag
		}
	}

	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		s.setRange(newRange);
		
		if(!oldHadInterpretation) {
			TridasInterpretation interpretation = new TridasInterpretation();
			s.getSeries().setInterpretation(interpretation);
		}		
		s.getSeries().getInterpretation().setDating(newDating);
		
		s.fireSampleRedated();
		s.setModified();
		s.fireSampleMetadataChanged(); // for mod flag
	}

	@Override
	public String getPresentationName() {
		return I18n.getText("redate");
	}
}