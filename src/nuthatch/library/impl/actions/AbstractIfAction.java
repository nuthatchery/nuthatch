package nuthatch.library.impl.actions;

import nuthatch.walk.Action;
import nuthatch.walk.Walker;

public abstract class AbstractIfAction<W extends Walker<?, ?>> extends AbstractComposeAction<W> {

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
