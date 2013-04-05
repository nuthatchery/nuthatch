package nuthatch.walk.impl;

import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Walk;

/**
 * A simple engine with registers.
 * 
 * This class is not intended to be subclassed; extend {@link AbstractVarWalker}
 * instead.
 * 
 * @author anya
 * 
 * @param <Value>
 * @param <Type>
 */
public final class SimpleWalker<Value, Type> extends AbstractVarWalker<Value, Type, SimpleWalker<Value, Type>> {

	public SimpleWalker(Tree<Value, Type> tree, Walk<SimpleWalker<Value, Type>> strat) {
		super(tree, strat);
	}


	public SimpleWalker(TreeCursor<Value, Type> cursor, Walk<SimpleWalker<Value, Type>> strat) {
		super(cursor, strat);
	}


	@Override
	protected SimpleWalker<Value, Type> subWalk(TreeCursor<Value, Type> cursor, Walk<SimpleWalker<Value, Type>> step) {
		return new SimpleWalker<Value, Type>(cursor, step);
	}

}
