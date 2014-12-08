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

package ar.edu.unlp.yaqc4j.generators.java.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.edu.unlp.yaqc4j.exceptions.GenerationError;
import ar.edu.unlp.yaqc4j.generators.Arbitrary;
import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.generators.java.lang.IntegerSimpleGen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;

/**
 * Default @see{java.util.Date} random generator.
 * 
 * @author Pablo
 * 
 */
public final class DateGen implements Gen<Date> {
    /**
     * the INTEGER_GEN.
     */
    private static final IntegerSimpleGen INTEGER_GEN = new IntegerSimpleGen();

    /**
     * the MAX_YEAR.
     */
    private static final long MAX_YEAR = 10000L;

    /**
     * the MAX_MONTH.
     */
    private static final long MAX_MONTH = 12L;

    /**
     * the MAX_DAY.
     */
    private static final long MAX_DAY = 31L;

    /**
     * @{inheritDoc
     */
    @Override
    public Date arbitrary(final Distribution random, final long minsize,
	    long maxsize) {
    if (minsize == maxsize){
    	maxsize++;
    }
    	
	int minyear = (int) Math.max(-MAX_YEAR, minsize);
	int maxyear = (int) Math.min(maxsize, MAX_YEAR);

	int minmonth = (int) Math.max(0, minsize);
	int maxmonth = (int) Math.min(maxsize, MAX_MONTH);

	int minday = (int) Math.max(1, minsize);
	int maxday = (int) Math.min(maxsize, MAX_DAY);

	int year = INTEGER_GEN.arbitrary(random, minyear, maxyear);
	int month = (int) Arbitrary.choose(random, minmonth, maxmonth);
	int day = (int) Arbitrary.choose(random, minday, maxday);

	try {
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    // df.setLenient(false);
	    return df.parse(day + "/" + month + "/" + year);
	} catch (ParseException e) {
	    // should not happen
	    throw new GenerationError("A wrong date was generated");
	}
    }

}
