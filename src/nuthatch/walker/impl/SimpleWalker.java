package nuthatch.walker.impl;

import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Step;

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

	public SimpleWalker(Tree<Value, Type> tree, Step<SimpleWalker<Value, Type>> strat) {
		super(tree, strat);
	}


	public SimpleWalker(TreeCursor<Value, Type> cursor, Step<SimpleWalker<Value, Type>> strat) {
		super(cursor, strat);
	}
}
