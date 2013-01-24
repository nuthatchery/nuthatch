package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;

public class TreePattern<Value, Type> implements Pattern<Value, Type> {

	private final Tree pattern;


	public TreePattern(Tree tree) {
		if(tree == null) {
			throw new IllegalArgumentException("Tree must not be null");
		}

		this.pattern = tree;
	}


	@Override
	public boolean match(TreeCursor<Value, Type> tree, Environment env) {
		return pattern.equals(tree);
	}

}
