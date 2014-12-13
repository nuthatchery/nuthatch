package nuthatch.examples;

import nuthatch.library.Walk;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;
import nuthatch.walker.impl.AbstractVarWalker;

class StringTreeWalker extends AbstractVarWalker<String, String, StringTreeWalker> implements Walker<String, String, StringTreeWalker> {

	public StringTreeWalker(Tree<String, String> tree, Walk<StringTreeWalker> step) {
		super(tree, step);
	}


	public StringTreeWalker(TreeCursor<String, String> cursor, Walk<StringTreeWalker> step) {
		super(cursor, step);
	}


	@Override
	protected StringTreeWalker subWalk(TreeCursor<String, String> cursor, Walk<StringTreeWalker> step) {
		return new StringTreeWalker(cursor, step);
	}

}
