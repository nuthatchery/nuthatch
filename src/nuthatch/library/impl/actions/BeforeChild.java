package nuthatch.library.impl.actions;

import nuthatch.walk.Action;
import nuthatch.walk.Walker;

final class BeforeChild<W extends Walker<?, ?>> extends AbstractIfAction<W> {

	public BeforeChild(Action<W> action) {
		super(action);
	}


	@Override
	public boolean cond(W walker) {
		return !walker.isAtLeaf() && !walker.from(LAST);
	}

}
