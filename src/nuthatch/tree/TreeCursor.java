package nuthatch.tree;

import nuthatch.tree.errors.BranchNotFoundError;

public interface TreeCursor<Value, Type> extends Iterable<TreeCursor<Value, Type>>, TreeHandle<Value, Type> {
	/**
	 * Branch number of the first child.
	 */
	public static final int FIRST = 1;
	/**
	 * Branch number of the last child.
	 */
	public static final int LAST = -1;
	/**
	 * Branch number of the parent.
	 */
	public static final int PARENT = 0;


	/**
	 * Make a copy of this cursor, tracking the same tree.
	 * 
	 * The new cursor will be attached to the same tree, but its own position.
	 * 
	 * @return A copy of this cursor
	 */
	TreeCursor<Value, Type> copy();


	/**
	 * Make a copy of this cursor, replacing the subtree rooted at the current
	 * node
	 * with the replacement.
	 * 
	 * The new cursor may or may not share tree data with the old cursor and
	 * replacement.
	 * 
	 * @return A copy of this cursor, with the current subtree replaced
	 */
	TreeCursor<Value, Type> copyAndReplaceSubtree(TreeCursor<Value, Type> replacement);


	/**
	 * Make a copy of this cursor rooted at the current node, tracking the same
	 * tree.
	 * 
	 * The new cursor will be attached to the same tree, but its own position
	 * and root
	 * 
	 * Same as getCursor().
	 * 
	 * @return A copy of this cursor rooted at the current node
	 */
	TreeCursor<Value, Type> copySubtree();


	/**
	 * Make a new tree cursor, positioned at the node at branch i.
	 * 
	 * The return value is the same as that from go(i).copy(), but the effect is
	 * different.
	 * 
	 * @param i
	 *            The branch index.
	 * @return A new tree cursor
	 */
	TreeCursor<Value, Type> getBranchCursor(int i) throws BranchNotFoundError;


	/**
	 * Get the branch this node was entered from.
	 * 
	 * @return Number of the branch back to the previous node
	 */
	int getFromBranch();


	/**
	 * Get a handle to the subtree rooted at the current node.
	 * 
	 * @return A handle to the current node of the cursor
	 * 
	 */
	TreeHandle<Value, Type> getHandle();


	/**
	 * Get the last element of the path.
	 * 
	 * @return getPath().getElement(getPath().size()-1)
	 */
	int getLastPathElement();


	/**
	 * Get the path from the root to the current node.
	 * 
	 * @return A path
	 */
	Path getPath();


	/**
	 * Get a path element.
	 * 
	 * This may be faster than calling getPath().
	 * 
	 * @param i
	 * @return getPath().getElement(i)
	 */
	int getPathElement(int i);


	/**
	 * Get a hopefully unique string identifier for this node.
	 * 
	 * The identifier is likely to be unique, but you can't rely on this.
	 * 
	 * @return A string which identifies this node
	 */
	String getPathId();


	/**
	 * Get the length of the path to the current node; i.e., the current depth
	 * we're at.
	 * 
	 * @return Length of the path
	 */
	int getPathLength();


	/**
	 * Follow branch i.
	 * 
	 * @param i
	 *            The branch direction
	 * @return this, for method chaining
	 * @throws BranchNotFoundError
	 *             If no such branch was found
	 * @see {@link #hasBranch(int)}
	 */
	TreeCursor<Value, Type> go(int i) throws BranchNotFoundError;


	/**
	 * Check if this node is root.
	 * 
	 * @return True if this node has no parent
	 */
	boolean isAtRoot();


	/**
	 * Check if we are outside the tree.
	 * 
	 * @return True if we are at the top, past the root node
	 */
	boolean isAtTop();

}
