package nuthatch.tree;

import nullness.Nullable;

public interface TreeHandle<Value, Type> {
	TreeHandle<Value, Type> getBranchHandle(int i);


	/**
	 * Make a new cursor rooted at the handle's node.
	 * 
	 * @return A new cursor rooted at the current node
	 */
	TreeCursor<Value, Type> getCursor();


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
	 * Get the number of children of this node.
	 * 
	 * @return The number of children
	 */
	int getNumChildren();


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
	 * Check if this node has the given name associated with it.
	 * 
	 * @return True if name.equals(getName())
	 */
	boolean hasName(String name);


	/**
	 * Check if this node has the given type
	 * 
	 * @param type
	 *            A type
	 * @return True if type.equals(getType())
	 */
	boolean hasType(Type type);


	/**
	 * Check if this node is a leaf.
	 * 
	 * @return True if this node has no children
	 */
	boolean isAtLeaf();


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
	boolean subtreeEquals(@Nullable TreeHandle<Value, Type> other);


	/**
	 * @return Result of toString() on current subtree
	 */
	String treeToString();
}
