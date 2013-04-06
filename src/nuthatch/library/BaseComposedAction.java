package nuthatch.library;

import nuthatch.walker.Walker;

public abstract class BaseComposedAction<W extends Walker<?, ?>> implements Action<W> {

	protected final Action<W> action;


	public BaseComposedAction(Action<W> action) {
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
