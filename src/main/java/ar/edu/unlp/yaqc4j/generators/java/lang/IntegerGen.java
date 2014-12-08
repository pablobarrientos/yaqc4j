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

package ar.edu.unlp.yaqc4j.generators.java.lang;

import ar.edu.unlp.yaqc4j.generators.ElementGen;
import ar.edu.unlp.yaqc4j.generators.FrequencyGen;
import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;
import ar.edu.unlp.yaqc4j.util.Pair;

/**
 * Integer random generator which generates extreme cases with 1% probability.
 * 
 * @author Pablo
 * 
 */
public final class IntegerGen implements Gen<Integer> {
	public static final int inf = Integer.MAX_VALUE;
	public static final int neginf = Integer.MIN_VALUE;
	public static final int zero = 0;

	FrequencyGen<Integer> frequencyGen;

	/**
	 * constructor
	 */
	@SuppressWarnings("unchecked")
	public IntegerGen() {
		Gen<Integer> extremesGen = new ElementGen<Integer>(inf, neginf, zero);
		frequencyGen = new FrequencyGen<Integer>(new Pair<Integer, Gen<Integer>>(
				1, extremesGen), new Pair<Integer, Gen<Integer>>(90,
				new IntegerSimpleGen()));
	}

	/**
	 * @see ar.edu.unlp.yaqc4j.generators.Gen#arbitrary(ar.edu.unlp.yaqc4j.randoms.Distribution,
	 *      long, long)
	 */
	public Integer arbitrary(Distribution random, long minsize, long maxsize) {
		int minSize = (int) minsize;
    	if(minsize < Integer.MIN_VALUE){
    		minSize = Integer.MIN_VALUE; 
    	}
    	int maxSize = (int) maxsize;
    	if (maxsize > Integer.MAX_VALUE){
    		maxSize = Integer.MAX_VALUE;
    	}
		return Math.max(Math.min(frequencyGen.arbitrary(random, minsize, maxsize), maxSize), minSize);
	}
}
