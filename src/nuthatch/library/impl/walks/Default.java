package nuthatch.library.impl.walks;

import nuthatch.walk.Action;
import nuthatch.walk.Walk;
import nuthatch.walk.Walker;

public class Default<W extends Walker<?, ?>> implements Walk<W> {

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
