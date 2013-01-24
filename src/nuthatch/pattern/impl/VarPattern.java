package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;

public class VarPattern<Value, Type> implements Pattern<Value, Type> {

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
	public boolean match(TreeCursor<Value, Type> tree, Environment env) {
		Tree binding = env.get(name);
		if(binding != null) {
			return tree.matches(binding);
		}
		else if(type != null && !type.equals(tree.getType())) {
			return false;
		}
		else {
			env.put(name, tree.getCurrentTree());
		}
		return true;
	}

}
