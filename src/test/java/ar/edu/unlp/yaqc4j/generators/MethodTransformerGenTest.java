/**
 * 
 */
package ar.edu.unlp.yaqc4j.generators;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;
import ar.edu.unlp.yaqc4j.samples.MethodTransformerSampleGen;

/**
 * @author pbarrientos
 *
 */
@RunWith(QCCheckRunner.class)
@Configuration(tests = 10000)
public class MethodTransformerGenTest {
	@Test
	@Configuration(maxsize=10000)
	@Generator(klass = int.class, generator = MethodTransformerSampleGen.class)
	public void testGeneratorMax(int i) {
		Assert.assertTrue(i >= 0);
		Assert.assertTrue(i <= 10000);
	}
	
	@Test
	@Configuration(maxsize=10000)
	@Generator(klass = int.class, generator = MethodTransformerSampleGen.class)
	public void testGeneratorMin(int i) {
		Assert.assertTrue(i >= 0);
		Assert.assertTrue(i <= 10000);
	}
}
