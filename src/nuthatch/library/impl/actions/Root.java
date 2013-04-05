package nuthatch.library.impl.actions;

import nuthatch.walk.Action;
import nuthatch.walk.Walker;

final class Root<W extends Walker<?, ?>> extends AbstractIfAction<W> {
	public Root(Action<W> action) {
		super(action);
	}


	@Override
	public boolean cond(W walker) {
		return walker.isAtRoot();
	}
}
