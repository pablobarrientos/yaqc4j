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

package ar.edu.unlp.yaqc4j.runners.statements;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import junit.framework.AssertionFailedError;

import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import ar.edu.unlp.yaqc4j.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Classify;
import ar.edu.unlp.yaqc4j.annotations.Collect;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.annotations.IsSingleton;
import ar.edu.unlp.yaqc4j.annotations.UseGenerators;
import ar.edu.unlp.yaqc4j.collectors.Classifier;
import ar.edu.unlp.yaqc4j.collectors.ClassifyResults;
import ar.edu.unlp.yaqc4j.collectors.Collector;
import ar.edu.unlp.yaqc4j.exceptions.ImplicationFalseError;
import ar.edu.unlp.yaqc4j.exceptions.Yaqc4jAssertionFailedError;
import ar.edu.unlp.yaqc4j.generators.Arbitrary;
import ar.edu.unlp.yaqc4j.generators.ArrayGen;
import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.generators.singleton.SingletonGen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;
import ar.edu.unlp.yaqc4j.randoms.UniformDistribution;

/**
 * Represents one or more actions to be taken at runtime in the course of
 * running a JUnit test suite in yaqc4j. It implements many core functionalities
 * for this tool.
 * 
 * @author Pablo
 * 
 */
public class QCCheckStatement extends Statement {
	/**
	 * the configuration.
	 */
	protected Configuration configuration;

	/**
	 * the method.
	 */
	protected FrameworkMethod method;

	/**
	 * the test.
	 */
	protected Object test;

	/**
	 * the fromPositionGenerators.
	 */
	protected Map<Integer, Gen<?>> fromPositionGenerators = new HashMap<Integer, Gen<?>>();

	/**
	 * the fromClassGenerators.
	 */
	protected Map<Class<?>, Gen<?>> fromClassGenerators = new HashMap<Class<?>, Gen<?>>();

	/**
	 * the classGenerators.
	 */
	protected Map<Class<?>, Gen<?>> classGenerators = new HashMap<Class<?>, Gen<?>>();

	protected long maxsize;
	protected long minsize;
	protected int maxNumberOfTests;
	protected Class<?>[] paramClasses;
	
	@SuppressWarnings("rawtypes")
	protected ClassifyResults[] clasifiers;
	@SuppressWarnings("rawtypes")
	protected Collector[] collectors;

	protected Object[] paramList;
	protected int failed;
	
	/**
	 * Constructor.
	 * 
	 * @param theConfiguration
	 *            the configuration.
	 * @param theClassGenerators
	 *            the class generators.
	 * @param theMethod
	 *            the method.
	 * @param theTest
	 *            the test.
	 */
	public QCCheckStatement(final Configuration theConfiguration,
			final Map<Class<?>, Gen<?>> theClassGenerators,
			final FrameworkMethod theMethod, final Object theTest) {
		this.configuration = theConfiguration;
		this.classGenerators = theClassGenerators;
		this.method = theMethod;
		this.test = theTest;
		this.collectGenerators();
		this.init();
	}

	@SuppressWarnings("rawtypes")
	public ClassifyResults[] getClassifiers() {
		return clasifiers;
	}
	
	@SuppressWarnings("rawtypes")
	public Collector[] getcollectors() {
		return collectors;
	}

	/**
	 * @return
	 */
	public Object[] getParamList() {
		return paramList;
	}

	/**
	 * @return the failed
	 */
	public int getFailed() {
		return failed;
	}

	/**
	 * @return
	 */
	public int getMaxNumberOfTests() {
		return this.configuration.getMaxNumberOfTests();
	}

	/**
	 * @{inheritDoc
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public void evaluate() throws Throwable {

		paramList = new Object[paramClasses.length];

		// If this is a test that has no parameters, only
		// run it once as JUnit normally does
		if (paramClasses.length == 0) {
			this.method.invokeExplosively(this.test, paramList);
			return;
		}

		// run the test trying to generate the proper arguments up to configuration
		// configuration.getMaxNumberOfFailedParams() times.
		for (failed = 1;; ++failed) {
			for (int pNo = 0; pNo < paramClasses.length; ++pNo) {
				//generate random value
				paramList[pNo] = this.arbitrary(paramClasses[pNo], pNo,
						this.configuration.getRandom(), minsize, maxsize);
				//collect and classify
				if (clasifiers[pNo] != null){
					clasifiers[pNo].classify(paramList[pNo]);
				}
				if (collectors[pNo] != null){
					collectors[pNo].collect(paramList[pNo]);
				}
			}
			try {
				this.method.invokeExplosively(this.test, paramList);
				break;
			} catch (ImplicationFalseError ex) {
				if (failed >= this.configuration.getMaxNumberOfFailedParams()) {
					throw new AssertionFailedError(String.format(
							"Arguments exhausted after (%d tries). %s", failed,
							ex.getMessage()));
				}
			} catch (AssertionError ex) {
				if (this.method.getAnnotation(Test.class) != null
						&& !this.method.getAnnotation(Test.class).expected()
								.isAssignableFrom(AssertionError.class)) {
					// if (failed++ >= maxNumberOfTests - 1)
					// throw new AssertionError();
					// break;
					// } else
					if (paramList.length > 0) {
						throw new Yaqc4jAssertionFailedError(paramList, failed,
								ex);
					}
				} else {
					throw ex;
				}
			 }
		}
	}

	/**
	 * @return
	 */
	protected void init() {
		maxNumberOfTests = this.configuration.getMaxNumberOfTests();
		maxsize = this.configuration.getMaxSize();
		minsize = this.configuration.getMinsize();

		ar.edu.unlp.yaqc4j.annotations.Configuration configAnnotation = this.method
				.getAnnotation(ar.edu.unlp.yaqc4j.annotations.Configuration.class);
		if (configAnnotation != null) {
			if (configAnnotation.maxsize() != Long.MAX_VALUE) {
				maxsize = configAnnotation.maxsize();
			}
			if (configAnnotation.minsize() != Long.MIN_VALUE) {
				minsize = configAnnotation.minsize();
			}
			if (configAnnotation.tests() != 0) {
				maxNumberOfTests = configAnnotation.tests();
			} else if (this.method.getMethod().getParameterTypes().length == 0){
				maxNumberOfTests = 1;
			}
		} else {
			if (this.method.getMethod().getParameterTypes().length == 0){
				maxNumberOfTests = 1;
			}
		}

		paramClasses = this.method.getMethod().getParameterTypes();
		Type[] genericParameterTypes = this.method.getMethod()
				.getGenericParameterTypes();
		for (int i = 0; i < genericParameterTypes.length; i++) {
			if (paramClasses[i]
					.isAssignableFrom(java.lang.reflect.TypeVariable.class)){
				paramClasses[i] = (Class<?>) Arbitrary
						.getSuperclassTypeParameter(this.test.getClass());
			}
			// verifies if there are singleton types annotated with @IsSingleton
			int index = 0; 
			for (Annotation[] annotations:this.method.getMethod().getParameterAnnotations()){
				for (Annotation annotation:annotations){
					if (annotation.annotationType().equals(IsSingleton.class)){
						IsSingleton singletonAnnotation = (IsSingleton) annotation;
						Class<?> clazz = method.getMethod().getParameterTypes()[index];
						@SuppressWarnings({ "rawtypes", "unchecked" })
						SingletonGen<?> generator = new SingletonGen(singletonAnnotation.singletonMethodName(), singletonAnnotation.fieldName(), clazz){
						};
						this.fromClassGenerators.put(clazz, generator);
					}
				}
				index++;
			}
			
		}
		createClassifiers();
		createCollector();
	}

	/**
	 * Reads the generators that will be used, by looking the annotations.
	 */
	protected void collectGenerators() {
		UseGenerators useGenerators = this.method
				.getAnnotation(UseGenerators.class);
		if (useGenerators != null) {
			for (Generator generator : useGenerators.value()) {
				this.collectGenerator(generator);
			}
		}
		Generator generator = this.method.getAnnotation(Generator.class);
		this.collectGenerator(generator);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void createClassifiers() {
		clasifiers = new ClassifyResults[this.method.getMethod().getParameterAnnotations().length];
		int index = 0;
		for (Annotation[] annotations:this.method.getMethod().getParameterAnnotations()){
			for (Annotation annotation:annotations){
				if (annotation.annotationType().equals(Classify.class)){
					Classify collectAnnotation = (Classify) annotation;
					Classifier[] classifiers = new Classifier[collectAnnotation.classifiers().length];
					int i = 0;
					for (Class<? extends Classifier> classifierClass: collectAnnotation.classifiers()){
						try {
							classifiers[i] = classifierClass.newInstance();
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						i++;
					}
					clasifiers[index] = new ClassifyResults(collectAnnotation.name(), classifiers);
				}
			}
			index++;
		}
	}
	
	/**
	 * 
	 */
	protected void createCollector() {
		collectors = new Collector[this.method.getMethod().getParameterAnnotations().length];
		int index = 0;
		for (Annotation[] annotations:this.method.getMethod().getParameterAnnotations()){
			for (Annotation annotation:annotations){
				if (annotation.annotationType().equals(Collect.class)){
					Collect collectAnnotation = (Collect) annotation;
					try {
						collectors[index] = collectAnnotation.collector().newInstance();
						collectors[index].setName(collectAnnotation.name());
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
			index++;
		}
	}

	/**
	 * Reads the generator annotation.
	 * 
	 * @param generator
	 *            the generator.
	 */
	protected void collectGenerator(final Generator generator) {
		if (generator != null) {
			if (generator.position() < 0) {
				Class<?> klass = generator.klass();
				if (klass == null) {
					throw new RuntimeException("Bad @Generator found.");
				}
				Class<? extends Gen<?>> genClass = generator.generator();
				try {
					Gen<?> gen = genClass.newInstance();
					this.fromClassGenerators.put(klass, gen);
				} catch (Exception ex) {
					throw new RuntimeException(String.format(
							"Unable to instantiate generator %s.",
							genClass.getName()), ex);
				}
			} else {
				Integer position = generator.position();
				if (position >= this.method.getMethod().getParameterTypes().length) {
					throw new RuntimeException(
							String.format(
									"Position %d given for a generator is greater than the number of arguments to %s.",
									position, this.method.getName()));
				}

				Class<? extends Gen<?>> genClass = generator.generator();
				try {
					Gen<?> gen = genClass.newInstance();
					this.fromPositionGenerators.put(position, gen);
				} catch (Exception ex) {
					throw new RuntimeException(String.format(
							"Unable to instantiate generator %s.",
							genClass.getName()), ex);
				}
			}
		}
	}

	/**
	 * Generates an arbitrary instance using declared and standard generators.
	 * 
	 * @param paramClass
	 *            the class
	 * @param position
	 *            the position
	 * @param random
	 *            the random
	 * @param maxsize
	 *            the size
	 * @return the object
	 */
	protected Object arbitrary(final Class<?> paramClass, final int position,
			final Distribution random, final long minsize, final long maxsize) {
		Gen<?> generator = this.generator(paramClass, position);
		return generator.arbitrary(random, minsize, maxsize);
	}

	/**
	 * Look up the generator for the given class, in the given position.
	 * 
	 * @param paramClass
	 *            the class.
	 * @param position
	 *            the position.
	 * @return the generator
	 */
	protected Gen<?> generator(final Class<?> paramClass, final int position) {
		Gen<?> generator = this.fromPositionGenerators.get(position);
		if (generator != null) {
			return generator;
		}
		generator = this.fromClassGenerators.get(paramClass);
		if (generator != null) {
			return generator;
		}
		generator = this.classGenerators.get(paramClass);
		if (generator != null) {
			return generator;
		}
		if (paramClass.isArray()) {
			return this.generatorForArray(paramClass, position);
		}
		generator = Arbitrary.getGeneratorFor(new UniformDistribution(),
				paramClass);
		if (generator != null) {
			return generator;
		} else {
			throw new RuntimeException(String.format(
					"Unable to find a matching generator for class %s.",
					paramClass.getName()));
		}
	}

	/**
	 * creates an array generator containing elements of the given class.
	 * 
	 * @param paramClass
	 *            the class.
	 * @param position
	 *            the position.
	 * @return the generator
	 * 
	 */
	protected Gen<?> generatorForArray(final Class<?> paramClass,
			final int position) {
		int dimension = 0;
		Class<?> component = paramClass;
		while (true) {
			Class<?> tmp = component.getComponentType();
			if (tmp != null) {
				dimension++;
				component = tmp;
			} else {
				break;
			}
		}
		return new ArrayGen(dimension, component, this.generator(component,
				position));
	}
}
