package nuthatch.strategy;

import nuthatch.tree.Tree;

public abstract class AbstractStrategy<Value, Type> implements Strategy<Value, Type> {
	protected static final int FIRST = Tree.FIRST;
	protected static final int LAST = Tree.LAST;
	protected static final int PARENT = Tree.PARENT;
}
