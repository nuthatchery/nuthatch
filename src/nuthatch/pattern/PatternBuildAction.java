package nuthatch.pattern;

import nuthatch.pattern.impl.MinimalAbstractPattern;
import nuthatch.tree.TreeCursor;

public abstract class PatternBuildAction<Value, Type> extends MinimalAbstractPattern<Value, Type> {

	private final Pattern<Value, Type> pat;


	public PatternBuildAction(Pattern<Value, Type> pat) {
		this.pat = pat;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T extends TreeCursor<Value, Type>> T build(BuildContext<Value, Type, T> context, Environment<T> env) throws NotBuildableException {
		return (T) build(pat.build(context, env));
	}


	public abstract TreeCursor<Value, Type> build(TreeCursor<Value, Type> tree);


	@Override
	public <T extends TreeCursor<Value, Type>> boolean isBound(Environment<T> env) {
		return pat.isBound(env);
	}


	@Override
	public boolean isVariable() {
		return pat.isVariable();
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		return pat.match(tree, env);
	}


	@Override
	public boolean subTreeOnly() {
		return pat.subTreeOnly();
	}

}
