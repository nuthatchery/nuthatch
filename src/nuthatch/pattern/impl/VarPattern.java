package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.tree.TreeCursor;

public class VarPattern<Value, Type> extends MinimalAbstractPattern<Value, Type> {
	private final String name;
	private final String type;


	public VarPattern(String name, String type) {
		if(name == null) {
			throw new IllegalArgumentException("Name must not be null");
		}
		this.name = name;
		this.type = type;
	}


	@Override
	public boolean match(TreeCursor<Value, Type> tree, Environment<TreeCursor<Value, Type>> env) {
		TreeCursor<Value, Type> binding = env.get(name);
		if(binding != null) {
			return tree.subtreeEquals(binding);
		}
		else if(type != null && !type.equals(tree.getType())) {
			return false;
		}
		else {
			env.put(name, tree.copy());
		}
		return true;
	}

}
