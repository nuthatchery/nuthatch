package nuthatch.library;

import nuthatch.walker.Walker;

public abstract class BaseAction<W extends Walker<?, ?, W>> implements Action<W> {

	@Override
	public void init(W walker) {

	}
}
