package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;

public class VarPattern implements Pattern {

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
	public boolean match(Tree tree, Environment env) {
		Tree binding = env.get(name);
		if(binding != null) {
			return tree.equals(binding);
		}
		else if(type != null && !type.equals(tree.getType())) {
			return false;
		}
		else {
			env.put(name, tree);
		}
		return true;
	}

}
