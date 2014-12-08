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

package ar.edu.unlp.yaqc4j.util;

/**
 * A generic pair class.
 * 
 * @author Pablo
 * 
 * @param <T1>
 *            the first component.
 * @param <T2>
 *            the second component.
 */
public final class Pair<T1, T2> {
    /**
     * the first.
     */
    private final T1 first;

    /**
     * the second.
     */
    private final T2 second;

    /**
     * The constructor.
     * 
     * @param theFirst
     *            the first component.
     * @param theSecond
     *            the second component.
     */
    public Pair(final T1 theFirst, final T2 theSecond) {
	this.first = theFirst;
	this.second = theSecond;
    }

    /**
     * @return the first component.
     */
    public T1 fst() {
	return this.first;
    }

    /**
     * @return the second component.
     */
    public T2 snd() {
	return this.second;
    }

    /**
     * @param <T1>
     *            the type of the first component.
     * @param <T2>
     *            the type of the second component.
     * @param first
     *            the first component
     * @param second
     *            the second component.
     * @return the pair.
     */
    public static <T1, T2> Pair<T1, T2> make(final T1 first, final T2 second) {
	return new Pair<T1, T2>(first, second);
    }
}
