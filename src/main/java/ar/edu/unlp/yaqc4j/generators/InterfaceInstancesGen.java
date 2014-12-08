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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Generic generator for interfaces. It constructs empty proxies with no
 * behavior. It might be useful for constructors arguments. The proxy returns
 * a random object for every method return from the interface.
 * 
 * @author Pablo
 * 
 */
public class InterfaceInstancesGen<T> implements Gen<T> {

	/**
	 * the interfaze
	 */
	private Class<T> interfaze;

	/**
	 * the random distribution.
	 */
	private Distribution random;

	/**
	 * the minsize.
	 */
	private long minsize;

	/**
	 * the maxsize.
	 */
	private long maxsize;

	/**
	 * @author Pablo
	 * 
	 */
	public class InternalInvocationHandler implements InvocationHandler {
		/**
		 * @{inheritDoc
		 */
		@SuppressWarnings("unchecked")
		public Object invoke(final Object proxy, final Method method,
				final Object[] args) throws Throwable {
			// if the return type is null or the return type is the interface,
			// it returns null (it avoids an infinite loop)
			if (method.getReturnType().equals(Void.TYPE)
					|| method.getReturnType().equals(
							InterfaceInstancesGen.this.getInterfaze())) {
				return null;
			}
			Gen<T> generator = (Gen<T>) Arbitrary.getGeneratorFor(
					InterfaceInstancesGen.this.getRandom(),
					method.getReturnType());
			if (generator == null) {
				return null;
			} else {
				return generator.arbitrary(
						InterfaceInstancesGen.this.getRandom(),
						InterfaceInstancesGen.this.getMinsize(),
						InterfaceInstancesGen.this.getMaxsize());
			}
		}
	}

	/**
	 * The constructor.
	 * 
	 * @param interfaze
	 */
	public InterfaceInstancesGen(final Class<T> interfaze) {
		if (!interfaze.isInterface()) {
			throw new IllegalArgumentException(
					"The given argument is not an interface.");
		}
		this.setInterfaze(interfaze);
	}

	/**
	 * @{inheritDoc
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T arbitrary(final Distribution random, final long minsize,
			final long maxsize) {
		// arbitrary method parameters are set before calling any method in the
		// proxy.
		this.setMaxsize(maxsize);
		this.setMinsize(minsize);
		this.setRandom(random);
		InvocationHandler handler = new InternalInvocationHandler();
		return (T) Proxy.newProxyInstance(this.interfaze.getClassLoader(),
				new Class[] { this.getInterfaze() }, handler);
	}

	/**
	 * The setter of interfaze.
	 * 
	 * @param interfaze
	 *            the interfaze to set
	 */
	protected void setInterfaze(final Class<T> interfaze) {
		this.interfaze = interfaze;
	}

	/**
	 * Getter of interfaze.
	 * 
	 * @return the interfaze
	 */
	protected Class<T> getInterfaze() {
		return this.interfaze;
	}

	/**
	 * Getter of random.
	 * 
	 * @return the random.
	 */
	private Distribution getRandom() {
		return random;
	}

	/**
	 * Setter of random.
	 * 
	 * @param random
	 *            the random.
	 */
	private void setRandom(Distribution random) {
		this.random = random;
	}

	/**
	 * Getter of minSize.
	 * 
	 * @return the minsize.
	 */
	private long getMinsize() {
		return minsize;
	}

	/**
	 * Setter of minsize.
	 * 
	 * @param minsize
	 *            the minsize.
	 */
	private void setMinsize(long minsize) {
		this.minsize = minsize;
	}

	/**
	 * Getter of maxSize.
	 * 
	 * @return the maxsize.
	 */
	private long getMaxsize() {
		return maxsize;
	}

	/**
	 * Setter of maxsize.
	 * 
	 * @param maxsize
	 *            the maxsize.
	 */
	private void setMaxsize(long maxsize) {
		this.maxsize = maxsize;
	}
}
