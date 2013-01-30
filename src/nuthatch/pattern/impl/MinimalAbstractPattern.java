package nuthatch.pattern.impl;

import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

public abstract class MinimalAbstractPattern<Value, Type> implements Pattern<Value, Type> {
	@Override
	public final boolean match(TreeCursor<Value, Type> tree) {
		return match(tree, new LayeredEnvironment<TreeCursor<Value, Type>>());
	}
}
