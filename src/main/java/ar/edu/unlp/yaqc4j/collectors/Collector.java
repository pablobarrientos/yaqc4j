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
package ar.edu.unlp.yaqc4j.collectors;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for collectors. All subclasses must implement the method for
 * classifying any object inside a category.
 * 
 * @author Pablo
 * 
 */
public abstract class Collector<K, T> {
	String name;
	Map<K, Integer> results;

	public Collector() {
		this.name = "UNKNOWN";
		results = new HashMap<K, Integer>();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public abstract K getCategoryFor(T t);
	
	public void collect(T t) {
		K category = this.getCategoryFor(t);
		if (results.get(category) == null){
			results.put(category, 1);
		} else {
			results.put(category, results.get(category) + 1);
		}
	}
	
	public String printResults(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Results for collector \"");
		buffer.append(this.name).append("\":\n");
		float total = 0;
		for (Integer count: results.values()){
			total += count;
		}
		for (K label: results.keySet()){
			buffer.append("  ").append(label.toString()).append(": ").append(results.get(label)).append(" occurences (")
				  .append((results.get(label) * 100) / total).append("%)\n");
		}
		return buffer.toString();
	}
}
