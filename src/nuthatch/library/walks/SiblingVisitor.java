package nuthatch.library.walks;

import nuthatch.walk.Step;
import nuthatch.walk.Walk;

public class SiblingVisitor<W extends Walk<?, ?>> implements Step<W> {

	@Override
	public int step(W walker) {
		return 0;
	}

}
