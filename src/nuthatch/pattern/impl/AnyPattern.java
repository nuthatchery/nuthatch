package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.tree.TreeCursor;

public class AnyPattern<Value, Type> extends MinimalAbstractPattern<Value, Type> {

	@Override
	public <K, T extends TreeCursor<Value, Type>> T build(T tree, Environment< ? extends T> env) throws NotBuildableException {
		throw new NotBuildableException("any");
	}


	@Override
	public <K, T extends TreeCursor<Value, Type>> boolean match(T tree, Environment< T> env) {
		return true;
	}


	@Override
	public boolean subTreeOnly() {
		return true;
	}

}
