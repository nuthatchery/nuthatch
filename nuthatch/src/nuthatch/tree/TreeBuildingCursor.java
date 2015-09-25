package nuthatch.tree;

public interface TreeBuildingCursor<Value, Type> extends TreeCursor<Value, Type> {
	/**
	 * Create a new node at the current position
	 * 
	 * If the current position is top, the new node will be the root.
	 * 
	 * If the node already exists it will be replaced.
	 * 
	 * The children will be placeholders; you may move onto them, but not
	 * inspect them until nodes are created for them.
	 * 
	 * @param name
	 *            Name of the node, or null
	 * @param type
	 *            Type of the node, or null
	 * @param data
	 *            Data value of the node, or null
	 * @param numChildren
	 *            Number of children to reserve
	 */
	void createNode(String name, Type type, Value data, int numChildren);


	/**
	 * @return True if the cursor is at a placeholder and not at a proper node
	 */
	boolean isAtPlaceholder();
}
