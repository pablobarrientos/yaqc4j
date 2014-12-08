package ar.edu.unlp.yaqc4j;

import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * @author Pablo
 * 
 */
@RunWith(QCCheckRunner.class)
@Configuration(tests = 100)
public class InterfaceTest {
    /**
     * @param list
     */
    @Test
    public void serializableTest(final Serializable s) {
	assertNotNull(s);
    }
}
