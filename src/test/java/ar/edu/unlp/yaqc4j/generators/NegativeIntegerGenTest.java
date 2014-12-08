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
package ar.edu.unlp.yaqc4j.generators;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * @author Pablo
 * 
 */
@RunWith(QCCheckRunner.class)
@Configuration(tests = 10000)
public class NegativeIntegerGenTest {
	@Test
	@Generator(klass = int.class, generator = NegativeIntegerGen.class)
	public void testGenerator(int negative) {
		Assert.assertTrue(negative < 0);
	}

	@Test
	@Configuration(tests = 1000, minsize = Integer.MIN_VALUE, maxsize = Integer.MAX_VALUE)
	@Generator(klass = int.class, generator = NegativeIntegerGen.class)
	public void testGeneratorWithMinMax(int negative) {
		Assert.assertTrue(negative < 0);
	}

	@Test
	@Configuration(tests = 1000, minsize = -1000)
	@Generator(klass = int.class, generator = NegativeIntegerGen.class)
	public void testGeneratorWithMin(int negative) {
		Assert.assertTrue(negative < 0 && negative >= -1000);
	}

	@Test
	@Configuration(tests = 1000, maxsize = -10000)
	@Generator(klass = int.class, generator = NegativeIntegerGen.class)
	public void testGeneratorWithMax(int negative) {
		Assert.assertTrue(negative < 0 && negative <= -10000);
	}

}
