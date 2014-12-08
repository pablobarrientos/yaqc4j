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

package ar.edu.unlp.yaqc4j.properties;

import static ar.edu.unlp.yaqc4j.Implication.imply;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.generators.Arbitrary;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * Generic class for testing equals and hashcode method
 * ar.edu.unlp.yaqc4j.properties.
 * 
 * @author Pablo
 * 
 * @param <T>
 *            the type.
 */
@RunWith(QCCheckRunner.class)
@Configuration(tests = 1)
public class EqualsHashProperties<T> {

	/**
	 * The constructor.
	 */
	public EqualsHashProperties() {
		Arbitrary.registerConstructorGeneratorFor((Class<?>) Arbitrary
				.getSuperclassTypeParameter(this.getClass()));
	}

	/**
	 * Reflexive property test.
	 * 
	 * @param a
	 *            the A instance
	 */
	@Test
	public void reflexiveEquals(final T a) {
		imply(a != null);
		assertEquals(a, a);
	}

	/**
	 * Symmetric property test.
	 * 
	 * @param a
	 *            the first instance.
	 * @param b
	 *            the second instance.
	 */
	@Test
	public void simetricEquals(final T a, final T b) {
		imply(a != null && b != null);
		imply(a.equals(b));
		assertEquals(b, a);
	}

	/**
	 * Transitive property test.
	 * 
	 * @param a
	 *            the first instance
	 * @param b
	 *            the second instance.
	 * @param c
	 *            the third instance.
	 */
	@Test
	public void transitiveEquals(final T a, final T b, final T c) {
		imply(a != null && b != null && c != null);
		imply(a.equals(b));
		imply(b.equals(c));
		assertEquals(a, c);
	}

	/**
	 * hashcode must be the same for equal objects.
	 * 
	 * @param a
	 *            the first instance.
	 * @param b
	 *            the second instance.
	 */
	@Test
	public void equalsHaveSameHashcode(final T a, final T b) {
		imply(a != null && b != null && a.equals(b));
		assertEquals(a.hashCode(), b.hashCode());
	}

	/**
	 * Test that hashcode is immutable in many calls.
	 * 
	 * @param a
	 *            the instance.
	 * @param times
	 *            the number of times that the hashcode method will be called.
	 */
	@Test
	public void hashcodeIsTheSame(final T a, final int times) {
		imply(a != null);
		int hc = a.hashCode();
		for (int i = 0; i < times; i++) {
			assertTrue(hc == a.hashCode());
		}
	}
}
