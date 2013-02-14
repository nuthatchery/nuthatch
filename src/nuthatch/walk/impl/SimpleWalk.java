package nuthatch.walk.impl;

import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Step;

/**
 * A simple engine with registers.
 * 
 * This class is not intended to be subclassed; extend {@link AbstractVarWalk}
 * instead.
 * 
 * @author anya
 * 
 * @param <Value>
 * @param <Type>
 */
public final class SimpleWalk<Value, Type> extends AbstractVarWalk<Value, Type, SimpleWalk<Value, Type>> {

	public SimpleWalk(Tree<Value, Type> tree, Step<SimpleWalk<Value, Type>> strat) {
		super(tree, strat);
	}


	public SimpleWalk(TreeCursor<Value, Type> cursor, Step<SimpleWalk<Value, Type>> strat) {
		super(cursor, strat);
	}
}
