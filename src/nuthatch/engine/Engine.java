package nuthatch.engine;

import nuthatch.strategy.Transform;
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
	 * Split execution into one engine per child, operating in parallel.
	 * 
	 */
	void split();


	/**
	 * Apply a transform at the current tree node.
	 * 
	 * @param t
	 *            The transform. It should return a replacement tree if desired,
	 *            or null if no change should occur.
	 */
	void transform(Transform<Value, Type> t);
}
