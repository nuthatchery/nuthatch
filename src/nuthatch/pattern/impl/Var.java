package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.tree.TreeCursor;

public class Var<Value, Type> extends MinimalAbstractPattern<Value, Type> {
	private TreeCursor<Value, Type> data;
	private final Type type;
	private final String name;


	public Var(Environment<?> env) {
		this.name = null;
		this.type = null;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> T build(T tree, Environment<T> env) throws NotBuildableException {
		throw new UnsupportedOperationException();
	}


	public TreeCursor<Value, Type> get() {
		return data;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
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

}
