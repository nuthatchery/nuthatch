package nuthatch.library.impl.actions;

import nuthatch.walk.Action;
import nuthatch.walk.Walker;

final class Up<W extends Walker<?, ?>> extends AbstractIfAction<W> {
	public Up(Action<W> action) {
		super(action);
	}


	@Override
	public boolean cond(W walker) {
		return walker.from(LAST);
	}
}
