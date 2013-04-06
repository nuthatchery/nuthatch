package nuthatch.library;

import nuthatch.tree.Tree;
import nuthatch.walker.Walker;

public interface Action<W extends Walker<?, ?>> {
	public static final int PARENT = Tree.PARENT;
	public static final int FIRST = Tree.FIRST;
	public static final int LAST = Tree.LAST;
	public static final int NEXT = Integer.MIN_VALUE + 1;
	public static final int PROCEED = Integer.MIN_VALUE;


	/**
	 * Initialise any internal state
	 * 
	 * @param walker
	 */
	void init(W walker);


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
	 * @return The direction of the next node to visit, or PROCEED to leave the
	 *         decision to the surrounding action
	 */
	int step(W walker);

}
