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

package ar.edu.unlp.yaqc4j.generators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.edu.unlp.yaqc4j.annotations.Configuration;
import ar.edu.unlp.yaqc4j.generators.Arbitrary;
import ar.edu.unlp.yaqc4j.generators.CloneGen;
import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.generators.java.util.ListGen;
import ar.edu.unlp.yaqc4j.runners.QCCheckRunner;

/**
 * Test for CloneGen generator.
 * 
 * @author Pablo
 * 
 */
@RunWith(QCCheckRunner.class)
public class CloneGenTest {

    /**
     * @{inheritDoc
     */
    @SuppressWarnings("unchecked")
    @Test
    @Configuration(tests = 10)
    public void notTheSameClonedInstance(final Date date) {
	ListGen<Date> listGen = new ListGen<Date>((Gen<Date>) Arbitrary
		.getGeneratorFor(Arbitrary.defaultDistribution(), Date.class));
	List<Date> list = listGen.arbitrary(Arbitrary.defaultDistribution(), 0, 20);
	CloneGen<Date> cloneGen = new CloneGen<Date>(date);
	CloneGen<ArrayList<Date>> listCloneGen = new CloneGen<ArrayList<Date>>(
		(ArrayList<Date>) list);
	assert (date != cloneGen.arbitrary(Arbitrary.defaultDistribution(), 1, 1));
	assert (list != listCloneGen.arbitrary(Arbitrary.defaultDistribution(), 1, 1));
    }
}
