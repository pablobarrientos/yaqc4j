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

import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.Assert;

import ar.edu.unlp.yaqc4j.generators.Arbitrary;
import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Default queue random generator. It can build queues of random content classes
 * or you can parameterize the content classes.
 * 
 * @author Pablo
 * 
 * @param <T>
 *            the content type.
 */
public class QueueGen<T> implements Gen<Queue<T>> {

    /**
     * the MIN_SIZE.
     */
    protected static final int MIN_SIZE = 0;

    /**
     * the contentGenerator generator.
     */
    private Gen<T> contentGenerator;

    /**
     * the min.
     */
    private int min;

    //
    // /**
    // * The constructor.
    // */
    // @SuppressWarnings("unchecked")
    // public QueueGen() {
    // Class<T> clazz = (Class<T>) Arbitrary
    // .getSuperclassTypeParameter(this
    // .getClass());
    // this.setContentGenerator((Gen<T>) Arbitrary.getGeneratorFor(Arbitrary
    // .rand(), clazz));
    // this.min = MIN_SIZE;
    // }

    /**
     * The constructor.
     * 
     * @param content
     *            the content.
     */
    public QueueGen(final Gen<T> content) {
	this.setContentGenerator(content);
	this.min = MIN_SIZE;
    }

    /**
     * The constructor.
     * 
     * @param theContentGenerator
     *            the content generator.
     * @param aMin
     *            the minimum.
     */
    public QueueGen(final Gen<T> theContentGenerator, final int aMin) {
	Assert.assertNotNull(theContentGenerator);
	Assert.assertTrue(aMin >= MIN_SIZE);
	this.setContentGenerator(theContentGenerator);
	this.min = aMin;
    }

    /**
     * @{inheritDoc
     */
    protected final Queue<T> createNew(final int size) {
	return new PriorityQueue<T>(size);
    }

    /**
     * The setter of contentGenerator.
     * 
     * @param theContentGenerator
     *            the contentGenerator to set
     */
    protected final void setContentGenerator(final Gen<T> theContentGenerator) {
	this.contentGenerator = theContentGenerator;
    }

    /**
     * The setter of min.
     * 
     * @param aMin
     *            the min to set
     */
    protected final void setMin(final int aMin) {
	this.min = aMin;
    }

    /**
     * getter of contentGenerator.
     * 
     * @return the contentGenerator
     */
    protected final Gen<T> getContentGenerator() {
	return this.contentGenerator;
    }

    /**
     * Getter of min.
     * 
     * @return the min
     */
    protected final long getMin() {
	return this.min;
    }

    /**
     * @{inheritDoc
     */
    public Queue<T> arbitrary(final Distribution random, final long minsize,
	    final long maxsize) {
	// generate a random size with 'size' as the maximum possible.
	int size2 = (int) Arbitrary.choose(random, this.getMin(), maxsize);
	Queue<T> c = this.createNew(size2);
	int i = Math.max(0, (int) minsize);
	while (i < size2) {
	    // the size of the argument decreases by 1 just to avoid overflow
	    c.add(this.getContentGenerator().arbitrary(random, minsize,
		    maxsize - 1));
	    i = c.size();
	}
	return c;
    }
}
