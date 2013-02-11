package nuthatch.walk;

import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

public interface Action<Value, Type> {
	/**
	 * Apply the transformation.
	 * 
	 * @param walker
	 *            The walker; use {@link Walker#currentTree()} to obtain the
	 *            current tree node
	 * @return A replacement subtree, or null if no change
	 */
	TreeCursor<Value, Type> apply(Walker<Value, Type> walker);
}
