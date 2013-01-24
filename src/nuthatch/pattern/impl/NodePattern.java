package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

public class NodePattern<Value, Type> extends AbstractPattern<Value, Type> {

	private final String name;
	private final Type type;
	private final Value data;
	private final Pattern<Value, Type>[] children;


	public NodePattern(String name, Type type, Value data, Pattern<Value, Type>[] children) {
		this.name = name;
		this.type = type;
		this.data = data;
		if(children != null) {
			this.children = children.clone();
		}
		else {
			this.children = null;
		}
	}


	@Override
	public boolean doMatch(TreeCursor<Value, Type> tree, Environment env) {
		if(name != null && !name.equals(tree.getName()))
			return false;
		if(type != null && !type.equals(tree.getType()))
			return false;
		if(data != null && !data.equals(tree.getData()))
			return false;
		if(children != null) {
			if(tree.numChildren() != children.length)
				return false;
			for(int i = 0; i < children.length; i++) {
				TreeCursor<Value, Type> copy = tree.copy();
				copy.go(i + 1);
				if(!children[i].match(copy, env))
					return false;
			}
		}
		return true;
	}
}
