package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

public class OrPattern<Value, Type> implements Pattern<Value, Type> {

	private final Pattern<Value, Type> a;
	private final Pattern<Value, Type> b;


	public OrPattern(Pattern<Value, Type> a, Pattern<Value, Type> b) {
		if(a == null || b == null) {
			throw new IllegalArgumentException("Patterns must not be null");
		}

		this.a = a;
		this.b = b;
	}


	@Override
	public boolean match(TreeCursor<Value, Type> tree, Environment<TreeCursor<Value, Type>> env) {
		return a.match(tree, env) || b.match(tree, env);
	}

}
