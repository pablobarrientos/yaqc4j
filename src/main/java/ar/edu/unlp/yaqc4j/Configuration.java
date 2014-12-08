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

package ar.edu.unlp.yaqc4j;

import ar.edu.unlp.yaqc4j.randoms.Distribution;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * Configuration of tests. It's a representation of a given '@Configuration'
 * occurrence.
 * 
 * @author Pablo
 * 
 */
public final class Configuration implements Cloneable {
	/**
	 * the random.
	 */
	private Distribution random;

	/**
	 * the maxNumberOfTests.
	 */
	private int maxNumberOfTests;

	/**
	 * the maxNumberOfFailedParams.
	 */
	private int maxNumberOfFailedParams;

	/**
	 * the maxsize.
	 */
	private long maxsize;

	/**
	 * the minsize.
	 */
	private long minsize;

	/**
	 * The constructor.
	 * 
	 * @param theRandom
	 *            the random.
	 */
	public <T extends Distribution> Configuration(final T theRandom) {
		this(theRandom, QCCheckRunner.DEFAULT_NUMBER_OF_RUNS, QCCheckRunner.MAX_ARGUMENTS_FAILS,
				Long.MIN_VALUE, Long.MAX_VALUE);
	}

	/**
	 * The constructor.
	 * 
	 * @param theRandom
	 *            the random.
	 * @param theMaxNumberOfTests
	 *            the maximum number of tests.
	 * @param theMaxNumberOfFailedParams
	 *            the maximum number of failed parameters.
	 * @param theSize
	 *            the maxsize.
	 */
	public Configuration(final Distribution theRandom,
			final int theMaxNumberOfTests,
			final int theMaxNumberOfFailedParams, final long theMinSize,
			final long theMaxSize) {
		this.random = theRandom;
		this.maxNumberOfTests = theMaxNumberOfTests;
		this.maxNumberOfFailedParams = theMaxNumberOfFailedParams;
		this.maxsize = theMaxSize;
		this.minsize = theMinSize;
	}

	/**
	 * @param dist
	 */
	public void setDistribution(final Distribution dist) {
		this.random = dist;
	}

	/**
	 * @param theSize
	 *            the maxsize.
	 */
	public void setMaxSize(final long theSize) {
		this.maxsize = theSize;
	}

	/**
	 * @param tests
	 *            .
	 */
	public void setMaxNumberOfTests(final int tests) {
		this.maxNumberOfTests = tests;
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Configuration config = (Configuration) super.clone();
		return config;
	}

	/**
	 * @return a random.
	 */
	public Distribution getRandom() {
		return this.random;
	}

	/**
	 * @return the maximum number of tests.
	 */
	public int getMaxNumberOfTests() {
		return this.maxNumberOfTests;
	}

	/**
	 * @return the maximum number of failed parameters.
	 */
	public int getMaxNumberOfFailedParams() {
		return this.maxNumberOfFailedParams;
	}

	/**
	 * @return the maxsize.
	 */
	public long getMaxSize() {
		return this.maxsize;
	}

	/**
	 * The setter of maxNumberOfFailedParams.
	 * 
	 * @param theMaxNumberOfFailedParams
	 *            the maxNumberOfFailedParams to set
	 */
	public void setMaxNumberOfFailedParams(final int theMaxNumberOfFailedParams) {
		this.maxNumberOfFailedParams = theMaxNumberOfFailedParams;
	}

	/**
	 * The setter of minsize.
	 * 
	 * @param minsize
	 *            the minsize to set
	 */
	public void setMinsize(final long minsize) {
		this.minsize = minsize;
	}

	/**
	 * Getter of minsize.
	 * 
	 * @return the minsize
	 */
	public long getMinsize() {
		return this.minsize;
	}
	
}
