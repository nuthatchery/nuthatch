package nuthatch.pattern;

import nuthatch.tree.TreeCursor;

public interface Pattern<Value, Type> {

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
	<T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env);

}
