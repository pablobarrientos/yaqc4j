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

package ar.edu.unlp.yaqc4j.generators;

import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Generator which invokes several times a transformation method in an object.
 * 
 * @author Pablo
 * @param <T>
 * 
 */
public abstract class MethodTransformerGen<T> implements Gen<T> {

	/**
	 * the inputGen.
	 */
	private Gen<T> originGenerator;
	
	/**
	 * The original value. 
	 */
	private T original;

	/**
	 * @param originGenerator
	 */
	public MethodTransformerGen(final Gen<T> originGenerator) {
		this.originGenerator = originGenerator;
	}
	
	/**
	 * @param origin
	 */
	public MethodTransformerGen(T origin) {
		this.original = origin;
	}

	/**
	 * @see ar.edu.unlp.yaqc4j.generators.Gen#arbitrary(ar.edu.unlp.yaqc4j.randoms.Distribution, long, long)
	 */
	@Override
	public T arbitrary(Distribution random, long minsize, long maxsize) {
		long times = new PositiveIntegerSimpleGen().arbitrary(random, minsize, maxsize);
		T result = original;
		if (original == null){
			result = originGenerator.arbitrary(random, minsize, maxsize);
		}
		for (int i = 0; i < times; i++) {
			result = this.transform(result);
		}
		return result;
	}

	protected abstract T transform(T object);
}
