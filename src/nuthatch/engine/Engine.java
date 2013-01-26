package nuthatch.engine;

import nuthatch.engine.errors.TypeMismatch;
import nuthatch.tree.TreeCursor;

public interface Engine<Value, Type> extends TreeCursor<Value, Type> {
	/**
	 * Return the depth of the tree at the current position.
	 * 
	 * @return Current depth, where root has depth 0
	 */
	int depth();


	/**
	 * Start executing the engine.
	 */
	void engage();


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
	 * Split execution into one engine per child, operating in parallel.
	 * 
	 */
	void split();
}
