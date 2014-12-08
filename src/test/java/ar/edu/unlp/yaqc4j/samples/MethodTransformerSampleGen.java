package ar.edu.unlp.yaqc4j.samples;

import ar.edu.unlp.yaqc4j.generators.MethodTransformerGen;

/**
 * @author pbarrientos
 *
 */
public class MethodTransformerSampleGen extends MethodTransformerGen<Integer>{

	public MethodTransformerSampleGen() {
		super(0);
	}

	/** (non-Javadoc)
	 * @see ar.edu.unlp.yaqc4j.generators.MethodTransformerGen#transform(java.lang.Object)
	 */
	@Override
	protected Integer transform(Integer i) {
		return i + 1;
	}

}
