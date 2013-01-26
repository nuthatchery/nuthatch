package nuthatch.tree;

import nullness.Nullable;
import nuthatch.tree.errors.BranchNotFoundError;

public interface Tree<Value, Type> {
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
	 * Iterate over the children of this node.
	 * 
	 * @return An iterable over the children
	 */
	Iterable<Tree<Value, Type>> children();


	/**
	 * Return a modifiable copy of this tree.
	 * 
	 * @return A copy of the tree, rooted at this node
	 */
	ModifiableTree<Value, Type> copy();


	/**
	 * Get the tree node at a branch.
	 * 
	 * @param i
	 *            The branch direction
	 * @return The tree node found at that branch
	 * @throws BranchNotFoundError
	 *             If no such branch was found
	 * @see {@link #hasBranch(int)}
	 */
	Tree<Value, Type> getBranch(int i) throws BranchNotFoundError;


	/**
	 * Get the branch number of a connected node.
	 * 
	 * @param node
	 *            A tree node which should be connected to the this node
	 * @return The branch at which this node is connected
	 * @throws BranchNotFoundError
	 *             If no such node is connected to the this node
	 */
	int getBranch(Tree<Value, Type> node) throws BranchNotFoundError;


	/**
	 * Get the data stored at this node.
	 * 
	 * @return The data value
	 */
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
	String getNodeId();


	/**
	 * Get parent of this tree node.
	 * 
	 * @return The parent node, or null if the parent is unavailable or doesn't
	 *         exist
	 */
	@Nullable
	Tree<Value, Type> getParent();


	/**
	 * Get the type of this node.
	 * 
	 * @return The type
	 */
	Type getType();


	/**
	 * Check if the given branch exists.
	 * 
	 * @param i
	 *            The branch number
	 * @return True if getBranch(i) will succeed
	 */
	boolean hasBranch(int i);


	/**
	 * Check if the tree implementation tracks parents.
	 * 
	 * @return True if this tree has information about parents
	 */
	boolean hasParentLinks();


	/**
	 * Check if this node is a leaf.
	 * 
	 * @return True if this node has no children
	 */
	boolean isLeaf();


	/**
	 * Check if the branch number identifies the parent.
	 * 
	 * @param i
	 *            A branch number
	 * @return True if i identifies the parent (i == 0 || i == numChildren())
	 */
	boolean isParent(int i);


	/**
	 * Check if this node is root.
	 * 
	 * @return True if this node has no parent, and the tree implementation
	 *         tracks parents
	 */
	boolean isRoot();


	/**
	 * Get the number of children of this node.
	 * 
	 * @return The number of children
	 */
	int numChildren();


	Tree<Value, Type> replaceChild(Tree<Value, Type> child, int childIndex);
}
