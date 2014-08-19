package nuthatch.library;

import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

public abstract class BaseMatchAction<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> implements MatchAction<Value, Type, C, W> {

	@Override
	public void init(W walker) {

	}
}
