/*******************************************************************************
 * Copyright (C) 2010 Lucas Madar and Peter Brewer
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
 *     Lucas Madar
 *     Peter Brewer
 ******************************************************************************/
package edu.cornell.dendro.corina.sources;

import javax.swing.Icon;
import javax.swing.tree.DefaultTreeCellRenderer;

/*
  notes:
  -- this class will be responsible for caching files
  ---- (ftpsource even more so -- though differently)
*/

public class FolderSource implements Source {

    private String name="Library"; // like "Library"

    public String getName() {
	return name;
    }

    private static Icon closedIcon;
    static {
        DefaultTreeCellRenderer dtcr = new DefaultTreeCellRenderer();
        closedIcon = dtcr.getClosedIcon();
    }
    public Icon getIcon() {
	return closedIcon;
    }

    public boolean canAcceptDrop() {
        return true;
    }
    public boolean canBeDragged() {
        return true;
    }
    public boolean canElementsBeDragged() {
        return true;
    }
}
