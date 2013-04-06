package nuthatch.library.impl.actions;

import nuthatch.library.Action;
import nuthatch.walker.Walker;

final class Up<W extends Walker<?, ?>> extends AbstractIfAction<W> {
	public Up(Action<W> action) {
		super(action);
	}


	@Override
	public boolean cond(W walker) {
		return walker.isAtLeaf() || walker.from(LAST);
	}
}
