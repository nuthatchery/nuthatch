package nuthatch.pattern;

import nuthatch.tree.Tree;

public interface Pattern extends Tree {

	/**
	 * Match pattern against tree.
	 * 
	 * Any variables in the pattern will be either bound in the environment (if
	 * they were free), or matched against the tree (if they were bound in the
	 * environment).
	 * 
	 * The environment is unchanged if the match fails, otherwise it may contain
	 * new variable bindings.
	 * 
	 * @param tree
	 *            A tree
	 * @param env
	 *            An environment
	 * @return true if the match succeeded
	 */
	boolean match(Tree tree, Environment env);
}
