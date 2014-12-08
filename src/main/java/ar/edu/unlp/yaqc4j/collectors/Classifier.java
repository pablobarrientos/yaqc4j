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

/**
 * 
 * Abstract class for classifiers. All subclasses must implement the method
 * which indicates the criteria to classify any object in its category.
 * 
 * @author Pablo
 * 
 */
public abstract class Classifier<T> {
	private String label;

	protected Classifier(String label) {
		this.label = label;
	}
	
	public Classifier() {
		this("UNLABELLED");
	}

	public String getLabel() {
		return label;
	}

	public abstract boolean classify(T object);
}
