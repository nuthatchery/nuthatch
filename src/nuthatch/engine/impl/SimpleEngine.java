package nuthatch.engine.impl;

import nuthatch.strategy.Strategy;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;

/**
 * A simple engine with registers.
 * 
 * This class is not intended to be subclassed; extend {@link RegisteredEngine}
 * instead.
 * 
 * @author anya
 * 
 * @param <Value>
 * @param <Type>
 */
public final class SimpleEngine<Value, Type> extends RegisteredEngine<Value, Type, SimpleEngine<Value, Type>> {

	public SimpleEngine(Tree<Value, Type> tree, Strategy<SimpleEngine<Value, Type>> strat) {
		super(tree, strat);
	}


	public SimpleEngine(TreeCursor<Value, Type> cursor, Strategy<SimpleEngine<Value, Type>> strat) {
		super(cursor, strat);
	}
}
