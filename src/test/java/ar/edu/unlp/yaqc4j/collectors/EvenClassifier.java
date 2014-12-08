package ar.edu.unlp.yaqc4j.collectors;

import ar.edu.unlp.yaqc4j.collectors.Classifier;

/**
 * @author Pablo
 *
 */
public class EvenClassifier extends Classifier<Integer> {

	public EvenClassifier() {
		super("even");
	}

	@Override
	public boolean classify(Integer num) {
		return num % 2 == 0;
	}

}
