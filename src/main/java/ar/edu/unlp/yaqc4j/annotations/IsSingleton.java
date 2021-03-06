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

import ar.edu.unlp.yaqc4j.generators.singleton.AbstractSingletonGen;
import ar.edu.unlp.yaqc4j.generators.singleton.SingletonGen;

/**
 * Annotation for indicating a singleton generator must be used to generate
 * instances of the given parameter.
 * 
 * @author Pablo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface IsSingleton {

	/**
	 * The field name.
	 * 
	 * @return
	 */
	String fieldName() default SingletonGen.DEFAULT_FIELD_NAME;

	/**
	 * the method name
	 * 
	 * @return
	 */
	String singletonMethodName() default AbstractSingletonGen.DEFAULT_METHOD_NAME;

}
