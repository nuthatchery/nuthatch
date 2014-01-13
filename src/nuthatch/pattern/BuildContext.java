package nuthatch.pattern;

import nuthatch.tree.TreeCursor;

public interface BuildContext<Value, Type> {

	/**
	 * Create a new tree node.
	 * 
	 * At least one of the arguments must be non-null
	 * 
	 * @param name
	 *            Constructor name, or null
	 * @param type
	 *            Node type, or null
	 * @param value
	 *            Node value, or null
	 * @param children
	 *            Children, or null
	 * @return Cursor for the new node
	 */
	<T extends TreeCursor<Value, Type>> T create(String name, Type type, Value value, T[] children);

}
