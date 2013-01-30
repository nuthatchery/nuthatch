package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.tree.TreeCursor;

public class Var<Value, Type> extends MinimalAbstractPattern<Value, Type> {
	private TreeCursor<Value, Type> data;
	private final Type type;
	private final String name;


	public Var() {
		this.name = null;
		this.type = null;
	}


	public Var(String name) {
		this.name = name;
		this.type = null;
	}


	public Var(String name, Type type) {
		this.name = name;
		this.type = type;
	}


	@Override
	public boolean match(TreeCursor<Value, Type> tree, Environment<TreeCursor<Value, Type>> env) {
		if(data == null) {
			if(type == null || type.equals(tree.getType())) {
				data = tree.copySubtree();
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return data.subtreeEquals(tree);
		}
	}


	@Override
	public String toString() {
		if(data != null) {
			return data.toString();
		}
		else if(name != null) {
			return name;
		}
		else {
			return "var";
		}
	}


	public TreeCursor<Value, Type> get() {
		return data;
	}

}
