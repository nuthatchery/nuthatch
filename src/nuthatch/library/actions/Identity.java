package nuthatch.library.actions;

import nuthatch.tree.TreeCursor;
import nuthatch.walk.Action;
import nuthatch.walker.Walker;

public class Identity<Value, Type> implements Action<Value, Type> {

	@Override
	public TreeCursor<Value, Type> apply(Walker<Value, Type> walker) {
		return null;
	}
}
