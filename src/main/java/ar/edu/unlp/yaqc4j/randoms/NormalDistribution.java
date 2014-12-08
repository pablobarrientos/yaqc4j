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

package ar.edu.unlp.yaqc4j.randoms;

import java.util.Random;

/**
 * Random generation using normal distribution. All subclasses give results
 * using this distribution.
 * 
 * @author Pablo
 * 
 */
public class NormalDistribution extends Random implements Distribution {

    /**
     * the serialVersionUID.
     */
    private static final long serialVersionUID = -2541819023189032557L;

    /**
     * the random.
     */
    private final Random random;

    /**
     * the N_SIGMA.
     */
    protected static final int N_SIGMA = 3;

    /**
     * The constructor.
     */
    public NormalDistribution() {
    	this.random = new Random();
    }

    /**
     * Generate the next random number for this distribution function.
     * 
     * @return double 0 <= x <= 1.0
     */
    public double nextRandomNumber(){
    	return this.nextGausian();
    }

    /**
     * @return
     */
    protected final double nextGausian() {
    	return this.nextGausian(N_SIGMA);
    }

    /**
     * @param sigma
     *            the sigma.
     * @return a double
     */
    protected final double nextGausian(final int sigma) {
	// n * sigma range normalized to 1.0
	return (this.random.nextGaussian() % sigma) / sigma;
    }

    /**
     * @{inheritDoc
     */
    @Override
    public boolean nextRandomBoolean() {
	return this.nextRandomNumber() > N_SIGMA;
    }

    /**
     * @{inheritDoc
     */
    @Override
    public int nextRandomInteger() {
	return (int) Math.round(this.nextRandomNumber());
    }
}
