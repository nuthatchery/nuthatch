package nuthatch.engine.impl;

import nuthatch.engine.Strategy;
import nuthatch.tree.Tree;

public abstract class AbstractStrategy implements Strategy {
	protected static final int FIRST = Tree.FIRST;
	protected static final int LAST = Tree.LAST;
	protected static final int PARENT = Tree.PARENT;
}
