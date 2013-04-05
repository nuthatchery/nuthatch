package nuthatch.library.impl.actions;

import nuthatch.walk.Action;
import nuthatch.walk.Walker;

public abstract class AbstractComposeAction<W extends Walker<?, ?>> implements Action<W> {

	protected final Action<W> action;


	public AbstractComposeAction(Action<W> action) {
		this.action = action;
	}


	@Override
	public void init(W walker) {
		action.init(walker);
	}


	@Override
	public int step(W walker) {
		int r = action.step(walker);
		if(r != PROCEED) {
			return r;
		}
		else {
			return PROCEED;
		}
	}
}
