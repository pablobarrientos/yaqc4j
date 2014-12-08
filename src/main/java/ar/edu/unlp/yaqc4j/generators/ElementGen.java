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

import java.util.Collection;

import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Generate a random object from the list of objects in the constructor. This
 * generator should be sub-classified.
 * 
 * @author Pablo
 * 
 * @param <T>
 *            the elements type.
 */
public class ElementGen<T> implements Gen<T> {
	/**
	 * the objects.
	 */
	private T[] objects;

	/**
	 * Constructor.
	 * 
	 * @param theObjects
	 *            the objects.
	 */
	public ElementGen(final T... theObjects) {
		this.objects = theObjects;
	}

	/**
	 * Constructor.
	 * 
	 * @param theObjects
	 *            the objects.
	 */
	@SuppressWarnings("unchecked")
	public ElementGen(final Collection<T> theObjects) {
		this.objects = (T[]) theObjects.toArray();
	}

	/**
	 * The constructor.
	 */
	public ElementGen() {
	}

	/**
	 * @{inheritDoc
	 */
	public T arbitrary(final Distribution random, final long minsize,
			final long maxsize) {
		int index = new PositiveIntegerSimpleGen().arbitrary(random,
				Math.min(0, minsize), Math.min(maxsize, this.objects.length - 1));
		return this.objects[index];
	}

	/**
	 * Getter of objects.
	 * 
	 * @return the objects
	 */
	protected final T[] getObjects() {
		return this.objects;
	}

	/**
	 * The setter of objects.
	 * 
	 * @param theObjects
	 *            the objects to set.
	 */
	protected final void setObjects(final T[] theObjects) {
		this.objects = theObjects;
	}
}
