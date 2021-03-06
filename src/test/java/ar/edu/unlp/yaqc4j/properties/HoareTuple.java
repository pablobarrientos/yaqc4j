/*
Yaqc4j is an specification-based testing framework, based on QuickCheck for Haskell by John Hughes.
It makes easier testing task by creating test data automatically through user-defined and built-in
generators.

Copyright (C) 2010 Universidad Nacional de La Plata

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package ar.edu.unlp.yaqc4j.properties;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * Hoare tuple.
 * 
 * @author Pablo
 * 
 */
@RunWith(QCCheckRunner.class)
public abstract class HoareTuple {
    /**
     * testTuple method.
     */
    @Test
    public void testTuple() {
	if (this.evalPreconditions()) {
	    // if the precondition is not true, the tuple is OK.
	    this.executeBody();
	    this.verifyPostCondition();
	}
    }

    /**
     * After body execution, some condition are verified. In this method,
     * assertions are validated.
     */
    protected abstract void verifyPostCondition();

    /**
     * in the body, some changes to the values used in pre and post conditions
     * could be changed.
     */
    protected abstract void executeBody();

    /**
     * Before body execution, some condition are verified. In this method,
     * assertions are validated.
     * 
     * @return a flag indicating that the tuple should be verified.
     */
    protected abstract boolean evalPreconditions();
}
