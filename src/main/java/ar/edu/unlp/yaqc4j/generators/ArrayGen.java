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

import java.lang.reflect.Array;

import ar.edu.unlp.yaqc4j.generators.java.lang.ShortGen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Generator of arrays. This class is not parameterized in order to allow arrays
 * of primitives.
 * 
 * @author Pablo
 * 
 */
@SuppressWarnings("rawtypes")
public final class ArrayGen implements Gen {
	/**
	 * the dimension.
	 */
	private final int dimension;

	/**
	 * the generator.
	 */
	private final Gen generator;

	/**
	 * the componentClass.
	 */
	private final Class componentClass;

	/**
	 * The constructor.
	 * 
	 * @param theDimension
	 *            the dimension.
	 * @param theComponentClass
	 *            the component class.
	 * @param theGenerator
	 *            the generator
	 */
	public ArrayGen(final int theDimension, final Class theComponentClass,
			final Gen theGenerator) {
		this.generator = theGenerator;
		this.dimension = theDimension;
		this.componentClass = theComponentClass;
	}

	/**
	 * @{inheritDoc
	 */
	public Object arbitrary(final Distribution random, final long minsize,
			final long maxsize) {
		// FIXME: arbitrary upper limit value (1000). Large arrays could create an OutOfMemoryError.
		int arraySize = new ShortGen().arbitrary(random, Math.max(0, minsize), Math.min(maxsize, 1000)); 
		arraySize = Math.abs(arraySize);
		int[] dimensions = new int[this.dimension];
		for (int i = 0; i < this.dimension; ++i) {
			dimensions[i] = arraySize;
		}
		Object array = null;
		array = (Object) Array.newInstance(this.componentClass, dimensions);
		this.fillArray(array, random, minsize, maxsize);
		return array;
	}

	/**
	 * @param array
	 *            the array to fill.
	 * @param random
	 *            the random.
	 * @param size
	 *            the size.
	 */
	private void fillArray(final Object array, final Distribution random,
			final long minsize, final long maxsize) {
		int arraySize = Array.getLength(array);
		Class comp = (Class) array.getClass().getComponentType();
		for (int i = 0; i < arraySize; ++i) {
			if (comp.isArray()) {
				this.fillArray(Array.get(array, i), random, minsize, maxsize);
			} else {
				Array.set(array, i,
						this.generator.arbitrary(random, minsize, maxsize));
			}
		}
	}

}
