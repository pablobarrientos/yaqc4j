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

package ar.edu.unlp.yaqc4j.generators.java.lang;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * 
 * @author Pablo
 * 
 */
@RunWith(QCCheckRunner.class)
@Configuration(tests = 10000)
public final class IntegerGenTest {
	@Test
	@Generator(klass = int.class, generator = IntegerGen.class)
	@Configuration(maxsize = 200000)
	public void testGeneratorMax(int i) {
		Assert.assertTrue(i <= 200000);
	}

	@Test
	@Generator(klass = int.class, generator = IntegerGen.class)
	@Configuration(minsize = -10000000)
	public void testGeneratorMin(int i) {
		Assert.assertTrue(i > -10000001);
	}

}