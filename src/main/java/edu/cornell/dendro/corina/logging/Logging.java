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
// Copyright (c) 2004-2005 Aaron Hamid.  All rights reserved.
// See license in COPYING.txt distributed with this file and available online at http://www.gnu.org/licenses/gpl.txt

package edu.cornell.dendro.corina.logging;

import edu.cornell.dendro.corina.core.AbstractSubsystem;

/**
 * Logging subsystem - just initializes CorinaLog.
 * @author Aaron Hamid
 */
public class Logging extends AbstractSubsystem {
  public String getName() {
    return "Logging";
  }
  @Override
public void init() {
    super.init();
    //CorinaLog.init();
    setInitialized(true);
  }
  @Override
public void destroy() {
    super.destroy();
    setInitialized(false);
    // nothing to destroy
  }
}
