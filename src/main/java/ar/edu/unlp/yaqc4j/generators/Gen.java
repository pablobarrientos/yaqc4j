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
 * Interface for generators.
 * 
 * All class that implement this interface must have a zero-argument constructor
 * if it is used with @link{Generator} annotation.
 * 
 * @param <T>
 *            the type.
 */
public interface Gen<T> {
    /**
     * Returns an arbitrary object of type T. The object should not have a size
     * greater than 'size' (or smaller than 'size'). What the size actually
     * refers to depends on the implementation, but it could be the number of
     * nodes in a tree, pixels in an image etc.
     * 
     * The supplied random generator should be the only source of randomness
     * used in the generator.
     * 
     * @param random
     *            the random generator that should be used when randomness is
     *            needed.
     * @param minsize
     *            , the lower limiting factor.
     * @param minsize
     *            , the upper limiting factor.
     * 
     * @return a random object
     */
    T arbitrary(Distribution random, long minsize, long maxsize);
}
