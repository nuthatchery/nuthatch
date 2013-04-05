package nuthatch.library.impl.actions;

import java.util.ArrayList;
import java.util.List;

import nuthatch.walk.Action;
import nuthatch.walk.Walker;

public abstract class AbstractComposeBuilder<W extends Walker<?, ?>> {
	protected final List<Action<W>> actions = new ArrayList<Action<W>>();


	public void add(Action<W> action) {
		actions.add(action);
	}


	public abstract Action<W> done();
}
