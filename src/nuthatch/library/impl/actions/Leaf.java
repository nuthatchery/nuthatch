package nuthatch.library.impl.actions;

import nuthatch.library.Action;
import nuthatch.walker.Walker;

final class Leaf<W extends Walker<?, ?>> extends AbstractIfAction<W> {
	private final boolean not;


	public Leaf(Action<W> action) {
		super(action);
		not = false;
	}


	public Leaf(Action<W> action, boolean not) {
		super(action);
		this.not = not;
	}


	@Override
	public boolean cond(W walker) {
		return walker.isAtLeaf() ^ not;
	}
}
