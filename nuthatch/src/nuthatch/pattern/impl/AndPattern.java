package nuthatch.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

public class AndPattern<Value, Type> extends MinimalAbstractPattern<Value, Type> {
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
	public <T extends TreeCursor<Value, Type>> T build(BuildContext<Value, Type, T> context, Environment<T> env) throws NotBuildableException {
		throw new NotBuildableException("and");
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		env.begin();
		if(a.match(tree, env) && b.match(tree, env)) {
			env.commit();
			return true;
		}
		else {
			env.rollback();
			return false;
		}
	}


	@Override
	public boolean subTreeOnly() {
		return a.subTreeOnly() && b.subTreeOnly();
	}

}
