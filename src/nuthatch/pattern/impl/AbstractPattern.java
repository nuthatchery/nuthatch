package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.tree.TreeCursor;

public abstract class AbstractPattern<Value, Type> extends MinimalAbstractPattern<Value, Type> {

	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		env.begin();
		if(doMatch(tree, env)) {
			env.commit();
			return true;
		}
		else {
			env.rollback();
			return false;
		}
	}


	protected abstract <T extends TreeCursor<Value, Type>> boolean doMatch(T tree, Environment<T> env);

}
