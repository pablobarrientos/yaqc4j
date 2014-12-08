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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.annotations.UseGenerators;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * @author Pablo
 * 
 */
@RunWith(QCCheckRunner.class)
@Configuration(tests = 100)
public class GeneratorTest {
	/**
	 * test.
	 * 
	 * @param i
	 *            some integer.
	 */
	@Test
	@UseGenerators({ @Generator(generator = ar.edu.unlp.yaqc4j.samples.generators.OnesGen.class, klass = int.class) })
	public void testMethodUseGenerator(final int i) {
		assertEquals("Should be one", 1, i);
	}

	/**
	 * test.
	 * 
	 * @param i
	 *            some integer.
	 */
	@Test
	@Generator(position = 0, generator = ar.edu.unlp.yaqc4j.samples.generators.OnesGen.class)
	public void testMethodGenerator(final Integer i) {
		assertEquals("Should be one", 1, i.intValue());
	}

	/**
	 * test.
	 * 
	 * @param i
	 *            some integer.
	 */
	@Test
	@Generator(klass = Integer.class, generator = ar.edu.unlp.yaqc4j.samples.generators.TwosGen.class)
	public void testClassGenerator(final Integer i) {
		assertEquals(2, i.intValue());
	}

	/**
	 * test.
	 * 
	 * @param i
	 *            some integer.
	 */
	@Test
	@Generator(klass = int.class, generator = ar.edu.unlp.yaqc4j.samples.generators.TwoFourSixGen.class)
	public void testElementGen(final int i) {
		assertTrue(i == 2 || i == 4 || i == 6);
	}

	/**
	 * test.
	 * 
	 * @param i
	 *            some integer array.
	 */
	@Test
	@Generator(klass = int[].class, generator = ar.edu.unlp.yaqc4j.samples.generators.SizeTwoArrayGen.class)
	public void testCustomArrayGen(final int[] i) {
		assertTrue(i.length == 2);
	}
}
