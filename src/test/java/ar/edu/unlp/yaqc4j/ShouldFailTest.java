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

package ar.edu.unlp.yaqc4j;

import static ar.edu.unlp.yaqc4j.Implication.imply;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * These unit tests are ar.edu.unlp.yaqc4j.samples of bad
 * ar.edu.unlp.yaqc4j.properties. It is quite easy to come up with
 * ar.edu.unlp.yaqc4j.properties that do not hold if one isn't careful.
 */
@RunWith(QCCheckRunner.class)
public class ShouldFailTest {
	/**
	 * Test that reversing a string doesn't give the same string back (which of
	 * course is a bad property since it can).
	 * 
	 * @param str
	 *            some string.
	 */
	@Test(expected = AssertionError.class)
	@Configuration(maxsize = 50, tests = 3)
	public void notAPropertyOfReverse(final String str) {
		StringBuffer buff = new StringBuffer(str);
		if (str.equals(buff.reverse().reverse().toString())) {
			fail();
		}
	}

	// /**
	// * One might think that adding two numbers together will give a number
	// * greater than both of those two numbers...
	// *
	// * @param i
	// * some integer.
	// * @param j
	// * some integer.
	// */
	// @Test(expected = AssertionError.class)
	// public void notAPropertyOfAdd(final int i, final int j) {
	// assertTrue(i + j > i && i + j > j);
	// }

	/**
	 * One might make an implication which is very hard to satisfy. In such
	 * cases one should probably create a special generator.
	 * 
	 * @param d
	 *            some double.
	 */
	@Test(expected = AssertionError.class)
	public void badImplication(final double d) {
		imply(d > 10.0);
		imply("Must be less than 10.001", d < 10.001);
	}
}
