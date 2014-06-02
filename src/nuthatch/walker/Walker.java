package nuthatch.walker;

import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.errors.TypeMismatch;

public interface Walker<Value, Type> extends TreeCursor<Value, Type> {
	/**
	 * Return the depth of the tree at the current position.
	 * 
	 * @return Current depth, where root has depth 0
	 */
	int depth();


	/**
	 * Return the direction we came from.
	 * 
	 * @return Direction back to the previous visited node
	 */
	int from();


	/**
	 * Check where we came from.
	 * 
	 * @param i
	 *            A direction
	 * @return True if we entered the current node from direction i
	 */
	boolean from(int i);


	/**
	 * @return An environment local to the current step of the walk.
	 */
	Environment<? extends TreeCursor<Value, Type>> getLocalEnv();


	/**
	 * Check if current node is a leaf.
	 * 
	 * @return True if current node has no children
	 */
	boolean isLeaf();


	/**
	 * Check if current node is root.
	 * 
	 * @return True if current node has no parent
	 */
	boolean isRoot();


	/**
	 * @return True if the walker is currently running (i.e., isAtTop() is
	 *         false)
	 */
	boolean isRunning();


	/**
	 * Uses the local step environment.
	 * 
	 * The environment is untouched if this method returns false.
	 * 
	 * @param pat
	 *            A pattern
	 * @return True if the pattern matched.
	 */
	boolean match(Pattern<Value, Type> pat);


	/**
	 * The environment is untouched if this method returns false.
	 * 
	 * @param pat
	 *            A pattern
	 * @param env
	 *            An environment. Variables may be bound as a side effect on the
	 *            environment.
	 * @return True if the pattern matched.
	 */
	boolean match(Pattern<Value, Type> pat, Environment<? extends TreeCursor<Value, Type>> env);


	/**
	 * Split execution into one walker per child, operating in parallel.
	 * 
	 */
	void nest();


	/**
	 * Replace current node.
	 * 
	 * The current node and its children are replace by the subtree
	 * obtained by building the pattern using the local environment.
	 * 
	 * @param tree
	 *            Replacement pattern
	 * @throws NotBuildableException
	 *             if the replacement pattern cannot be build
	 */
	void replace(Pattern<Value, Type> pattern) throws NotBuildableException;


	/**
	 * Replace current node.
	 * 
	 * The current node and its children are replace by the subtree
	 * obtained by building the pattern using the provided environment.
	 * 
	 * @param tree
	 *            Replacement pattern
	 * @param env
	 *            The environment. Will not be changed by this method.
	 * @throws NotBuildableException
	 *             if the replacement pattern cannot be build
	 */
	void replace(Pattern<Value, Type> pattern, Environment<? extends TreeCursor<Value, Type>> env) throws NotBuildableException;


	/**
	 * Replace current node.
	 * 
	 * The current node and its children are replace by the subtree identified
	 * by the supplied tree cursor.
	 * 
	 * @param tree
	 *            Replacement subtree
	 * @throws TypeMismatch
	 *             if the replacement tree is of an incompatible type
	 */
	void replace(TreeCursor<Value, Type> tree) throws TypeMismatch;


	/**
	 * Start executing the walker.
	 */
	void start();
}
