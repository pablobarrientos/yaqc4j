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

package ar.edu.unlp.yaqc4j.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Custom class loader for searching and loading classes in the classpath.
 * Useful for creating generators on the fly for classes with no generator
 * specified.
 * 
 * @author Pablo
 * 
 */
public class CustomClassLoader extends ClassLoader {
    /**
     * the clazzName
     */
    private String clazzName;

    /**
     * The constructor.
     */
    public CustomClassLoader(final String clazzName) {
	super(CustomClassLoader.class.getClassLoader());
	this.setClazzName(clazzName);
    }

    /**
     * @{inheritDoc
     */
    @Override
    public Class<?> loadClass(final String className)
    throws ClassNotFoundException {
	return this.findClass(className);
    }

    /**
     * @{inheritDoc
     */
    @Override
    public Class<?> findClass(final String className) {
	byte classByte[];
	Class<?> result = null;
	if (this.getClazzName().equals(className)) {
	    try {
		String classPath = ((String) ClassLoader.getSystemResource(
			className.replace('.', '/') + ".class").getFile())
			.substring(1);
		classByte = this.loadClassData(classPath);
		result = this.defineClass(className, classByte, 0,
			classByte.length, null);
		// this.classes.put(className, result);
		return result;
	    } catch (Exception e) {
		return null;
	    }
	} else {
	    try {
		return this.findSystemClass(className);
	    } catch (Exception e) {
		return null;
	    }
	}
    }

    /**
     * @param className
     * @return
     * @throws IOException
     */
    private byte[] loadClassData(final String className) throws IOException {
	File f;
	f = new File(className);
	int size = (int) f.length();
	byte buff[] = new byte[size];
	FileInputStream fis = new FileInputStream(f);
	DataInputStream dis = new DataInputStream(fis);
	dis.readFully(buff);
	dis.close();
	return buff;
    }

    /**
     * The setter of clazzName.
     * 
     * @param clazzName
     *            the clazzName to set
     */
    public void setClazzName(final String clazzName) {
	this.clazzName = clazzName;
    }

    /**
     * Getter of clazzName.
     * 
     * @return the clazzName
     */
    public String getClazzName() {
	return this.clazzName;
    }

}