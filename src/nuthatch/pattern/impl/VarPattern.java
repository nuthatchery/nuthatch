package nuthatch.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.VarName;
import nuthatch.tree.TreeCursor;

public class VarPattern<Value, Type> extends MinimalAbstractPattern<Value, Type> {
	private final String name;
	private final String type;


	public VarPattern(String name, String type) {
		if(name == null) {
			throw new IllegalArgumentException("Name must not be null");
		}
		this.name = name.intern();
		this.type = type;
	}


	public VarPattern(VarName<?> name, String type) {
		if(name == null) {
			throw new IllegalArgumentException("Name must not be null");
		}
		this.name = name.getName();
		this.type = type;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> T build(BuildContext<Value, Type> context, Environment<T> env) throws NotBuildableException {
		T t = env.get(name);
		if(t != null) {
			return t;
		}
		else {
			throw new NotBuildableException("Variable " + name + " not bound");
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		TreeCursor<Value, Type> binding = env.get(name);
		if(binding != null) {
			return tree.subtreeEquals(binding);
		}
		else if(type != null && !type.equals(tree.getType())) {
			return false;
		}
		else {
			env.put(name, (T) tree.copySubtree());
		}
		return true;
	}


	@Override
	public boolean subTreeOnly() {
		return true;
	}

}
