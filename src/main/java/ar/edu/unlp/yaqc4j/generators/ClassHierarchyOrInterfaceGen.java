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

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ar.edu.unlp.yaqc4j.exceptions.GenerationError;
import ar.edu.unlp.yaqc4j.randoms.Distribution;
import ar.edu.unlp.yaqc4j.util.ClassSearcher;

/**
 * Abstract class for generating instances of T, provided T is an interface or an
 * abstract class. Subclasses should not define anything else.
 * 
 * @author Pablo
 * 
 */
public abstract class ClassHierarchyOrInterfaceGen<T> implements Gen<T> {
    /**
     * the subjectClass.
     */
    private Class<T> subjectClass;

    /**
     * The constructor.
     */
    @SuppressWarnings("unchecked")
    public ClassHierarchyOrInterfaceGen() {
	Class<T> clazz = (Class<T>) Arbitrary.getSuperclassTypeParameter(this
		.getClass());
	if (clazz.isAnnotation() || clazz.isEnum() || clazz.isArray()
		|| clazz.isPrimitive()) {
	    throw new IllegalArgumentException(
		    "The constructor argument must be a class or interface");
	}
	this.setSubjectClass(clazz);
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

    /**
     * @{inheritDoc
     */
    @SuppressWarnings("unchecked")
    @Override
    public T arbitrary(final Distribution random, final long minsize,
	    final long maxsize) {
	List<Class<?>> classes = null;
	List<Gen<?>> assignableGenerators = null;
	try {
	    classes = ClassSearcher
	    .getAssignableClasses(this.getSubjectClass());
	} catch (Exception e) {
	    throw new GenerationError(e);
	}
	if (classes != null && !classes.isEmpty()) {
	    // search declared generators for assignable classes
	    assignableGenerators = new ArrayList<Gen<?>>();
	    for (Class<?> assignableClass : classes) {
		// pick a random generator for the assignable (non-abstract)
		// class
		if (!Modifier.isAbstract(assignableClass.getModifiers())) {
		    Set<Gen<?>> set = Arbitrary
		    .getGeneratorsOf(assignableClass);
		    if (!set.isEmpty()) {
			assignableGenerators.addAll(set);
		    }
		}
	    }
	    if (assignableGenerators.size() > 0) {
		int index = (int) Arbitrary.choose(random, 0,
			assignableGenerators.size() - 1);
		return (T) assignableGenerators.get(index).arbitrary(random,
			minsize, maxsize);
	    } else { // creates a constructor based generator for any
		// non-abstract class
		// List<Class<?>> concreteSubclasses = new
		// ArrayList<Class<?>>();
		// for (Class<?> clazz : classes) {
		// if (clazz.isAnnotation() || clazz.isEnum()
		// || clazz.isArray() || clazz.isPrimitive()
		// || !Modifier.isAbstract(clazz.getModifiers())
		// && clazz.getConstructors().length > 0) {
		// concreteSubclasses.add(clazz);
		// }
		// }
		// if (concreteSubclasses.size() > 0) {
		// new ConstructorBasedGenerator(concreteSubclasses
		// .get((int) Arbitrary.choose(random, 0,
		// concreteSubclasses.size()))).arbitrary(
		// random, minsize, maxsize);
		// } else {
		throw new GenerationError(
		"No generators available for classes in the class hierarchy");
		// }
	    }
	} else {
	    throw new GenerationError(
	    "No subclasses defined for this class");
	}
    }

}
