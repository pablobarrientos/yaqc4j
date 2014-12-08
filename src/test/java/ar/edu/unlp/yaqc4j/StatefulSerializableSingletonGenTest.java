/*
Yaqc4j is an specification-based testing framework, based on QuickCheck for Haskell by John Hughes.
It makes easier testing task by creating test data automatically through user-defined and built-in
generators.

Copyright (C) 2010 Universidad Nacional de La Plata

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

package ar.edu.unlp.yaqc4j;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;
import ar.edu.unlp.yaqc4j.samples.singleton.One;

/**
 * StatefulSerializableSingletonGen test.
 * 
 * @author Pablo
 * 
 */
@RunWith(QCCheckRunner.class)
public class StatefulSerializableSingletonGenTest {

    /**
     * the RUNNING_TIMES constant.
     */
    private static final int RUNNING_TIMES = 10;

    /**
     * the runs.
     */
    private int runs = 0;

    /**
     * the generated instances.
     */
    private One[] instances = new One[RUNNING_TIMES];

    /**
     * @{inheritDoc
     */
    @Test
    @Configuration(tests = RUNNING_TIMES)
    @Generator(klass = ar.edu.unlp.yaqc4j.samples.singleton.One.class, generator = ar.edu.unlp.yaqc4j.samples.generators.OneSingletonGen.class)
    public void singletonNotTheSameInstance(final One instance) {
	// the parameter should be different between test runs, in order to
	// avoid internal singleton data sharing.
	this.instances[this.runs] = instance;
	if (this.runs > 1) {
	    for (int i = 0; i < this.runs - 1; i++) {
		assertTrue("Should return different instances",
			this.instances[i] != this.instances[i + 1]);
	    }
	}
	this.runs++;
    }
}
