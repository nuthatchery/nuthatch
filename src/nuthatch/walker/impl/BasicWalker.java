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
public final class BasicWalker<Value, Type> extends AbstractVarWalker<Value, Type, BasicWalker<Value, Type>> {

	public BasicWalker(Tree<Value, Type> tree, Step<BasicWalker<Value, Type>> strat) {
		super(tree, strat);
	}


	public BasicWalker(TreeCursor<Value, Type> cursor, Step<BasicWalker<Value, Type>> strat) {
		super(cursor, strat);
	}
}
