package ar.edu.unlp.yaqc4j.collectors;

import java.util.List;

/**
 * @author Pablo
 *
 */
public class EmptyListClassifier extends Classifier<List<?>> {

	public EmptyListClassifier() {
		super("empty");
	}

	@Override
	public boolean classify(List<?> list) {
		return list.isEmpty();
	}

}
