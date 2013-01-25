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


	TreeCursor<Value, Type> getBranch(int i);


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
	String getName();


	/**
	 * Get a hopefully unique string identifier for this node.
	 * 
	 * The identifier is likely to be unique, but you can't rely on this.
	 * 
	 * @return A string which identifies this node
	 */
	String getPathId();


	Tree<Value, Type> getCurrentTree();


	Path getPath();


	int getPathElement(int i);


	int getLastPathElement();


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
	 * @return this
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
	 * Check if this node is a leaf.
	 * 
	 * @return True if this node has no children
	 */
	boolean isAtLeaf();


	/**
	 * Check if this node is root.
	 * 
	 * @return True if this node has no parent, and the tree implementation
	 *         tracks parents
	 */
	boolean isAtRoot();


	boolean isAtTop();


	boolean matches(Tree<Value, Type> tree);


	/**
	 * Get the number of children of this node.
	 * 
	 * @return The number of children
	 */
	int numChildren();
}
