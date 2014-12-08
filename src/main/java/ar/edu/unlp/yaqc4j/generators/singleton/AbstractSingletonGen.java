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

import ar.edu.unlp.yaqc4j.generators.Arbitrary;
import ar.edu.unlp.yaqc4j.generators.Gen;

/**
 * Abstract class for singleton generator classes.
 * 
 * @author Pablo
 * 
 * @param <T>
 *            the singleton type.
 */
public abstract class AbstractSingletonGen<T> implements Gen<T> {

    /**
     * default method name
     */
    public static final String DEFAULT_METHOD_NAME = "getInstance";

	/**
     * the className.
     */
    private Class<? extends T> className = null;

    /**
     * the singletonMethod.
     */
    private String singletonMethod;

    /**
     * Constructor for singleton class that have a {@code getInstance} method.
     * 
     */
    public AbstractSingletonGen() {
	this(DEFAULT_METHOD_NAME);
    }

    /**
     * The constructor.
     * 
     * @param singletonMethodName
     *            the name of the method for obtaining the class instance.
     * 
     */
    @SuppressWarnings("unchecked")
    public AbstractSingletonGen(final String singletonMethodName) {
	this.setClassName((Class<? extends T>) Arbitrary
		.getSuperclassTypeParameter(this.getClass()));
	this.setSingletonMethod(singletonMethodName);
    }

    /**
     * Getter of singletonMethod.
     * 
     * @return the singletonMethod
     */
    protected final String getSingletonMethod() {
	return this.singletonMethod;
    }

    /**
     * The setter of singletonMethod.
     * 
     * @param theSingletonMethod
     *            the singletonMethod to set
     */
    protected final void setSingletonMethod(final String theSingletonMethod) {
	this.singletonMethod = theSingletonMethod;
    }

    /**
     * Getter of className.
     * 
     * @return the className
     */
    protected final Class<? extends T> getClassName() {
	return this.className;
    }

    /**
     * The setter of className.
     * 
     * @param theClassName
     *            the className to set
     */
    protected final void setClassName(final Class<? extends T> theClassName) {
	this.className = theClassName;
    }
}
