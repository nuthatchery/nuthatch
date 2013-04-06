package nuthatch.library.impl.actions;

import nuthatch.library.Action;
import nuthatch.walker.Walker;

final class AfterChild<W extends Walker<?, ?>> extends AbstractIfAction<W> {

	public AfterChild(Action<W> action) {
		super(action);
	}


	@Override
	public boolean cond(W walker) {
		return !walker.from(PARENT);
	}

}
