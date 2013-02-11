package nuthatch.walk;

import nuthatch.walker.Walker;

public interface Step<W extends Walker<?, ?>> {
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
