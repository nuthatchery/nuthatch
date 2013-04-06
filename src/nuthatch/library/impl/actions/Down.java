package nuthatch.library.impl.actions;

import nuthatch.library.Action;
import nuthatch.walker.Walker;

final class Down<W extends Walker<?, ?>> extends AbstractIfAction<W> {
	public Down(Action<W> action) {
		super(action);
	}


	@Override
	public boolean cond(W walker) {
		return walker.from(PARENT);
	}
}
