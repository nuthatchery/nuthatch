package nuthatch.pattern.impl;

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
	public <T extends TreeCursor<Value, Type>> T build(T tree, Environment<? extends T> env) throws NotBuildableException {
		throw new UnsupportedOperationException();
	}


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
			env.put(name, (T) tree.copy());
		}
		return true;
	}

}
