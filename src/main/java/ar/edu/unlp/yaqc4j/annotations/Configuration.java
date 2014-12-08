/*
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

package ar.edu.unlp.yaqc4j.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ar.edu.unlp.yaqc4j.randoms.Distribution;
import ar.edu.unlp.yaqc4j.randoms.UniformDistribution;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * Annotation for giving some parameters to a given test.
 * 
 * @author Pablo
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface Configuration {
	/**
	 * arguments size.
	 * 
	 * @return the arguments max size.
	 */
	long maxsize() default Long.MAX_VALUE;

	/**
	 * arguments size.
	 * 
	 * @return the arguments min size.
	 */
	long minsize() default Long.MIN_VALUE;

	/**
	 * number of tests.
	 * 
	 * @return the number of tests.
	 */
	int tests() default QCCheckRunner.DEFAULT_NUMBER_OF_RUNS;

	/**
	 * max number of argument generation fails.
	 * 
	 * @return the max number of argument generation fails.
	 */
	int maxArgumentsFails() default QCCheckRunner.MAX_ARGUMENTS_FAILS;

	/**
	 * Distribution to be used.
	 * 
	 * @return
	 */
	Class<? extends Distribution> distribution() default UniformDistribution.class;
}
