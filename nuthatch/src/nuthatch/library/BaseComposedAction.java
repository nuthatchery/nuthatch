package nuthatch.library;

import nuthatch.walker.Walker;

public abstract class BaseComposedAction<W extends Walker<?, ?, W>> implements Action<W> {

	protected final Action<W> action;


	public BaseComposedAction(Action<W> action) {
		this.action = action;
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
		BaseComposedAction<?> other = (BaseComposedAction<?>) obj;
		if(action == null) {
			if(other.action != null) {
				return false;
			}
		}
		else if(!action.equals(other.action)) {
			return false;
		}
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		return result;
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
