package ar.edu.unlp.yaqc4j;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.generators.java.lang.IntegerSimpleGen;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;
import ar.edu.unlp.yaqc4j.samples.A;
import ar.edu.unlp.yaqc4j.samples.C;
import ar.edu.unlp.yaqc4j.samples.generators.CGen;

/**
 * @author Pablo
 * 
 */
@RunWith(QCCheckRunner.class)
@Configuration(maxsize = 50)
@Generator(generator = IntegerSimpleGen.class, klass = int.class)
public class HierarchySampleTest {
	/**
	 * No explicit generator was defined for b, but subclass C has an associated
	 * generator
	 * 
	 * @param i
	 *            some integer.
	 */
	@Test
	@Generator(generator = CGen.class, klass = C.class)
	public void aIsNotNull(final A a) {
		assertNotNull(a);
	}
}
