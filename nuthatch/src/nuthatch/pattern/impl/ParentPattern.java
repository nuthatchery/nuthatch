package nuthatch.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;

public class ParentPattern<Value, Type> implements Pattern<Value, Type> {

	private final Pattern<Value, Type> pattern;


	public ParentPattern(Pattern<Value, Type> pattern) {
		this.pattern = pattern;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> T build(BuildContext<Value, Type, T> context, Environment<T> env) throws NotBuildableException {
		throw new UnsupportedOperationException();
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean isBound(Environment<T> env) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isVariable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		if(tree.isAtRoot() || tree.isAtTop()) {
			return false;
		}
		T cursor = (T) tree.getBranchCursor(Tree.PARENT);
		int fromBranch = cursor.getFromBranch();
		return pattern.match(cursor, env);
	}


	@Override
	public boolean subTreeOnly() {
		return false;
	}

}
