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

import ar.edu.unlp.yaqc4j.generators.java.lang.IntegerSimpleGen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Default String integer random generator.
 * 
 * @author Pablo
 */
public class StringIntegerGen implements Gen<String> {
	/**
	 * the CHAR_GEN.
	 */
	protected static Gen<Integer> INT_GEN = new IntegerSimpleGen();

	/**
	 * @{inheritDoc
	 */
	public String arbitrary(final Distribution random, final long minsize,
			final long maxsize) {
		return INT_GEN.arbitrary(random, minsize, maxsize).toString();
	}
}
