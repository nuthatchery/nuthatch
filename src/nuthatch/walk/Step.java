package nuthatch.walk;

import nuthatch.tree.Tree;
import nuthatch.walker.Walker;

public interface Step<W extends Walker<?, ?>> {
	public static final int PARENT = Tree.PARENT;
	public static final int FIRST = Tree.FIRST;
	public static final int LAST = Tree.LAST;
	public static final int NEXT = Integer.MIN_VALUE + 1;


	/**
	 * Visit a new node.
	 * 
	 * This method is called whenever the walker enters a node.
	 * 
	 * The step should perform the task of visiting the current node only,
	 * and return the next node that should be visited (rather than trying to
	 * visit multiple nodes during the same visit call).
	 * 
	 * @param walker
	 *            The walker
	 * @return The direction of the next node to visit
	 */
	int step(W walker);
}
