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

package ar.edu.unlp.yaqc4j.generators.java.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import ar.edu.unlp.yaqc4j.generators.Arbitrary;
import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Map generator.
 * 
 * @author Pablo
 * 
 * @param <K>
 *            the keys type.
 * @param <V>
 *            the values type.
 */
public class MapGen<K, V> implements Gen<Map<K, V>> {

    /**
     * the MIN_SIZE.
     */
    public static final int MIN_SIZE = 0;

    /**
     * the keyGenerator generator.
     */
    private final Gen<K> keyGenerator;

    /**
     * the valueGenerator generator.
     */
    private final Gen<V> valueGenerator;

    /**
     * the min.
     */
    private final int min;

    /**
     * The constructor.
     * 
     * @param theKeyGenerator
     *            the key generator.
     * @param theValueGenerator
     *            the value generator.
     */
    public MapGen(final Gen<K> theKeyGenerator, final Gen<V> theValueGenerator) {
	this(theKeyGenerator, theValueGenerator, MIN_SIZE);
    }

    /**
     * The constructor.
     * 
     * @param theKeyGenerator
     *            the key generator.
     * @param theValueGenerator
     *            the value generator.
     * @param aMin
     *            the minimum.
     * 
     */
    public MapGen(final Gen<K> theKeyGenerator, final Gen<V> theValueGenerator,
	    final int aMin) {
	Assert.assertNotNull(theKeyGenerator);
	Assert.assertNotNull(theValueGenerator);
	Assert.assertTrue(aMin >= MIN_SIZE);
	this.keyGenerator = theKeyGenerator;
	this.valueGenerator = theValueGenerator;
	this.min = aMin;
    }

    /**
     * @{inheritDoc
     */
    @Override
    public Map<K, V> arbitrary(final Distribution random, final long minsize,
	    final long maxsize) {
	// generate a random size with 'size' as the maximum possible.
	int size2 = (int) Arbitrary.choose(random, this.getMin(), maxsize);
	Map<K, V> map = new HashMap<K, V>(size2);
	int i = (int) minsize;
	while (i < size2) {
	    // the size of the argument decreases by 1 just to avoid overflow
	    K key = (K) this.getKeyGenerator().arbitrary(random, minsize,
		    maxsize - 1);
	    V value = (V) this.getValueGenerator().arbitrary(random, minsize,
		    maxsize - 1);
	    map.put(key, value);
	    i++;
	}
	return map;
    }

    /**
     * Getter of min.
     * 
     * @return the min
     */
    private long getMin() {
	return this.min;
    }

    /**
     * Getter of keyGenerator.
     * 
     * @return the keyGenerator
     */
    private Gen<K> getKeyGenerator() {
	return this.keyGenerator;
    }

    /**
     * Getter of valueGenerator.
     * 
     * @return the valueGenerator
     */
    private Gen<V> getValueGenerator() {
	return this.valueGenerator;
    }

}
