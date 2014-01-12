package nuthatch.pattern;

import nuthatch.tree.TreeCursor;

public interface Pattern<Value, Type> {

	/**
	 * Build a tree from a pattern.
	 * 
	 * The built tree replaces the current node of the cursor (if any).
	 * 
	 * @param tree
	 *            A tree cursor to insert the new tree into
	 * @param env
	 *            An environment
	 * @return the tree cursor
	 * @throws NotBuildableException
	 *             if the pattern contains elements that are not buildable, such
	 *             as And, Or, Any, etc.
	 */
	<K, T extends TreeCursor<Value, Type>> T build(T tree, Environment<K, ? extends T> env) throws NotBuildableException;


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
	<K, T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<K, T> env);


	/**
	 * @return True if this pattern only accesses a subtree during matching
	 *         (never follows parent branches).
	 */
	boolean subTreeOnly();
}
