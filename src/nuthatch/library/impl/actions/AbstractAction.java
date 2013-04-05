package nuthatch.library.impl.actions;

import nuthatch.walk.Action;
import nuthatch.walk.Walker;

public abstract class AbstractAction<W extends Walker<?, ?>> implements Action<W> {

	@Override
	public void init(W walker) {

	}
}
