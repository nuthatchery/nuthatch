package nuthatch.library.impl.actions;

import java.util.Arrays;
import java.util.Collection;

import nuthatch.library.Action;
import nuthatch.walker.Walker;

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
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		Combine<?> other = (Combine<?>) obj;
		if(!Arrays.equals(actions, other.actions)) {
			return false;
		}
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(actions);
		return result;
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


	@Override
	public String toString() {
		return "seq" + Arrays.toString(actions);
	}
}
