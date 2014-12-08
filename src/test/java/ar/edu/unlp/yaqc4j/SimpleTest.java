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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.generators.Arbitrary;
import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.generators.NullGen;
import ar.edu.unlp.yaqc4j.generators.PercentageGen;
import ar.edu.unlp.yaqc4j.generators.PositiveIntegerGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.AlphaNumericCharacterGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.AlphaNumericStringGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.DoubleSimpleGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.IntegerSimpleGen;
import ar.edu.unlp.yaqc4j.generators.java.util.ListGen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;
import ar.edu.unlp.yaqc4j.randoms.InvertedNormalDistribution;
import ar.edu.unlp.yaqc4j.randoms.NormalDistribution;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * 
 * @author Pablo
 */
@RunWith(QCCheckRunner.class)
@Configuration(maxsize = 50, minsize = -50, tests=1000)
@Generator(generator = IntegerSimpleGen.class, klass = int.class)
public class SimpleTest {
    private static final int NUM_RUNS = 20;

	@Test
    @Generator(generator = PositiveIntegerGen.class, klass = Integer.class)
    @Configuration(distribution = NormalDistribution.class, minsize=0, maxsize= 100)
    public void testArrays(Integer[] myArray) {
        for (int i = 0; i < myArray.length; i++) {
            assertTrue(myArray[i] != null);
        }
    }
	/**
	 * @param i
	 *            some integer.
	 */
	@Test
	public void arrayIsNotNull(final int[][] i) {
		for (int j = 0; j < i.length; ++j) {
			for (int k = 0; k < i[j].length; ++k) {
				assertNotNull(i[j][k]);
			}
		}
	}

	/**
	 * @param i
	 *            some integer.
	 */
	@Test
	@Configuration(distribution = InvertedNormalDistribution.class)
	public void classConfigurationTest(final int i) {
		assertTrue("@Configuration for class (or int generator) failed",
				i >= -50 && i <= 50);
	}

	/**
	 * @param i
	 *            the integer.
	 */
	@Test
	@Configuration(maxsize = 5, minsize = -5)
	public void methodConfigurationTest(final int i) {
		assertTrue("@Configuration for methods (or in generator) failed",
				i >= -5 && i <= 5);
	}

	/**
	 * @param str
	 *            some string.
	 */
	@Test
	public void testReverse(final String str) {
		StringBuffer buff = new StringBuffer(str);
		assertEquals(str, buff.reverse().reverse().toString());
	}

	/**
	 * @param d
	 *            some double.
	 */
	@Test
	@Generator(klass = double.class, generator = PercentageGen.class)
	public void testNullGen(final double d) {
		final int iterations = 10000;
		Distribution rand = Arbitrary.defaultDistribution();
		NullGen<Integer> ngen = new NullGen<Integer>(new IntegerSimpleGen(), d);
		int nulls = 0;
		for (int i = 0; i < iterations; ++i) {
			if (ngen.arbitrary(rand, 0, 100) == null) {
				nulls++;
			}
		}
		assertEquals(nulls / (double) iterations, d, 0.05);
	}

	/**
	 * @param from
	 *            the lower limit.
	 * 
	 * @param to
	 *            the upper limit.
	 */
	@Test
	public void testArbitraryChoose(final long from, final long to) {
		for (int i = 0; i < 100; ++i) {
			long rand = Arbitrary
					.choose(Arbitrary.defaultDistribution(), -3, 3);
			assertTrue("Should not be less than -3 or greater than 3",
					rand >= -3 && rand <= 3);
			rand = Arbitrary.choose(Arbitrary.defaultDistribution(), 1, 1);
			assertTrue("Should not be anything other than 1", rand == 1);
		}
	}

	/**
	 * test for lists.
	 */
	@SuppressWarnings("unchecked")
	@Test
	@Configuration(tests=20)
	public void testDateList() {
		ListGen<Date> listGen = new ListGen<Date>(
				(Gen<Date>) Arbitrary.getGeneratorFor(
						Arbitrary.defaultDistribution(), Date.class));
		List<Date> list = listGen.arbitrary(Arbitrary.defaultDistribution(), 0,
				10);
		assertTrue(list.size() < 11);
	}

	/**
	 * test for lists.
	 */
	@Test
	@Configuration(tests=NUM_RUNS)
	public void testListWithArgument() {
		ListGen<Double> listGen = new ListGen<Double>(new DoubleSimpleGen());
		List<Double> list = listGen.arbitrary(Arbitrary.defaultDistribution(),
				5, 10);
		assertTrue(list.size() < 11 && list.size() > 4);
	}
	
	
	/**
	 * test for alpha numeric string generator.
	 */
	@Test
	@Generator(klass=String.class, generator=AlphaNumericStringGen.class)
	public void testAlphaNumStringGen(final String str) {
		assertTrue(str.matches("[a-zA-Z0-9]*"));
	}
	
	/**
	 * test for alpha numeric character generator.
	 */
	@Test
	@Generator(klass=Character.class, generator=AlphaNumericCharacterGen.class)
	public void testAlphaNumCharacterGen(final Character c) {
		assertTrue(Character.isDigit(c) || Character.isLetter(c));
	}
	
}
