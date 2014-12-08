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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.generators.java.lang.IntegerSimpleGen;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * Test for @link{Configuration}.
 * 
 * @author Pablo
 * 
 */
@RunWith(QCCheckRunner.class)
@Generator(generator = IntegerSimpleGen.class, klass = int.class)
@Configuration(tests = 5)
public class ConfigurationTest {
	/**
	 * the count.
	 */
	private static int count = 0;

	/**
	 * the count2.
	 */
	private static int count2 = 0;

	/**
	 * @param i
	 *            some integer.
	 */
	@Test
	@Configuration(tests = 3)
	public void configurationSetNumberOfTests(final int i) {
		count++;
		assertTrue("Should not run more than 3 tests", count < 4);
	}

	/**
	 * test.
	 * 
	 * @param i
	 *            some integer.
	 */
	@Test
	public void configurationDontSetNumberOfTests(final int i) {
		count2++;
	}

	/**
	 * test.
	 */
	@AfterClass
	public static void configurationDontSetNumberOfTestsCheckIt() {
		assertEquals(count2, 5);
		assertEquals(count, 3);
	}
}
