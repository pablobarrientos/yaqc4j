package ar.edu.unlp.yaqc4j.collectors;

import java.util.List;

/**
 * @author Pablo
 *
 */
public class SingletonListClassifier extends Classifier<List<?>> {

	public SingletonListClassifier() {
		super("singleton");
	}

	@Override
	public boolean classify(List<?> list) {
		return list.size() ==1;
	}

}
