package nuthatch.walk;

import nuthatch.tree.TreeCursor;

public interface Action<Value, Type> {
	/**
	 * Apply the transformation.
	 * 
	 * @param walker
	 *            The walker; use {@link Walk#currentTree()} to obtain the
	 *            current tree node
	 * @return A replacement subtree, or null if no change
	 */
	TreeCursor<Value, Type> apply(Walk<Value, Type> walker);
}
