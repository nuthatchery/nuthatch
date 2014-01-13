package nuthatch.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeBuildingCursor;
import nuthatch.tree.TreeCursor;

public class ParentPattern<Value, Type> implements Pattern<Value, Type> {

	private final Pattern<Value, Type> pattern;


	public ParentPattern(Pattern<Value, Type> pattern) {
		this.pattern = pattern;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> T build(BuildContext<Value, Type> context, Environment<T> env) throws NotBuildableException {
		throw new UnsupportedOperationException();
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		if(tree.isAtRoot() || tree.isAtTop()) {
			return false;
		}
		T cursor = (T) tree.getBranch(Tree.PARENT);

		return pattern.match(cursor, env);
	}


	@Override
	public boolean subTreeOnly() {
		return false;
	}

}
