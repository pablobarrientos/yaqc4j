package ar.edu.unlp.yaqc4j.samples.generators;

import ar.edu.unlp.yaqc4j.generators.Gen;
import ar.edu.unlp.yaqc4j.randoms.Distribution;
import ar.edu.unlp.yaqc4j.samples.C;

/**
 * @author Pablo
 * 
 */
public class CGen implements Gen<C> {

    /**
     * @{inheritDoc
     */
    @Override
    public C arbitrary(final Distribution random, final long minsize,
	    final long maxsize) {
	return new C((int) minsize);
    }

}
