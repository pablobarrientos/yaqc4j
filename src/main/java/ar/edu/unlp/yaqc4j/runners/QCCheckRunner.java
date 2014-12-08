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

package ar.edu.unlp.yaqc4j.runners;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Test.None;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import ar.edu.unlp.yaqc4j.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.annotations.UseGenerators;
import ar.edu.unlp.yaqc4j.collectors.ClassifyResults;
import ar.edu.unlp.yaqc4j.collectors.Collector;
import ar.edu.unlp.yaqc4j.exceptions.Yaqc4jAssertionFailedError;
import ar.edu.unlp.yaqc4j.generators.Arbitrary;
import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.runners.statements.QCCheckExpectException;
import ar.edu.unlp.yaqc4j.runners.statements.QCCheckStatement;

/**
 * Test case class model in yacq4j. It implements many core functionalities of
 * this tool. You must use this runner for using all features of YAQC4J.
 * 
 * @author Pablo
 * 
 */
public class QCCheckRunner extends BlockJUnit4ClassRunner {

	public static final int DEFAULT_NUMBER_OF_RUNS = 100;

	/**
	 * the arbitrary MAX_ARGUMENTS_FAILS value.
	 */
	public static final int MAX_ARGUMENTS_FAILS = 100;

	/**
	 * the configuration.
	 */
	private Configuration configuration;

	/**
	 * the classGenerators.
	 */
	private Map<Class<?>, Gen<?>> classGenerators;

	/**
	 * the test object.
	 */
	Object test;

	/**
	 * Constructor.
	 * 
	 * @param klass
	 *            the class.
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InitializationError .
	 * 
	 */
	public QCCheckRunner(final Class<?> klass) throws InitializationError,
			InstantiationException, IllegalAccessException {
		super(klass);
		this.processClassEnvironment();
	}

	/**
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void processClassEnvironment() throws InstantiationException,
			IllegalAccessException {
		ar.edu.unlp.yaqc4j.annotations.Configuration config = this
				.getClassAnnotation(ar.edu.unlp.yaqc4j.annotations.Configuration.class);
		if (config != null) {
			this.configuration = new Configuration(config.distribution()
					.newInstance());
			if (config.maxsize() != Long.MAX_VALUE) {
				this.configuration.setMaxSize(config.maxsize());
			}
			if (config.minsize() != Long.MIN_VALUE) {
				this.configuration.setMinsize(config.minsize());
			}

			if (config.tests() != DEFAULT_NUMBER_OF_RUNS) {
				this.configuration.setMaxNumberOfTests(config.tests());
			}
			if (config.maxArgumentsFails() != MAX_ARGUMENTS_FAILS) {
				this.configuration.setMaxNumberOfFailedParams(config
						.maxArgumentsFails());
			}
		} else {
			this.configuration = new Configuration(
					Arbitrary.defaultDistribution());
		}
		// collect declared generators.
		this.classGenerators = new HashMap<Class<?>, Gen<?>>();
		Generator generator = this.getClassAnnotation(Generator.class);
		if (generator != null) {
			this.addGenerator(generator);
		}
		UseGenerators useGenerators = this
				.getClassAnnotation(UseGenerators.class);
		if (useGenerators != null) {
			for (Generator gen : useGenerators.value()) {
				this.addGenerator(gen);
			}
		}
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected final void validateTestMethods(final List<Throwable> errors) {
		List<FrameworkMethod> methods = this.getTestClass()
				.getAnnotatedMethods(Test.class);
		for (FrameworkMethod eachTestMethod : methods) {
			eachTestMethod.validatePublicVoid(false, errors);
		}
	}

	/**
	 * Getter of class annotation in the class.
	 * 
	 * @param <T>
	 *            the annotation type.
	 * @param annotation
	 *            the annotation.
	 * @return the annotation.
	 */
	@SuppressWarnings("unchecked")
	private <T extends Annotation> T getClassAnnotation(
			final Class<T> annotation) {
		Annotation[] classAnnotations = this.getTestClass().getAnnotations();
		for (Annotation classAnnotation : classAnnotations) {
			if (classAnnotation.annotationType() == annotation) {
				return (T) classAnnotation;
			}
		}
		return null;
	}

	/**
	 * Adds a generator.
	 * 
	 * @param generator
	 *            the generator.
	 */
	private void addGenerator(final Generator generator) {
		Class<?> klass = generator.klass();
		Class<? extends Gen<?>> genClass = generator.generator();
		if (klass == null || genClass == null) {
			throw new RuntimeException("Bad @Generator found.");
		}
		if (this.classGenerators.containsKey(klass)) {
			throw new RuntimeException("Duplicate generators found.");
		}
		try {
			Gen<?> gen = genClass.newInstance();
			this.classGenerators.put(klass, gen);
			// the constructor is also registered in Arbitrary
			Arbitrary.registerFor(gen, klass);
		} catch (InstantiationException ex) {
			throw new RuntimeException(String.format(
					"Unable to instantiate generator %s.", genClass.getName()),
					ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(String.format(
					"Unable to instantiate generator %s.", genClass.getName()),
					ex);
		}
	}

	/**
	 * Adds a generator.
	 * 
	 * @param generator
	 *            the generator.
	 */
	private void addGenerator(final Generator generator,
			final Map<Class<?>, Gen<?>> generators) {
		Class<?> klass = generator.klass();
		Class<? extends Gen<?>> genClass = generator.generator();
		if (klass == null || genClass == null) {
			throw new RuntimeException("Bad @Generator found.");
		}
		if (generators.containsKey(klass)) {
			throw new RuntimeException("Duplicate generators found.");
		}
		try {
			Gen<?> gen = genClass.newInstance();
			generators.put(klass, gen);
			// is it right???
			Arbitrary.registerFor(gen, klass);
		} catch (InstantiationException ex) {
			throw new RuntimeException(String.format(
					"Unable to instantiate generator %s.", genClass.getName()),
					ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(String.format(
					"Unable to instantiate generator %s.", genClass.getName()),
					ex);
		}
	}

	private EachTestNotifier makeNotifier(FrameworkMethod method,
			RunNotifier notifier) {
		Description description = describeChild(method);
		return new EachTestNotifier(notifier, description);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		EachTestNotifier eachNotifier = makeNotifier(method, notifier);
		if (method.getAnnotation(Ignore.class) != null) {
			eachNotifier.fireTestIgnored();
			return;
		}
		eachNotifier.fireTestStarted();
		try {
			try {
				test = new ReflectiveCallable() {
					@Override
					protected Object runReflectiveCall() throws Throwable {
						return createTest();
					}
				}.run();
			} catch (Throwable e) {
				new Fail(e).evaluate();
			}
			QCCheckStatement qcStatement = methodInvoker(method, test);
			Statement statement = methodBlock(method, qcStatement);
//			if (method.getMethod().getParameterTypes().length == 0) {
//				// If this is a test that has no parameters, only
//				// run it once as JUnit normally does
//				statement.evaluate();
//				System.out.println(method.getName() + ": OK. Passed 1 tests.");
//			} else {
				for (int i = 0; i < qcStatement.getMaxNumberOfTests(); i++) {
					try {
						statement.evaluate();
					} catch(Yaqc4jAssertionFailedError failure){
						System.out.println(method.getName() + " failed. " + failure.getMessage());
						throw failure;
					} catch(AssertionError failure){
						System.out.println(method.getName() + ": Failed after " + (i + 1) + " tests." + failure.getMessage());
						throw failure;
					}
				}
				for (Collector collector: qcStatement.getcollectors()){
					if (collector != null){
						System.out.println(collector.printResults());
					}
				}
				for (ClassifyResults classifiers: qcStatement.getClassifiers()){
					if (classifiers != null){
						System.out.println(classifiers.printResults());
					}
				}
				System.out.println(method.getName() + ": OK. Passed " + qcStatement.getMaxNumberOfTests() + " tests.");
//			}
		} catch (AssumptionViolatedException e) {
			eachNotifier.addFailedAssumption(e);
		} catch (Throwable e) {
			eachNotifier.addFailure(e);
		} finally {
			eachNotifier.fireTestFinished();
		}
	}

	@SuppressWarnings("deprecation")
	protected Statement methodBlock(FrameworkMethod method, Statement statement) {
		Statement resStatement = possiblyExpectingExceptions(method, test, statement);
		resStatement = withPotentialTimeout(method, test, resStatement);
		resStatement = withBefores(method, test, resStatement);
		resStatement = withAfters(method, test, resStatement);
		return resStatement;
	}

    protected Statement possiblyExpectingExceptions(FrameworkMethod method,
            Object test, Statement next) {
        Test annotation = method.getAnnotation(Test.class);
        return expectsException(annotation) ? new QCCheckExpectException(next,
                getExpectedException(annotation)) : next;
    }
    
    private Class<? extends Throwable> getExpectedException(Test annotation) {
        if (annotation == null || annotation.expected() == None.class) {
            return null;
        } else {
            return annotation.expected();
        }
    }
    
    private boolean expectsException(Test annotation) {
        return getExpectedException(annotation) != null;
    }
    
	/**
	 * @{inheritDoc
	 */
	@Override
	protected final QCCheckStatement methodInvoker(
			final FrameworkMethod method, final Object test) {
		try {
			Arbitrary.reset();
			processClassEnvironment();
			// config annotation process
			Configuration conf = (Configuration) this.configuration.clone();
			if (method
					.getAnnotation(ar.edu.unlp.yaqc4j.annotations.Configuration.class) != null) {
				ar.edu.unlp.yaqc4j.annotations.Configuration conf2 = method
						.getAnnotation(ar.edu.unlp.yaqc4j.annotations.Configuration.class);
				if (conf.getMaxNumberOfFailedParams() == QCCheckRunner.MAX_ARGUMENTS_FAILS
						&& conf2.maxArgumentsFails() != QCCheckRunner.MAX_ARGUMENTS_FAILS) {
					conf.setMaxNumberOfFailedParams(conf2.maxArgumentsFails());
				}
				if (conf2.tests() != DEFAULT_NUMBER_OF_RUNS) {
					conf.setMaxNumberOfTests(conf2.tests());
				} else if (method.getMethod().getParameterTypes().length == 0){
					conf.setMaxNumberOfTests(DEFAULT_NUMBER_OF_RUNS);
				}
				if (conf.getMaxSize() == Long.MAX_VALUE
						&& conf2.maxsize() != Long.MAX_VALUE) {
					conf.setMaxSize(conf2.maxsize());
				}
				if (conf.getMinsize() == Long.MIN_VALUE
						&& conf2.minsize() != Long.MIN_VALUE) {
					conf.setMinsize(conf2.minsize());
				}
				if (conf.getRandom().getClass()
						.equals(Arbitrary.defaultDistribution().getClass())
						&& conf2.distribution() != null) {
					conf.setDistribution(conf2.distribution().newInstance());
				}
			} else {
				if (method.getMethod().getParameterTypes().length == 0){
					conf.setMaxNumberOfTests(1);
				}
			}

			// custom generators in the method
			Map<Class<?>, Gen<?>> generators = new HashMap<Class<?>, Gen<?>>();
			generators.putAll(this.classGenerators);
			if (method
					.getAnnotation(ar.edu.unlp.yaqc4j.annotations.Generator.class) != null) {
				generators
						.remove(method.getAnnotation(
								ar.edu.unlp.yaqc4j.annotations.Generator.class)
								.klass());
				this.addGenerator(
						method.getAnnotation(ar.edu.unlp.yaqc4j.annotations.Generator.class),
						generators);
			}
			UseGenerators useGenerators = method
					.getAnnotation(ar.edu.unlp.yaqc4j.annotations.UseGenerators.class);
			if (useGenerators != null) {
				for (Generator gen : useGenerators.value()) {
					generators.remove(gen.klass());
					this.addGenerator(gen, generators);
				}
			}

			return new QCCheckStatement(conf, generators, method, test);
		} catch (Exception ex) {
			throw new RuntimeException(
					"Unable to reconfigure the test configuration.", ex);
		}
	}
}
