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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import ar.edu.unlp.yaqc4j.exceptions.GenerationError;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Generator for classes with (at least) one constructor.
 * 
 * @author Pablo
 * 
 * @param <T>
 *            the type of the instances to create
 */
public class ConstructorBasedGen<T> implements Gen<T> {

	/**
	 * the subjectClass.
	 */
	private Class<T> subjectClass;

	/**
	 * Constructor.
	 * 
	 * @param aClass
	 *            The class.
	 */
	public ConstructorBasedGen(final Class<T> aClass) {
		if (aClass.isAnnotation()) {
			throw new IllegalArgumentException(
					"The constructor argument must be a class, not an annotation");
		}
		if (aClass.isEnum()) {
			throw new IllegalArgumentException(
					"The constructor argument must be a class, not an Enum. Use an EnumGen instead.");
		}
		// if (aClass.isPrimitive()) {
		// throw new IllegalArgumentException(
		// "The constructor argument must be a class, not a primitive type");
		// }
		if (aClass.isArray()) {
			throw new IllegalArgumentException(
					"The constructor argument must be a class, not an array type.");
		}
		this.setSubjectClass(aClass);
	}

	/**
	 * Generates random instances of the given type arguments.
	 * 
	 * @param random
	 *            the random.
	 * @param argumentsTypes
	 *            the array of argument types.
	 * @return the array of arguments object.
	 */
	private Object[] generateArguments(final Distribution random,
			final Class<?>[] argumentsTypes) {
		Object[] arguments = new Object[argumentsTypes.length];
		for (int i = 0; i < argumentsTypes.length; i++) {
			Class<?> aClass = argumentsTypes[i];
			arguments[i] = Arbitrary.getInstanceOf(random, aClass);
		}
		return arguments;
	}

	/**
	 * @{inheritDoc
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T arbitrary(final Distribution random, final long minsize,
			final long maxsize) {
		Constructor<T>[] constructors = (Constructor<T>[]) this
				.getSubjectClass().getConstructors();
		if (constructors.length == 0) {
			if (this.getSubjectClass().isInterface()) {
				return new InterfaceInstancesGen<T>(
						this.getSubjectClass()).arbitrary(random, minsize,
						maxsize);
			} else {
				throw new GenerationError(this.getSubjectClass().toString()
						+ " has no constructor.");
			}
		}
		int index = (int) Arbitrary.choose(random, 0, constructors.length - 1);
		Constructor<?> constructor = constructors[index];
		Class<?>[] classes = constructor.getParameterTypes();
		Object[] arguments = this.generateArguments(random, classes);
		try {
			return (T) constructor.newInstance(arguments);
		} catch (IllegalArgumentException e) {
			throw new GenerationError(e);
		} catch (InstantiationException e) {
			throw new GenerationError(e);
		} catch (IllegalAccessException e) {
			throw new GenerationError(e);
		} catch (InvocationTargetException e) {
			StringBuffer sb = new StringBuffer();
			sb.append("Error in arguments generation for constructor ");
			sb.append(constructor.getName());
			sb.append("(");
			for (int i = 0; i < constructor.getParameterTypes().length; i++) {
				Class<?> clazz = constructor.getParameterTypes()[i];
				sb.append(clazz.getName());
				if (i != constructor.getParameterTypes().length - 1)
					sb.append(",");
			}
			sb.append(")");
			GenerationError ex = new GenerationError(sb.toString());
			ex.initCause(e);
			throw ex;
		}
	}

	/**
	 * Getter of the subjectClass.
	 * 
	 * @return the subjectClass
	 */
	private Class<T> getSubjectClass() {
		return this.subjectClass;
	}

	/**
	 * Setter of the subjectClass.
	 * 
	 * @param theSubjectClass
	 *            the subject class.
	 */
	private void setSubjectClass(final Class<T> theSubjectClass) {
		this.subjectClass = theSubjectClass;
	}
}
