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
 * Generate null instead of objects, with a given probability.
 * 
 * @param <T>
 *            the type.
 */
public class NullGen<T> implements Gen<T> {
    /**
     * the generator.
     */
    private Gen<T> generator;

    /**
     * the probability.
     */
    private double probability;

    /**
     * Construct a generator that will generate <code>null</code> with the
     * supplied probability instead of objects from the given generator.
     * 
     * @param theGenerator
     *            generator for objects
     * @param theProbability
     *            probability that the object will be null instead of an object
     *            from the given generator
     */
    public NullGen(final Gen<T> theGenerator, final double theProbability) {
	this.generator = theGenerator;
	this.probability = theProbability;
    }

    /**
     * @{inheritDoc
     */
    public final T arbitrary(final Distribution random, final long minsize,
	    final long maxsize) {
	if (random.nextRandomNumber() < this.probability) {
	    return null;
	} else {
	    return this.generator.arbitrary(random, minsize, maxsize);
	}
    }
}
