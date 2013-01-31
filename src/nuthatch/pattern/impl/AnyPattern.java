package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.tree.TreeCursor;

public class AnyPattern<Value, Type> extends MinimalAbstractPattern<Value, Type> {

	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		return true;
	}

}
