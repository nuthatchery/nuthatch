package nuthatch.library.impl.actions;

import nuthatch.walk.Action;
import nuthatch.walk.Walker;

final class AfterChild<W extends Walker<?, ?>> extends AbstractIfAction<W> {

	public AfterChild(Action<W> action) {
		super(action);
	}


	@Override
	public boolean cond(W walker) {
		return !walker.from(PARENT);
	}

}
