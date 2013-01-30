package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.tree.TreeCursor;

public abstract class AbstractPattern<Value, Type> extends MinimalAbstractPattern<Value, Type> {

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


	protected abstract boolean doMatch(TreeCursor<Value, Type> tree, Environment<TreeCursor<Value, Type>> env);

}
