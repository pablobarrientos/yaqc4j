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

import java.io.Serializable;

/**
 * Singleton example.
 * 
 * @author Pablo
 * 
 */
public final class One implements Serializable {

    /**
     * the serialVersionUID.
     */
    private static final long serialVersionUID = -3891121087555345262L;

    /**
     * The instance.
     */
    private static One instance;

    /**
     * The tipical private constructor.
     */
    private One() {
    }

    /**
     * The well-known singleton method.
     * 
     * @return the single instance.
     */
    public static synchronized One getInstance() {
	if (instance == null) {
	    instance = new One();
	}
	return instance;
    }
}
