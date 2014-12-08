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
import ar.edu.unlp.yaqc4j.util.Pair;

/**
 * The frequency generator from Hughes's paper.
 * 
 * @author Pablo
 * 
 * @param <T>
 *            the type.
 */
public class FrequencyGen<T> implements Gen<T> {
	/**
	 * the generatorPairs.
	 */
	private Pair<Integer, Gen<T>>[] generatorPairs;

	/**
	 * the size.
	 */
	private int size;

	/**
	 * Constructor.
	 * 
	 * @param theGeneratorPairs
	 *            the generator pairs
	 */
	public FrequencyGen(final Pair<Integer, Gen<T>>... theGeneratorPairs) {
		if (theGeneratorPairs == null || theGeneratorPairs.length == 0) {
			throw new IllegalArgumentException("frequency used with empty list");
		}
		this.generatorPairs = theGeneratorPairs;
		this.size = 0;
		for (int i = 0; i < theGeneratorPairs.length; ++i) {
			this.size += theGeneratorPairs[i].fst();
		}
	}

	/**
	 * @{inheritDoc
	 */
	public final T arbitrary(final Distribution random, final long minsize,
			final long maxsize) {
		int generatorNumber = new PositiveIntegerSimpleGen().arbitrary(random, 1, this.size);
		Gen<T> generator = this.pick(0, generatorNumber);
		return generator.arbitrary(random, minsize, maxsize);
	}

	/**
	 * @param pos
	 *            the position.
	 * @param sought
	 *            the sought.
	 * @return the generator.
	 */
	private Gen<T> pick(final int pos, final int sought) {
		int interval = this.generatorPairs[pos].fst();
		if (sought <= interval || pos == this.generatorPairs.length -1) {
			return this.generatorPairs[pos].snd();
		} else {
			return this.pick(pos + 1, sought);
		}
	}
}
