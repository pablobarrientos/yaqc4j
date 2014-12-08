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

import static org.junit.Assert.assertNotNull;

import java.awt.event.ActionEvent;

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
@Configuration(maxsize = 5, tests = 1000)
@UseGenerators({
		@Generator(generator = ar.edu.unlp.yaqc4j.generators.PositiveIntegerGen.class, klass = Integer.class),
		@Generator(generator = ar.edu.unlp.yaqc4j.generators.PositiveIntegerGen.class, klass = int.class),
		@Generator(generator = ar.edu.unlp.yaqc4j.generators.PositiveLongGen.class, klass = long.class),
		@Generator(generator = ar.edu.unlp.yaqc4j.generators.PositiveLongGen.class, klass = Long.class) })
public class ConstructorBasedGenTest {

	@Test
	public void generateList(final ActionEvent object) {
		assertNotNull(object);
	}
}
