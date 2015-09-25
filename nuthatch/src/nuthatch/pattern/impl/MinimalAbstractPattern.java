package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

public abstract class MinimalAbstractPattern<Value, Type> implements Pattern<Value, Type> {

	@Override
	public <T extends TreeCursor<Value, Type>> boolean isBound(Environment<T> env) {
		return false;
	}


	@Override
	public boolean isVariable() {
		return false;
	}

}
