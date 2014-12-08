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

package ar.edu.unlp.yaqc4j.generators.singleton;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ar.edu.unlp.yaqc4j.exceptions.GenerationError;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Abstract class for singleton generators. All subclasses must fix the
 * singleton class with method getClassName.
 * 
 * This generator create new instances of the singleton for each test run.
 * 
 * @author Pablo
 * 
 * @param <T>
 *            the singleton type.
 */
public abstract class SingletonGen<T> extends
		AbstractSingletonGen<T> {

	public static final String DEFAULT_FIELD_NAME = "instance";
	
	/**
	 * the field name.
	 */
	private String fieldName;

	/**
	 * The constructor. It assumes that the singleton field is named "instance". Use the other constructor if this is different.
	 */
	public SingletonGen() {
		super();
		this.setFieldName(DEFAULT_FIELD_NAME);
	}
	
	/**
	 * The constructor.
	 * 
	 * @param singletonMethodName
	 *            the name of the singleton method.
	 *
	 * @param singletonField
	 * 			  the name of the singleton field.
	 */
	public SingletonGen(final String singletonMethodName, final String singletonField) {
		super(singletonMethodName);
		this.setFieldName(singletonField);
	}
	
	/**
	 * The constructor.
	 * 
	 * @param singletonMethodName
	 *            the name of the singleton method.
	 *
	 * @param singletonField
	 * 			  the name of the singleton field.
	 * @param className
	 			the class name.
	 */
	public SingletonGen(final String singletonMethodName, final String singletonField, final Class<T> className) {
		super(singletonMethodName);
		this.setFieldName(singletonField);
		this.setClassName(className);
	}


	/**
	 * @{inheritDoc
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final T arbitrary(final Distribution random, final long minsize,
			final long maxsize) {
		Class<T> cls;

		Method method = null;
		try {
			cls = (Class<T>) Class.forName(this.getClassName().getName(), true,
					this.getClassName().getClassLoader());
			Field instance = cls.getDeclaredField(this.getFieldName());
			instance.setAccessible(true);
			instance.set(cls, null);
			method = cls.getMethod(this.getSingletonMethod(), new Class[0]);
			// It is assumed that the method is static
			return (T) method.invoke(null, new Object[0]);
		} catch (ClassNotFoundException e) {
			throw new GenerationError(e);
		} catch (SecurityException e) {
			throw new GenerationError(e);
		} catch (NoSuchMethodException e) {
			throw new GenerationError(e);
		} catch (IllegalArgumentException e) {
			throw new GenerationError(e);
		} catch (IllegalAccessException e) {
			throw new GenerationError(e);
		} catch (InvocationTargetException e) {
			throw new GenerationError(e);
		} catch (NoSuchFieldException e) {
			throw new GenerationError(e);
		}
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 */
	private void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldName
	 */
	private String getFieldName() {
		return fieldName;
	}

}
