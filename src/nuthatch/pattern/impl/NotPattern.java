package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

public class NotPattern<Value, Type> implements Pattern<Value, Type> {

	private final Pattern<Value, Type> a;


	public NotPattern(Pattern<Value, Type> a) {
		if(a == null) {
			throw new IllegalArgumentException("Pattern must not be null");
		}

		this.a = a;
	}


	@Override
	public boolean match(TreeCursor<Value, Type> tree, Environment<TreeCursor<Value, Type>> env) {
		env.begin();
		boolean result = !a.match(tree, env);
		env.rollback();
		return result;
	}

}
