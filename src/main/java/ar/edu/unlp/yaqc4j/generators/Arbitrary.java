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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.unlp.yaqc4j.exceptions.GenerationError;
import ar.edu.unlp.yaqc4j.generators.java.lang.AlphaNumericStringGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.BooleanGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.ByteGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.CharacterGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.DoubleGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.FloatGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.FloatSimpleGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.LongGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.ShortGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.StringBufferGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.StringBuilderGen;
import ar.edu.unlp.yaqc4j.generators.java.math.BigDecimalGen;
import ar.edu.unlp.yaqc4j.generators.java.math.BigIntegerGen;
import ar.edu.unlp.yaqc4j.generators.java.util.DateGen;
import ar.edu.unlp.yaqc4j.generators.java.util.ListGen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;
import ar.edu.unlp.yaqc4j.randoms.UniformDistribution;
import ar.edu.unlp.yaqc4j.util.ClassSearcher;

/**
 * Utility class that helps in creating new generators and instances using known
 * (registered) generators.
 * 
 * @author Pablo
 * 
 */
public final class Arbitrary {
	/**
	 * the integerGen.
	 */
	private static Gen<Integer> integerGen;

	/**
	 * the customGenerators.
	 */
	private static Map<Class<? extends Object>, Set<Gen<?>>> customGenerators;

	/**
	 * Private Constructor.
	 */
	private Arbitrary() {
	}

	/* static block */
	static {
		reset();
	}

	/**
	 * Reset status.
	 */
	public static final void reset(){
		setCustomGenerators(new HashMap<Class<? extends Object>, Set<Gen<?>>>());
		initializeBasicGenerators();
		integerGen = new PositiveIntegerSimpleGen();
	}

	/**
	 * Generates an instance of the class using a randomly selected known
	 * generator. No limit size is assume.
	 * 
	 * @param <T>
	 *            the type of the object being generated.
	 * @param random
	 *            the random.
	 * @param aClass
	 *            the class of the object being generated.
	 * @return the object generated.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstanceOf(final Distribution random,
			final Class<T> aClass) {
		Gen<T> generator = (Gen<T>) getGeneratorFor(random, aClass);
		// both max and min parameters could be inappropriate arguments
		T o = generator.arbitrary(random,
				Long.MIN_VALUE, Long.MAX_VALUE);
		return generator == null ? null : o;
	}

	/**
	 * Registers a new generator for a class.
	 * 
	 * @param gen
	 *            the generator
	 * @param class1
	 *            the class for which the generator generates the instances
	 */
	public static void registerFor(final Gen<?> gen, final Class<?> class1) {
		getSetOf(class1).add(gen);
	}

	/**
	 * Registers a new generator for a class. If another generator was
	 * registered, this one is ignored.
	 * 
	 * @param gen
	 *            the generator
	 * @param class1
	 *            the class for which the generator generates the instances
	 */
	private static void basicRegisterFor(final Gen<?> gen, final Class<?> class1) {
		if (getSetOf(class1).isEmpty()) {
			getSetOf(class1).add(gen);
		}
	}

	/**
	 * Registers a new generator for a class and all its super classes.
	 * 
	 * @param gen
	 *            the generator
	 * @param class1
	 *            the class for which the generator generates the instances
	 */
	public static void registerForInHierarchy(final Gen<?> gen,
			final Class<?> class1) {
		getSetOf(class1).add(gen);
		if (!class1.getSuperclass().equals(Object.class)) {
			registerForInHierarchy(gen, class1.getSuperclass());
		}
	}

	/**
	 * Registers a @see{ar.edu.unlp.yaqc4j.generatorsConstructorBasedGenerator}
	 * for the given class (and all its super classes).
	 * 
	 * @param aClass
	 *            the class
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void registerConstructorGeneratorFor(
			final Class<? extends Object> aClass) {
		registerForInHierarchy(new ConstructorBasedGen(aClass), aClass);
	}

	/**
	 * Initializes all basic known generators.
	 */
	private static void initializeBasicGenerators() {
		// primitive types can be used because wrappers generator do not return
		// null values
		basicRegisterFor(new ByteGen(), Byte.class);
		basicRegisterFor(new ByteGen(), byte.class);
		basicRegisterFor(new ShortGen(), Short.class);
		basicRegisterFor(new ShortGen(), short.class);
		// basicRegisterFor(new IntegerGen(), Integer.class);
		// basicRegisterFor(new IntegerGen(), int.class);
		basicRegisterFor(new CharacterGen(), Character.class);
		basicRegisterFor(new CharacterGen(), char.class);
		basicRegisterFor(new BooleanGen(), Boolean.class);
		basicRegisterFor(new BooleanGen(), boolean.class);
		basicRegisterFor(new DoubleGen(), Double.class);
		basicRegisterFor(new DoubleGen(), double.class);
		basicRegisterFor(new FloatGen(), Float.class);
		basicRegisterFor(new FloatGen(), float.class);
		basicRegisterFor(new LongGen(), Long.class);
		basicRegisterFor(new LongGen(), long.class);
		basicRegisterFor(new BigDecimalGen(), BigDecimal.class);
		basicRegisterFor(new BigIntegerGen(), BigInteger.class);
		basicRegisterFor(new DateGen(), Date.class);
		basicRegisterFor(new AlphaNumericStringGen(), String.class);
		basicRegisterFor(new StringBuilderGen(), StringBuilder.class);
		basicRegisterFor(new StringBufferGen(), StringBuffer.class);
		// basicRegisterFor(new VoidGen(), Void.class);
		basicRegisterFor(new ListGen<Float>(new FloatSimpleGen(), 0, 10),
				Collection.class);
		// registerFor(new ObjectGen(), Object.class);

	}

	/**
	 * Returns a random generated object.
	 * 
	 * @param random
	 *            the random.
	 * @return the generated object.
	 */
	public static Object getRandomObject(final Distribution random) {
		return getRandomObjectOf(random, getGenerableClasses());
	}

	/**
	 * Generates a random object that is instance of the classes in the set.
	 * 
	 * @param random
	 *            the random.
	 * @param generableClasses
	 *            the set of classes.
	 * @return the generated object.
	 */
	private static Object getRandomObjectOf(final Distribution random,
			final List<Class<?>> generableClasses) {
		return getRandomObjectSkipping(random, generableClasses,
				new ArrayList<Class<?>>());
	}

	/**
	 * generates a random object.
	 * 
	 * @param random
	 *            the random.
	 * @return the object generated.
	 */
	public static Object getNonBaseRandomObject(final Distribution random) {
		return getRandomObjectSkippingPackages(random, new String[] {
				"java.lang", "java.util", "java.math", "java.text",
				"ar.edu.unq.yaqc4j" });
	}

	/**
	 * Selects an instance from a class in the array of packages.
	 * 
	 * @param random
	 *            the random
	 * @param packages
	 *            the array of packages.
	 * @return the object generated
	 */
	private static Object getRandomObjectSkippingPackages(
			final Distribution random, final String[] packages) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (Class<?> aClass : getGenerableClasses()) {
			if (!classInPackage(aClass, packages) && !aClass.isPrimitive()) {
				classes.add(aClass);
			}
		}
		return getRandomObjectOf(random, classes);

	}

	/**
	 * Indicates is aClass is part of any of the packages in packages.
	 * 
	 * @param aClass
	 *            the class.
	 * @param packages
	 *            the array of packages.
	 * @return a flag
	 */
	private static boolean classInPackage(final Class<?> aClass,
			final String[] packages) {
		for (String aPackageName : packages) {
			if (aClass.getName().startsWith(aPackageName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns an object from includedClasses, skipping the ones in
	 * skippedClasses.
	 * 
	 * @param random
	 *            the random.
	 * @param includedClasses
	 *            all possible objects that can be selected.
	 * @param skippedClasses
	 *            all object that should not be included as a result.
	 * @return an object included in the includedClasses collection.
	 */
	private static Object getRandomObjectSkipping(final Distribution random,
			final Collection<Class<?>> includedClasses,
			final Collection<Class<?>> skippedClasses) {
		Set<Class<?>> classes = new HashSet<Class<?>>(includedClasses);
		classes.removeAll(skippedClasses);
		Class<?> randomClass = (Class<?>) getRandomObjectOn(random, classes);
		return getInstanceOf(random, randomClass);
	}

	/**
	 * Selects randomly an object included in the collection.
	 * 
	 * @param random
	 *            the random.
	 * @param objects
	 *            The collection.
	 * @return the selected object.
	 * 
	 */
	public static Object getRandomObjectOn(final Distribution random,
			final Collection<?> objects) {
		List<Object> objectsList = new ArrayList<Object>(objects);
		Integer index = integerGen.arbitrary(random, 0, objects.size() - 1);
		return objectsList.get(index);
	}

	/**
	 * Return all known classes for which a generator is used to create
	 * instances.
	 * 
	 * @return the set of all generable classes.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Class<? extends Object>> getGenerableClasses() {
		List<Class<? extends Object>> result = new ArrayList();
		result.addAll(getCustomGenerators().keySet());
		return result;
	}

	/**
	 * Returns all generators for this class.
	 * 
	 * @param aClass
	 *            the class.
	 * @return a set of all generators for this class.
	 */
	public static Set<Gen<?>> getGeneratorsOf(
			final Class<? extends Object> aClass) {
		Set<Gen<?>> validGeneratorsForClass = new HashSet<Gen<?>>();
		validGeneratorsForClass.addAll(getSetOf(aClass));
		for (Gen<?> generator : getEquivalentSetsOf(aClass)) {
			validGeneratorsForClass.add(generator);
		}
		return validGeneratorsForClass;
	}

	/**
	 * Returns a set of all generators that can be used for instances
	 * generations of the given class.
	 * 
	 * @param aClass
	 *            the class.
	 * @return the collection of all generators.
	 */
	private static Collection<Gen<?>> getEquivalentSetsOf(
			final Class<? extends Object> aClass) {
		Collection<Gen<?>> equivalents = new ArrayList<Gen<?>>();
		for (Class<?> classItem : getCustomGenerators().keySet()) {
			if (aClass.isAssignableFrom(classItem)) {
				equivalents.addAll(getCustomGenerators().get(classItem));
			}
		}
		return equivalents;
	}

	/**
	 * Returns all generators known for this class.
	 * 
	 * @param aClass
	 *            the class.
	 * @return the set of all generators
	 */
	private static Set<Gen<?>> getSetOf(final Class<?> aClass) {
		Set<Gen<?>> generators = getCustomGenerators().get(aClass);
		if (generators == null) {
			generators = new HashSet<Gen<?>>();
			getCustomGenerators().put(aClass, generators);
		}
		return generators;
	}

	/**
	 * Returns a randomly selected generator for the class or a
	 * 
	 * @see{ar.edu.unlp.yaqc4j.generators.ConstructorBasedGenerator if no
	 *                                                             generator was
	 *                                                             specified.
	 * @param random
	 *            the random.
	 * @param aClass
	 *            should not be null.
	 * @return the class instances generator.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Gen<?> getGeneratorFor(final Distribution random,
			final Class<?> aClass) {
		if (aClass == null) {
			throw new IllegalArgumentException("Class is null");
		}
		List<Class<?>> classes = null;
		List<Gen<?>> assignableGenerators = null;
		Set<Gen<?>> generators = getGeneratorsOf(aClass);
		if (!generators.isEmpty()) {// some generators for the class were found
			if (generators.size() == 1) {
				return generators.iterator().next();
			} else { // choose among all declared generators
				int index = (int) choose(random, 0, generators.size() - 1);
				return new ArrayList<Gen<?>>(generators).get(index);
			}
		} else {
			// tries to find generators for assignable classes.
			try {
				classes = ClassSearcher.getAssignableClasses(aClass);
			} catch (Exception e) {
			}
			if (classes != null && !classes.isEmpty()) {
				// search declared generators for assignable classes
				assignableGenerators = new ArrayList<Gen<?>>();
				for (Class<?> assignableClass : classes) {
					assignableGenerators
							.addAll(getGeneratorsOf(assignableClass));
				}
				if (assignableGenerators.size() > 0) {
					int index = (int) Arbitrary.choose(random, 0,
							assignableGenerators.size());
					return assignableGenerators.get(index);
				}
			}
			// returns a custom generator for classes or interfaces
			if (aClass.isInterface()) {
				return new InterfaceInstancesGen(aClass);
				// throw new GenerationException(
				// "No generator has been defined for " + aClass.getName());
			} else if (Modifier.isAbstract(aClass.getModifiers())) {
				throw new GenerationError(
						"The class " + aClass.getName() + " is abstract and no generator could be found for it or its subclasses.");
			} else {
				return new ConstructorBasedGen(aClass);
			}
		}
	}

	/**
	 * Returns all known generators that uses this generator.
	 * 
	 * @return the custom generators.
	 */
	private static Map<Class<? extends Object>, Set<Gen<?>>> getCustomGenerators() {
		return customGenerators;
	}

	/**
	 * Setter for all known generators.
	 * 
	 * @param theCustomGenerators
	 *            the map of classes and associated generators.
	 */
	private static void setCustomGenerators(
			final Map<Class<? extends Object>, Set<Gen<?>>> theCustomGenerators) {
		customGenerators = theCustomGenerators;
	}

	/**
	 * Returns a class randomly selected generator from a registered classes.
	 * 
	 * @return a class.
	 */
	public static Gen<?> getRandomClassGenerator() {
		return getGeneratorFor(defaultDistribution(), getRandomGenerableClass());
	}

	/**
	 * Returns a class randomly selected from the registered classes.
	 * 
	 * @return a class.
	 */
	public static Class<?> getRandomGenerableClass() {
		List<Class<?>> classSet = getGenerableClasses();
		int index = (int) choose(defaultDistribution(), 0L, classSet.size() - 1);
		Iterator<Class<?>> it = (Iterator<Class<?>>) classSet.iterator();
		Class<?> clazz = null;
		for (int i = 0; i < index; i++) {
			clazz = it.next();
		}
		return clazz;
	}

	/**
	 * Generate a random long in the interval [from - to].
	 * 
	 * @param random
	 *            the random.
	 * @param from
	 *            the lower limit.
	 * @param to
	 *            the upper limit.
	 * @return a random long
	 */
	public static long choose(final Distribution random, final long from,
			final long to) {
		if (from > to) {
			throw new RuntimeException(
					"Lower limit is greater that upper limit. Please check parameters.");
		}
		BigDecimal i = new BigDecimal(from);
		BigDecimal j = new BigDecimal(to);
		BigDecimal k = j.subtract(i);
		BigDecimal l = new BigDecimal(Math.abs(random.nextRandomNumber()));
		return k.multiply(l).add(i).setScale(0, RoundingMode.HALF_EVEN).longValue();
	}

	/**
	 * Getter of default random element.
	 * 
	 * @return a random.
	 */
	public static Distribution defaultDistribution() {
		return new UniformDistribution();
	}

	/**
	 * Gives the parameter type for this class, if possible. This method is
	 * public so you can define generic generators that know in runtime the
	 * parameterized class.
	 * 
	 * @param class1
	 *            the class.
	 * @return the type.
	 */
	public static Type getSuperclassTypeParameter(final Class<?> class1) {
		Type type = class1.getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			// the type has no parameters, so a random class is selected
			return getRandomGenerableClass();
		}
		Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
		return typeArgument;
	}

}
