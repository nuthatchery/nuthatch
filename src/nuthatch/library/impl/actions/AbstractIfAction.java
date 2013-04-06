package nuthatch.library.impl.actions;

import nuthatch.library.Action;
import nuthatch.library.BaseComposedAction;
import nuthatch.walker.Walker;

abstract class AbstractIfAction<W extends Walker<?, ?>> extends BaseComposedAction<W> {

	public AbstractIfAction(Action<W> action) {
		super(action);
	}


	public abstract boolean cond(W walker);


	@Override
	public int step(W walker) {
		if(cond(walker)) {
			return super.step(walker);
		}
		else {
			return PROCEED;
		}
	}
}
