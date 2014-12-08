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

package ar.edu.unlp.yaqc4j;

import ar.edu.unlp.yaqc4j.exceptions.ImplicationFalseError;

/**
 * 
 * Logical implication, used to ensure some condition before test running.
 * 
 * @author Pablo
 * 
 */
public final class Implication {

    /**
     * The private constructor.
     */
    private Implication() {

    }

    /**
     * 
     * Make an implication that must hold for the test to run.
     * 
     * @param implication
     *            the condition.
     */
    public static void imply(final boolean implication) {
	if (!implication) {
	    throw new ImplicationFalseError();
	}
    }

    /**
     * Make an implication that must hold for the test to run.
     * 
     * @param message
     *            the message.
     * @param implication
     *            the boolean.
     */
    public static void imply(final String message, final boolean implication) {
	if (!implication) {
	    throw new ImplicationFalseError(message);
	}
    }
}
