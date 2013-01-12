package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;

public class TreePattern implements Pattern {

	private final Tree pattern;


	public TreePattern(Tree tree) {
		if(tree == null) {
			throw new IllegalArgumentException("Tree must not be null");
		}

		this.pattern = tree;
	}


	@Override
	public boolean match(Tree tree, Environment env) {
		return pattern.equals(tree);
	}

}
