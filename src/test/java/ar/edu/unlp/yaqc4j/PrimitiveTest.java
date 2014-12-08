/*
Yaqc4j is an specification-based testing framework, based on QuickCheck for Haskell by John Hughes.
It makes easier testing task by creating test data automatically through user-defined and built-in
generators.

Copyright (C) 2010 Universidad Nacional de La Plata

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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Generator;
import ar.edu.unlp.yaqc4j.generators.java.lang.IntegerSimpleGen;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * Tests for primitives.
 * 
 */
@RunWith(QCCheckRunner.class)
public class PrimitiveTest {
    /**
     * test.
     * 
     * @param i
     *            some double.
     */
    @Test
    public void test(final double i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some double.
     */
    @Test
    public void test(final Double i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some float.
     */
    @Test
    public void test(final float i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some float.
     */
    @Test
    public void test(final Float i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some byte.
     */
    @Test
    public void test(final byte i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some byte.
     */
    @Test
    public void test(final Byte i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some short.
     */
    @Test
    public void test(final short i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some short.
     */
    @Test
    public void test(final Short i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some integer.
     */
    @Test
    @Generator(generator = IntegerSimpleGen.class, klass = int.class)
    public void test(final int i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some integer.
     */
    @Test
    @Generator(generator = IntegerSimpleGen.class, klass = Integer.class)
    public void test(final Integer i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some long.
     */
    @Test
    public void test(final long i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some long.
     */
    @Test
    public void test(final Long i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some big integer.
     */
    @Test
    public void test(final BigInteger i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some big decimal.
     */
    @Test
    public void test(final BigDecimal i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some integer.
     */
    @Test
    @Generator(generator = IntegerSimpleGen.class, klass = int.class)
    public void test(final int[] i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some array.
     */
    @Test
    public void test(final boolean[][] i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some string.
     */
    @Test
    public void test(final String i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some char.
     */
    @Test
    public void test(final char i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some character.
     */
    @Test
    public void test(final Character i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some boolean.
     */
    @Test
    public void test(final boolean i) {
    }

    /**
     * test.
     * 
     * @param i
     *            some boolean.
     */
    @Test
    public void test(final Boolean i) {
    }
}
