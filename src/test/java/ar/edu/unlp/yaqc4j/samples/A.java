/*
Yaqc4j is an specification-based testing framework, based on QuickCheck for Haskell by John Hughes.
It makes easier testing task by creating test data automatically through user-defined and built-in
generators.

Copyright (C) 2010 Universidad Nacional de La Plata - pbarrientos@unq.edu.ar

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

package ar.edu.unlp.yaqc4j.samples;

/**
 * Sample class.
 * 
 * @author Pablo
 * 
 */
public abstract class A {

    /**
     * the index.
     */
    private Integer index;

    /**
     * The constructor.
     * 
     * @param theIndex
     *            the index.
     */
    protected A(final Integer theIndex) {
	this.setIndex(theIndex);
    }

    /**
     * @param anA
     *            the a
     * @return int.
     */
    public abstract Integer refer(A anA);

    /**
     * @param b
     *            a b.
     * @return int.
     */
    protected abstract Integer referedByB(B b);

    /**
     * @param c
     *            the c.
     * @return int.
     */
    protected abstract Integer referedByC(C c);

    /**
     * @return the index.
     */
    public final Integer getIndex() {
	return this.index;
    }

    /**
     * @param theIndex
     *            the index.
     */
    private void setIndex(final Integer theIndex) {
	this.index = theIndex;
    }

}
