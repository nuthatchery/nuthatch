package nuthatch.library.impl.actions;

import nuthatch.walk.Action;
import nuthatch.walk.Walker;

final class Leaf<W extends Walker<?, ?>> extends AbstractIfAction<W> {
	public Leaf(Action<W> action) {
		super(action);
	}


	@Override
	public boolean cond(W walker) {
		return walker.isAtLeaf();
	}
}
