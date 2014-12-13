package nuthatch.library.impl.walks;

import nuthatch.library.Action;
import nuthatch.library.Walk;
import nuthatch.walker.Walker;

public class Default<W extends Walker<?, ?, W>> implements Walk<W> {

	private Action<W> action;


	public Default(Action<W> action) {
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
			return NEXT;
		}
	}

}
