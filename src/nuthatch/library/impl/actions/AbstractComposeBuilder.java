package nuthatch.library.impl.actions;

import java.util.ArrayList;
import java.util.List;

import nuthatch.library.Action;
import nuthatch.library.ActionBuilder;
import nuthatch.walker.Walker;

abstract class AbstractComposeBuilder<W extends Walker<?, ?, W>> implements ActionBuilder<W> {
	protected final List<Action<W>> actions = new ArrayList<Action<W>>();


	@Override
	public void add(Action<W> action) {
		actions.add(action);
	}


	@Override
	public abstract Action<W> done();
}
