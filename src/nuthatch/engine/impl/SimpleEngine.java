package nuthatch.engine.impl;

import nuthatch.strategy.Strategy;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;

public final class SimpleEngine<Value, Type> extends RegisteredEngine<Value, Type, SimpleEngine<Value, Type>> {

	public SimpleEngine(TreeCursor<Value, Type> cursor, Strategy<SimpleEngine<Value, Type>> strat) {
		super(cursor, strat);
	}


	public SimpleEngine(Tree<Value, Type> tree, Strategy<SimpleEngine<Value, Type>> strat) {
		super(tree, strat);
	}
}
