package ar.edu.unlp.yaqc4j.collectors;

import ar.edu.unlp.yaqc4j.collectors.Classifier;

/**
 * @author Pablo
 *
 */
public class OddClassifier extends Classifier<Integer> {

	public OddClassifier() {
		super("odd");
	}

	@Override
	public boolean classify(Integer num) {
		return num % 2 != 0;
	}

}
