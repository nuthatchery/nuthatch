package nuthatch.library.walks;

import nuthatch.walk.Step;
import nuthatch.walk.Walk;

public class Default<W extends Walk<?, ?>> implements Step<W> {

	@Override
	public int step(W walker) {
		return NEXT;
	}

}
