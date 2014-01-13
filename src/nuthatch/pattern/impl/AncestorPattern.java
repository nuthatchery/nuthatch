package nuthatch.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeBuildingCursor;
import nuthatch.tree.TreeCursor;

public class AncestorPattern<Value, Type> implements Pattern<Value, Type> {

	private final Pattern<Value, Type> pattern;


	public AncestorPattern(Pattern<Value, Type> pattern) {
		this.pattern = pattern;
	}


	@Override
	public <T extends TreeCursor<Value, Type>> T build(BuildContext<Value, Type> context, Environment<T> env) throws NotBuildableException {
		throw new NotBuildableException("ancestor");
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<T> env) {
		T copy = (T) tree.copy();
		while(!copy.isAtRoot()) {
			copy.go(Tree.PARENT);
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
