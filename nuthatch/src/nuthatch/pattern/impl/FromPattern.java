package nuthatch.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

public class FromPattern<Value, Type> implements Pattern<Value, Type> {

	private final int branch;


	public FromPattern(int branch) {
		this.branch = branch;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> T build(BuildContext<Value, Type, T> context, Environment<T> env) throws NotBuildableException {
		throw new NotBuildableException("ancestor");
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean isBound(Environment<T> env) {
		return false;
	}


	@Override
	public boolean isVariable() {
		return false;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		return tree.getFromBranch() == branch;
	}


	@Override
	public boolean subTreeOnly() {
		return false;
	}

}
