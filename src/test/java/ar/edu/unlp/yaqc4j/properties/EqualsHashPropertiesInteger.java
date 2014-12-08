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

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.annotations.UseGenerators;
import ar.edu.unlp.yaqc4j.generators.java.lang.IntegerSimpleGen;

/**
 * The number of test is very slow because the probability to find arguments
 * that satisfy preconditions is very slow. On the other side, the max number of
 * arguments fails is 10000.
 * 
 * @author Pablo
 * 
 */
@Configuration(maxsize = 25, tests = 1, maxArgumentsFails = 100)
@UseGenerators({@Generator(klass = int.class, generator = IntegerSimpleGen.class),
				@Generator(klass = Integer.class, generator = IntegerSimpleGen.class)})
public class EqualsHashPropertiesInteger extends EqualsHashProperties<Integer> {

}
