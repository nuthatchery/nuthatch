package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

public abstract class AbstractPattern<Value, Type> implements Pattern<Value, Type> {

	protected abstract boolean doMatch(TreeCursor<Value, Type> tree, Environment<TreeCursor<Value, Type>> env);


	@Override
	public final boolean match(TreeCursor<Value, Type> tree, Environment<TreeCursor<Value, Type>> env) {
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

}
