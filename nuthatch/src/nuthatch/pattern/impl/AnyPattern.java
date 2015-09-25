package nuthatch.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.tree.TreeCursor;

public class AnyPattern<Value, Type> extends MinimalAbstractPattern<Value, Type> {

	@Override
	public <T extends TreeCursor<Value, Type>> T build(BuildContext<Value, Type, T> context, Environment<T> env) throws NotBuildableException {
		throw new NotBuildableException("any");
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		return true;
	}


	@Override
	public boolean subTreeOnly() {
		return true;
	}

}
