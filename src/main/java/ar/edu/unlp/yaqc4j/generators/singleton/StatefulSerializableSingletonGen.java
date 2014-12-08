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

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ar.edu.unlp.yaqc4j.exceptions.GenerationError;
import ar.edu.unlp.yaqc4j.generators.CloneGen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Abstract class for statefull singleton generator. All subclasses must fix the
 * singleton class with method getClassName.
 * 
 * This generator create new instances of a stateful singleton, in order to
 * avoid data sharing among test cases.
 * 
 * As a restriction, the singleton class must implement.
 * 
 * @see{java.io.Serializable
 * 
 * @author Pablo
 * @param <T>
 *            The singleton type.
 * 
 */
public abstract class StatefulSerializableSingletonGen<T extends Serializable>
extends AbstractSingletonGen<T> {

    /**
     * The constructor.
     */
    public StatefulSerializableSingletonGen() {
	super();
    }

    /**
     * The constructor.
     * 
     * @param singletonMethodName
     *            the singleton method name
     */
    public StatefulSerializableSingletonGen(final String singletonMethodName) {
	super(singletonMethodName);
    }

    /**
     * @{inheritDoc
     */
    @SuppressWarnings("unchecked")
    @Override
    public final T arbitrary(final Distribution random, final long minsize,
	    final long maxsize) {
	Class<T> cls;
	try {
	    Method method = null;
	    cls = (Class<T>) Class.forName(this.getClassName().getName(), true,
		    this.getClassName().getClassLoader());
	    method = cls.getMethod(this.getSingletonMethod(), new Class[0]);
	    // It is assumed that the method is static
	    Object o = method.invoke(null, new Object[0]);
	    return new CloneGen<T>((T) o).arbitrary(random, minsize, maxsize);
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
	}
    }
}
