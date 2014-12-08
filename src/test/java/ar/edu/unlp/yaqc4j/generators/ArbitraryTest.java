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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * @author pbarrientos
 * 
 */
@RunWith(QCCheckRunner.class)
public class ArbitraryTest {

	private static final int NUMBER_OF_TESTS = 10000;

	/**
	 * test choose. Tests overflow errors.
	 */
	@Test
	@Configuration(tests=NUMBER_OF_TESTS)
	public void chooseFromEntireRange() {
		long value = Arbitrary.choose(Arbitrary.defaultDistribution(), Long.MIN_VALUE, Long.MAX_VALUE);
		Assert.assertTrue(value >= Long.MIN_VALUE && value <= Long.MAX_VALUE);
	}

	/**
	 * choose positive
	 */
	@Test
	@Configuration(tests=NUMBER_OF_TESTS)
	public void choosePositive() {
		long value = Arbitrary.choose(Arbitrary.defaultDistribution(), 0, Long.MAX_VALUE);
		Assert.assertTrue(value >= 0 && value <= Long.MAX_VALUE);
	}
	
	/**
	 * choose positive
	 */
	@Test
	@Configuration(tests=NUMBER_OF_TESTS)
	public void choosePositiveMinGreaterThanZero() {
		long value = Arbitrary.choose(Arbitrary.defaultDistribution(), 10000, Long.MAX_VALUE);
		Assert.assertTrue(value >= 10000 && value <= Long.MAX_VALUE);
	}
	
	/**
	 * choose positive
	 */
	@Test
	@Configuration(tests=NUMBER_OF_TESTS)
	public void chooseMinLowerThanZero() {
		long value = Arbitrary.choose(Arbitrary.defaultDistribution(), -10000, Long.MAX_VALUE);
		Assert.assertTrue(value >= -10000 && value <= Long.MAX_VALUE);
	}
	
	/**
	 * choose negative
	 */
	@Test
	@Configuration(tests=NUMBER_OF_TESTS)
	public void chooseNegative() {
		long value = Arbitrary.choose(Arbitrary.defaultDistribution(), Long.MIN_VALUE, 0);
		Assert.assertTrue(value <= 0 && value >= Long.MIN_VALUE);
	}
	
	/**
	 * choose negative
	 */
	@Test
	@Configuration(tests=NUMBER_OF_TESTS)
	public void chooseNegativeMinNotMinimum() {
		long value = Arbitrary.choose(Arbitrary.defaultDistribution(), -1000, 0);
		Assert.assertTrue(value >= -1000 && value <= 0);
	}
}
