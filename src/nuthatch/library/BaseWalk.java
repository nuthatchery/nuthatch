package nuthatch.library;

import nuthatch.walker.Walker;

public abstract class BaseWalk<W extends Walker<?, ?, W>> implements Walk<W> {

	@Override
	public void init(W walker) {

	}


	@Override
	public int step(W walker) {
		return NEXT;
	}
}
