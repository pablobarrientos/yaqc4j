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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ar.edu.unlp.yaqc4j.exceptions.GenerationError;
import ar.edu.unlp.yaqc4j.generators.CloneGen;
import ar.edu.unlp.yaqc4j.generators.ElementGen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Abstract generator class for enumerations. Just subclass this class
 * redefining the inherited constructor if you want to get a generic abstract
 * generator.
 * 
 * Be careful with enumerations with ar.edu.unlp.yaqc4j.properties, because they
 * share the state between tests!
 * 
 * @author Pablo
 * @param <E>
 *            the enumeration.
 * 
 */
public abstract class EnumGen<E extends Enum<E>> extends ElementGen<E> {
	/**
	 * The constructor.
	 */
	@SuppressWarnings("unchecked")
	public EnumGen() {
		Class<E> enumName = null;
		for (Method m : this.getClass().getMethods()) {
			// look the abstract method in the current class
			if (m.getName().equals("fixEnumName")) {
				enumName = (Class<E>) m.getParameterTypes()[0];
				break;
			}
		}
		try {
			this.setObjects((E[]) enumName.getMethod("values", new Class[0])
					.invoke(null, new Object[0]));
		} catch (IllegalArgumentException e) {
			throw new GenerationError(e);
		} catch (SecurityException e) {
			throw new GenerationError(e);
		} catch (IllegalAccessException e) {
			throw new GenerationError(e);
		} catch (InvocationTargetException e) {
			throw new GenerationError(e);
		} catch (NoSuchMethodException e) {
			throw new GenerationError(e);
		}
	}

	/**
	 * 
	 * @param random
	 *            the Random.
	 * @param size
	 *            the size.
	 * @param enu
	 *            the enum
	 * @return the object clon
	 */
	private E cloneEnum(final Distribution random, final long minsize,
			final long maxsize, final E enu) {
		CloneGen<E> cloneGen = new CloneGen<E>(enu);
		return cloneGen.arbitrary(random, minsize, maxsize);
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public final E arbitrary(final Distribution random, final long minsize,
			final long maxsize) {
		E element = super.arbitrary(random, minsize, maxsize);
		return this.cloneEnum(random, minsize, maxsize, element);
	}

	/**
	 * Fixes the enum name.
	 * 
	 * @param object
	 *            an instance.
	 */
	public abstract void fixEnumName(E object);

}
