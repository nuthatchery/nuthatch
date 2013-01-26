package nuthatch.tree;

import nullness.Nullable;
import nuthatch.tree.errors.BranchNotFoundError;

public interface TreeCursor<Value, Type> {
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
	 * Make a new tree cursor, positioned at the node at branch i.
	 * 
	 * The return value is the same as that from go(i).copy(), but the effect is
	 * different.
	 * 
	 * @param i
	 *            The branch index.
	 * @return A new tree cursor
	 */
	TreeCursor<Value, Type> getBranch(int i) throws BranchNotFoundError;


	/**
	 * Get the data stored at this node.
	 * 
	 * @return The data value
	 */
	@Nullable
	Value getData();


	/**
	 * Get the (constructor) name of this node.
	 * 
	 * @return The name
	 */
	@Nullable
	String getName();


	/**
	 * Get a hopefully unique string identifier for this node.
	 * 
	 * The identifier is likely to be unique, but you can't rely on this.
	 * 
	 * @return A string which identifies this node
	 */
	String getPathId();


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
	 * Get the last element of the path.
	 * 
	 * @return getPath().getElement(getPath().size()-1)
	 */
	int getLastPathElement();


	/**
	 * Get the branch this node was entered from.
	 * 
	 * @return Number of the branch back to the previous node
	 */
	int getFromBranch();


	/**
	 * Get the type of this node.
	 * 
	 * @return The type
	 */
	Type getType();


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
	 * Check if the given branch exists.
	 * 
	 * @param i
	 *            The branch number
	 * @return True if getBranch(i) will succeed
	 */
	boolean hasBranch(int i);


	/**
	 * Check if this node has data associated with it.
	 * 
	 * @return True if getData() != null
	 */
	boolean hasData();


	/**
	 * Check if this node has a name associated with it.
	 * 
	 * @return True if getName() != null
	 */
	boolean hasName();


	/**
	 * Check if this node is a leaf.
	 * 
	 * @return True if this node has no children
	 */
	boolean isAtLeaf();


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


	/**
	 * Equality on subtrees rooted at the current node.
	 * 
	 * @param other
	 *            The other cursor, or null
	 * @return True if the subtrees rooted at this and the other node are equal
	 * @throws UnsupportedOperationException
	 *             if other is of a different type, and the implementation only
	 *             supports comparison with the same type
	 */
	boolean subtreeEquals(@Nullable TreeCursor<Value, Type> other);


	/**
	 * Get the number of children of this node.
	 * 
	 * @return The number of children
	 */
	int getNumChildren();
}
