package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

public class AndPattern<Value, Type> implements Pattern<Value, Type> {

	private final Pattern<Value, Type> a;
	private final Pattern<Value, Type> b;


	public AndPattern(Pattern<Value, Type> a, Pattern<Value, Type> b) {
		if(a == null || b == null) {
			throw new IllegalArgumentException("Patterns must not be null");
		}
		this.a = a;
		this.b = b;
	}


	@Override
	public boolean match(TreeCursor<Value, Type> tree, Environment env) {
		return a.match(tree, env) && b.match(tree, env);
	}

}
