package ar.edu.unlp.yaqc4j;

import java.io.Serializable;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.annotations.UseGenerators;
import ar.edu.unlp.yaqc4j.generators.java.io.SerializableGen;
import ar.edu.unlp.yaqc4j.generators.java.lang.IntegerSimpleGen;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;
import ar.edu.unlp.yaqc4j.samples.A;
import ar.edu.unlp.yaqc4j.samples.C;
import ar.edu.unlp.yaqc4j.samples.generators.CGen;
import ar.edu.unlp.yaqc4j.samples.generators.OneSingletonGen;
import ar.edu.unlp.yaqc4j.samples.singleton.One;

/**
 * @author Pablo
 * 
 */
@RunWith(QCCheckRunner.class)
@UseGenerators( {
    @Generator(generator = OneSingletonGen.class, klass = One.class),
    @Generator(generator = SerializableGen.class, klass = Serializable.class),
    @Generator(generator = IntegerSimpleGen.class, klass = int.class),
    @Generator(generator = CGen.class, klass = C.class) })
    @Configuration(tests = 5)
    public class ClassHierarchyOrInterfaceGenTest extends TestCase {

    /**
     * @param serializable
     */
    @Test
    public void forInterfaces(final Serializable serializable) {
	assertNotNull(serializable);
    }

    /**
     * @param serializable
     */
    @Test
    public void forAbstractClasses(final A a) {
	assertNotNull(a);
    }

}
