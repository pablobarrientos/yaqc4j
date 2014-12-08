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

package ar.edu.unlp.yaqc4j.generators;

import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * 
 * Generator for objects using a transformation method (a "functor").
 * 
 * @author Pablo
 * 
 * @param <T>
 *            the origin type.
 * @param <U>
 *            the target type.
 */
public abstract class TransformerGen<T, U> implements Gen<U> {

    /**
     * the inputGen.
     */
    private Gen<T> inputGen;

    /**
     * The constructor.
     * 
     * @param originGen
     *            the origin generator.
     */
    public TransformerGen(final Gen<T> originGen) {
	this.inputGen = originGen;

    }

    /**
     * @{inheritDoc
     */
    @Override
    public final U arbitrary(final Distribution random, final long minsize,
	    final long maxsize) {
	return this
		.transform(this.inputGen.arbitrary(random, minsize, maxsize));
    }

    /**
     * @param object
     *            the original object.
     * @return the object trasnformed.
     */
    protected abstract U transform(final T object);

}