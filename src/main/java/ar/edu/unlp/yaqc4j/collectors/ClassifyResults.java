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
 * Class used to collect and report all classifiers for a given test method
 * parameter.
 * 
 * @author Pablo
 * 
 */
public class ClassifyResults<T> {
	Classifier<T>[] classifiers;
	String name;
	Map<String, Integer> results;

	public ClassifyResults(String name, Classifier<T>[] classifiers) {
		this.classifiers = classifiers;
		this.name = name;
		results = new HashMap<String, Integer>();
		for (Classifier<T> classifier : classifiers) {
			results.put(classifier.getLabel(), 0);
		}
	}

	public void classify(T t) {
		for (Classifier<T> classifier : classifiers) {
			if (classifier.classify(t)) {
				results.put(classifier.getLabel(),
						results.get(classifier.getLabel()) + 1);
			}
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
		for (String label: results.keySet()){
			buffer.append("  ").append(label).append(": ").append(results.get(label)).append(" occurences (").append((results.get(label) * 100) / total).append("%)\n");
		}
		return buffer.toString();
	}
}
