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

package ar.edu.unlp.yaqc4j.exceptions;

import java.util.Arrays;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Error thrown when an assertion fails. This error is caught and results are
 * shown to the user in console.
 * 
 * @author Pablo
 */
public class Yaqc4jAssertionFailedError extends
		junit.framework.AssertionFailedError {
	/**
	 * the serialVersionUID.
	 */
	private static final long serialVersionUID = -200785451590524447L;

	/**
	 * the parameters.
	 */
	private String parameters;

	/**
	 * the numtests.
	 */
	private int numtests;

	/**
	 * the error.
	 */
	private Throwable error;

	/**
	 * constructor.
	 * 
	 * @param params
	 *            theParams.
	 * @param theNumtests
	 *            the number of tests
	 * @param theError
	 *            the error.
	 */
	public Yaqc4jAssertionFailedError(final Object[] params,
			final int theNumtests, final Throwable theError) {
		String[] stringParams = new String[params.length];
		for (int i = 0; i < params.length; i++) {
			stringParams[i] = ToStringBuilder.reflectionToString(params[i]);
		}
		this.parameters = Arrays.toString(stringParams);
		this.numtests = theNumtests;
		this.error = theError;
	}

	/**
	 * Message getter.
	 * 
	 * @return the message.
	 */
	@Override
	public final String getMessage() {
		return String.format("After %d tests.\nMessage: \"%s\". \nWith input parameters:\n %s.",
				this.numtests, this.error.getMessage(), this.parameters);
	}
}
