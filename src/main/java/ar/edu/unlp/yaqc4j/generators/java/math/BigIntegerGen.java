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

package ar.edu.unlp.yaqc4j.generators.java.math;

import java.math.BigInteger;

import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.generators.PositiveIntegerGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.ByteGen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Default BigInteger random generator.
 * 
 * @author Pablo
 * 
 */
public final class BigIntegerGen implements Gen<BigInteger> {
	/**
	 * @{inheritDoc
	 */
	public BigInteger arbitrary(final Distribution random, final long minsize,
			final long maxsize) {
		ByteGen gen = new ByteGen();
		byte[] bytes = new byte[new PositiveIntegerGen().arbitrary(random,
				minsize, Math.min(maxsize, 2000))]; // FIXME: arbitrary length of this array to avoid OutOfMemoryError
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = gen.arbitrary(random, minsize, maxsize);
		}
		return new BigInteger(new PositiveIntegerGen().arbitrary(random,
				minsize, maxsize) > 0 ? 1 : -1, bytes);
	}
}
