package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

public class NotPattern<Value, Type> extends MinimalAbstractPattern<Value, Type> {
	private final Pattern<Value, Type> a;


	public NotPattern(Pattern<Value, Type> a) {
		if(a == null) {
			throw new IllegalArgumentException("Pattern must not be null");
		}

		this.a = a;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> T build(T tree, Environment<? extends T> env) throws NotBuildableException {
		throw new NotBuildableException("not");
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		env.begin();
		boolean result = !a.match(tree, env);
		env.rollback();
		return result;
	}


	@Override
	public boolean subTreeOnly() {
		return a.subTreeOnly();
	}

}
