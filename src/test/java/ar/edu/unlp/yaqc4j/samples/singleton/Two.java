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

package ar.edu.unlp.yaqc4j.samples.singleton;

/**
 * Singleton example.
 * 
 * @author Pablo
 * 
 */
public final class Two implements ITwo {

    /**
     * the serialVersionUID.
     */
    @SuppressWarnings("unused")
	private static final long serialVersionUID = -3891121087555345262L;

    /**
     * The instance.
     */
    private static Two instance;

    /**
     * the value
     */
    private Integer value = 0;

    /**
     * The tipical private constructor.
     */
    private Two() {
    }

    /**
     * The well-known singleton method.
     * 
     * @return the single instance.
     */
    public static synchronized Two getInstance() {
	if (instance == null) {
	    instance = new Two();
	}
	return instance;
    }

    /**
     * @{inheritDoc
     */
    public void setValue(final Integer value) {
	this.value = value;
    }

    /**
     * @{inheritDoc
     */
    public Integer getValue() {
	return this.value;
    }
}
