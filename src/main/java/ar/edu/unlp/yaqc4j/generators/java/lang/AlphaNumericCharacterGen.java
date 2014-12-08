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
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Generator of alpha numeric characters.
 * 
 * @author Pablo
 * 
 */
public class AlphaNumericCharacterGen extends CharacterGen {
	/**
     * @{inheritDoc
     */
	@Override
	public Character arbitrary(Distribution random, long minsize, long maxsize) {
		// char 48: '0'; char 57: '9'
		Character num = super.arbitrary(random, 48, 57); 
		// char 65: 'A'; char 90: 'Z'
		Character upp = super.arbitrary(random, 65, 90);
		// char 97: 'a'; char 122: 'z'
		Character low = super.arbitrary(random, 97, 122);
		ElementGen<Character> eGen = 
			new ElementGen<Character>(num, upp, low) {};
		return eGen.arbitrary(random, 0, 2);
	}
}
