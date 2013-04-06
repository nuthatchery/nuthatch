package nuthatch.library;

import nuthatch.walker.Walker;

public abstract class AbstractAction<W extends Walker<?, ?>> implements Action<W> {

	@Override
	public void init(W walker) {

	}
}
