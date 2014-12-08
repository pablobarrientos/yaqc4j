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

package ar.edu.unlp.yaqc4j.collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Classify;
import ar.edu.unlp.yaqc4j.annotations.Collect;
import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.generators.java.lang.IntegerSimpleGen;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;
import ar.edu.unlp.yaqc4j.samples.SimpleCollectionGen;

/**
 * Sample of simple objects classifiers and collector.
 * 
 * @author Pablo
 * 
 */
@RunWith(QCCheckRunner.class)
public class SampleCollector {

	@Test
	@Configuration(minsize=-2, maxsize=2, tests=100)
	@Generator(klass = Integer.class, generator = IntegerSimpleGen.class)
	public void sample1(
			@Classify(name="sampleIntegers", classifiers = {EvenClassifier.class, OddClassifier.class }) final Integer a,
			@Collect(name="partitionInt", collector=IntegerPartitionCollector.class) final Integer b) {
		assertEquals(a, a);
	}
	
	@Test
	@Generator(generator=SimpleCollectionGen.class, klass=List.class)
	@Configuration(maxsize=200, tests=300)
	public void sample2(
			@Classify(name="sampleLists", classifiers = {EmptyListClassifier.class, NonEmptyListClassifier.class, SingletonListClassifier.class }) final List<Integer> list) {
		assertTrue(list.size() >= 0);
	}

}
