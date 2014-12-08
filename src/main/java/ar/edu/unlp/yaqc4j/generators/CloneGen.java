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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import ar.edu.unlp.yaqc4j.exceptions.GenerationError;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Generator of object clones from a given prototype.
 * 
 * 
 * @param <T>
 *            the clone type.
 */
public class CloneGen<T extends Serializable> implements Gen<T> {

    /**
     * the object to be cloned.
     */
    private T cloned;

    /**
     * Constructor.
     * 
     * @param object
     *            the object.
     */
    public CloneGen(final T object) {
	this.setCloned(object);
    }

    /**
     * @{inheritDoc
     */
    @Override
    public final T arbitrary(final Distribution random, final long minsize,
	    final long maxsize) {
	try {
	    return this.cloneObject();
	} catch (IOException e) {
	    throw new GenerationError("prototype "
		    + this.getCloned().toString() + " is not serializable.");
	} catch (ClassNotFoundException e) {
	    throw new GenerationError(e);
	}
    }

    /**
     * Clone the object.
     * 
     * @return the cloned object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private T cloneObject() throws IOException, ClassNotFoundException {
	if (this.getCloned().getClass().isPrimitive()) {
	    return this.getCloned();
	} else {
	    ByteArrayOutputStream bytesStream = new ByteArrayOutputStream();
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		    bytesStream);
	    objectOutputStream.writeObject(this.getCloned());
	    objectOutputStream.flush();
	    objectOutputStream.close();
	    ObjectInputStream objectInputStream = new ObjectInputStream(
		    new ByteArrayInputStream(bytesStream.toByteArray()));
	    return this.castObjectToT(objectInputStream);
	}
    }

    /**
     * Casts the object.
     * 
     * @param objectInputStream
     *            .
     * @return the object reference
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    private T castObjectToT(final ObjectInputStream objectInputStream)
    throws IOException, ClassNotFoundException {
	return (T) objectInputStream.readObject();
    }

    /**
     * getter of cloned.
     * 
     * @return the cloned
     */
    private T getCloned() {
	return this.cloned;
    }

    /**
     * the setter of cloned.
     * 
     * @param theCloned
     *            the cloned to set.
     */
    private void setCloned(final T theCloned) {
	this.cloned = theCloned;
    }

}
