package nuthatch.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;

public class AncestorPattern<Value, Type> implements Pattern<Value, Type> {

	private final Pattern<Value, Type> pattern;


	public AncestorPattern(Pattern<Value, Type> pattern) {
		this.pattern = pattern;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> T build(BuildContext<Value, Type, T> context, Environment<T> env) throws NotBuildableException {
		throw new NotBuildableException("ancestor");
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean isBound(Environment<T> env) {
		return false;
	}


	@Override
	public boolean isVariable() {
		return false;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		T copy = (T) tree.copy();
		while(!copy.isAtRoot()) {
			copy.go(Tree.PARENT);
			int fromBranch = copy.getFromBranch();

			if(pattern.match(copy, env)) {
				return true;
			}
		}
		return false;
	}


	@Override
	public boolean subTreeOnly() {
		return false;
	}

}
