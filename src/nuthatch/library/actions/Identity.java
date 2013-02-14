package nuthatch.library.actions;

import nuthatch.tree.TreeCursor;
import nuthatch.walk.Action;
import nuthatch.walk.Walk;

public class Identity<Value, Type> implements Action<Value, Type> {

	@Override
	public TreeCursor<Value, Type> apply(Walk<Value, Type> walker) {
		return null;
	}
}
