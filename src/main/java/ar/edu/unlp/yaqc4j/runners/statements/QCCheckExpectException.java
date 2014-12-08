/*
Yaqc4j is an specification-based testing framework, based on QuickCheck for Haskell by John Hughes.
It makes easier testing task by creating test data automatically through user-defined and built-in
generators.

Copyright (C) 2013 Universidad Nacional de La Plata

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

package ar.edu.unlp.yaqc4j.runners.statements;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.runners.model.Statement;

import ar.edu.unlp.yaqc4j.exceptions.Yaqc4jAssertionFailedError;

/**
 * @author Pablo
 *
 */
public class QCCheckExpectException extends ExpectException {
	   private QCCheckStatement fNext;
	    private final Class<? extends Throwable> fExpected;

	    /**
	     * @param next
	     * @param expected
	     */
	    public QCCheckExpectException(Statement next, Class<? extends Throwable> expected) {
	    	super(next, expected);
	    	fNext = (QCCheckStatement)next;
	        fExpected = expected;
	    }
	    
	    /**
	     * @see org.junit.internal.runners.statements.ExpectException#evaluate()
	     */
	    @Override
	    public void evaluate() throws Exception {
	    	boolean complete = false;
	        try {
	            fNext.evaluate();
	            complete = true;
	        } catch (AssumptionViolatedException e) {
	            throw e;
	        } catch (Throwable e) {
	            if (!fExpected.isAssignableFrom(e.getClass())) {
	                String message = "Unexpected exception, expected<"
	                        + fExpected.getName() + "> but was<"
	                        + e.getClass().getName() + ">";
	                throw new Exception(message, e);
	            }
	        }
	        if (complete) {
	        	throw new Yaqc4jAssertionFailedError(fNext.getParamList(), fNext.getFailed(),
	        			new AssertionError("Expected exception: " + fExpected.getName()));
	        }
	    }
}
