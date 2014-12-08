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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import ar.edu.unlp.yaqc4j.generators.Arbitrary;
import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Generator for Lists. It can build list of random content classes or you can
 * parameterize the content classes.
 * 
 * @author Pablo
 * 
 * @param <T>
 *            the content type.
 */
public class ListGen<T> implements Gen<List<T>> {

	/**
	 * the MIN_SIZE.
	 */
	protected static final int MIN_SIZE = 0;

	/**
	 * the MAX_SIZE constant. It is an absolute arbitrary number. However, it
	 * could be ignored if the right constructor is used.
	 */
	protected static final int MAX_SIZE = 15;

	/**
	 * the contentGenerator generator.
	 */
	private Gen<T> contentGenerator;

	/**
	 * the min.
	 */
	private Long min;

	/**
	 * the max
	 */
	private Long max;

	// @SuppressWarnings("unchecked")
	// public ListGen() {
	// Class<T> clazz = (Class<T>) Arbitrary
	// .getSuperclassTypeParameter((Class<? extends ListGen<?>>) this
	// .getClass());
	// // Assert.assertNotNull(clazz);
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
	public ListGen(final Gen<T> content) {
		this.setContentGenerator(content);
	}

	/**
	 * The constructor.
	 * 
	 * @param theContentGenerator
	 *            the content generator.
	 * @param aMin
	 *            the minimum.
	 */
	public ListGen(final Gen<T> theContentGenerator, final int aMin,
			final int aMax) {
		Assert.assertNotNull(theContentGenerator);
		Assert.assertTrue(aMin >= MIN_SIZE);
		this.setContentGenerator(theContentGenerator);
		this.setMin(aMin);
		this.setMax(new Long(aMax));
	}

	/**
	 * @{inheritDoc
	 */
	protected final List<T> createNew(final int size) {
		return new ArrayList<T>(size);
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
	protected final void setMin(final long aMin) {
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
	protected final Long getMin() {
		return this.min;
	}

	/**
	 * @{inheritDoc
	 */
	public List<T> arbitrary(final Distribution random, final long minsize,
			final long maxsize) {
		int minsize2 = (int) ((this.getMin() == null) ? Math.max(0, minsize)
				: this.getMin());
		int maxsize2 = (int) ((this.getMax() == null) ? Math.max(0, maxsize)
				: this.getMax());
		if (maxsize2 < minsize2) {
			int temp = maxsize2;
			maxsize2 = minsize2;
			minsize2 = temp;
		}
		// generate a random size with 'size' as the maximum possible.
		int size2 = (int) Arbitrary.choose(random, minsize2, maxsize2);
		List<T> c = this.createNew(size2);
		int i = 0;
		while (i < size2) {
			// the size of the argument decreases by 1 just to avoid overflow
			c.add(this.getContentGenerator().arbitrary(random, minsize2,
					Math.max(minsize2, size2 - 1)));
			i = c.size();
		}
		return c;
	}

	/**
	 * The setter of max.
	 * 
	 * @param max
	 *            the max to set
	 */
	public void setMax(final Long max) {
		this.max = max;
	}

	/**
	 * Getter of max.
	 * 
	 * @return the max
	 */
	public Long getMax() {
		return this.max;
	}

}
