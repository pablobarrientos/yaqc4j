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
 * @author pbarrientos
 * 
 */
@RunWith(QCCheckRunner.class)
@Configuration(tests = 100)
public class StringBufferGenTest {
	@Test
	@Generator(klass = StringBuffer.class, generator = StringBufferGen.class)
	@Configuration(maxsize = 20000)
	public void testGeneratorMax(StringBuffer b) {
		Assert.assertTrue(b.length() <= 20000);
	}
	
	@Test
	@Generator(klass = StringBuffer.class, generator = StringBufferGen.class)
	@Configuration(minsize = -20000)
	public void testGeneratorMin(StringBuffer b) {
		Assert.assertTrue(b.length() >= 0);
	}
	
	@Test
	@Generator(klass = StringBuffer.class, generator = StringBufferGen.class)
	@Configuration(minsize = 10)
	public void testGeneratorMinPositive(StringBuffer b) {
		Assert.assertTrue(b.length() >= 10);
	}
}
