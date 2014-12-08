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

package ar.edu.unlp.yaqc4j.samples;

/**
 * Sample class.
 * 
 * @author Pablo
 * 
 */
public class B extends A {

    /**
     * The constructor.
     * 
     * @param index
     *            the index.
     */
    public B(final Integer index) {
	super(index);
    }

    /**
     * @{inheritDoc
     */
    @Override
    public final boolean equals(final Object anObject) {
	if (!(anObject instanceof A)) {
	    return false;
	}
	A anA = (A) anObject;
	return this.getIndex().equals(anA.getIndex());
    }

    /**
     * @{inheritDoc
     */
    @Override
    public final int hashCode() {
	return this.getIndex();
    }

    /**
     * @{inheritDoc
     */
    @Override
    public final Integer refer(final A anA) {
	return anA.referedByB(this);
    }

    /**
     * @{inheritDoc
     */
    @Override
    protected final Integer referedByB(final B b) {
	return this.getIndex();
    }

    /**
     * @{inheritDoc
     */
    @Override
    protected final Integer referedByC(final C c) {
	return this.getIndex();
    }

}
