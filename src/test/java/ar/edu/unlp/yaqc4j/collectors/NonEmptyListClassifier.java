package ar.edu.unlp.yaqc4j.collectors;

import java.util.List;

/**
 * @author Pablo
 *
 */
public class NonEmptyListClassifier extends Classifier<List<?>> {

	public NonEmptyListClassifier() {
		super("non-empty");
	}

	@Override
	public boolean classify(List<?> list) {
		return !list.isEmpty();
	}

}
