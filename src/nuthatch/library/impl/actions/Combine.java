package nuthatch.library.impl.actions;

import java.util.Collection;

import nuthatch.walk.Action;
import nuthatch.walk.Walker;

final class Combine<W extends Walker<?, ?>> implements Action<W> {

	private Action<W>[] actions;


	@SafeVarargs
	public Combine(Action<W>... actions) {
		this.actions = actions.clone();
	}


	public Combine(Collection<Action<W>> actions) {
		this.actions = actions.toArray(new Action[actions.size()]);
	}


	@Override
	public void init(W walker) {
		for(Action<W> a : actions) {
			a.init(walker);
		}
	}


	@Override
	public int step(W walker) {
		for(Action<W> a : actions) {
			int r = a.step(walker);
			if(r != PROCEED) {
				return r;
			}
		}
		return PROCEED;
	}
}
