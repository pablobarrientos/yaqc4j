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

package ar.edu.unlp.yaqc4j.generators.java.math;

import java.math.BigDecimal;

import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Default BigDecimal random generator.
 * 
 * @author Pablo
 * 
 */
public final class BigDecimalGen implements Gen<BigDecimal> {
    /**
     * the INT_GEN.
     */
    private static final BigIntegerGen INT_GEN = new BigIntegerGen();

    /**
     * @{inheritDoc
     */
    public BigDecimal arbitrary(final Distribution random, final long minsize,
	    final long maxsize) {
	return new BigDecimal(INT_GEN.arbitrary(random, minsize, maxsize) + "."
		+ INT_GEN.arbitrary(random, minsize, maxsize).abs());
    }
}
