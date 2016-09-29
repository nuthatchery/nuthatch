package nuthatch.rascal.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

import org.rascalmpl.value.IValue;
import org.rascalmpl.value.type.Type;

public class ListVarPattern implements Pattern<IValue, Type> {
	private String name;


	public ListVarPattern(String name) {
		this.name = name.intern();
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> T build(BuildContext<IValue, Type, T> context, Environment<T> env) throws NotBuildableException {
		TreeCursor<IValue, Type> binding = env.get(name);
		if(binding == null) {
			throw new NotBuildableException("Variable not bound");
		}
		else {
			return (T) binding.copySubtree();
		}
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> boolean isBound(Environment<T> env) {
		return env.get(name) != null;
	}


	@Override
	public boolean isVariable() {
		return true;
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> boolean match(T tree, Environment<T> env) {
		TreeCursor<IValue, Type> binding = env.get(name);
		if(binding != null) {
			return tree.subtreeEquals(binding);
		}
		else if(!tree.getType().isList()) {
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


	@Override
	public String toString() {
		return "*" + name;
	}
}
