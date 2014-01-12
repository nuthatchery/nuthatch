package nuthatch.pattern.impl;

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
	public <K, T extends TreeCursor<Value, Type>> T build(T tree, Environment<K, ? extends T> env) throws NotBuildableException {
		throw new NotBuildableException("ancestor");
	}


	@Override
	public <K, T extends TreeCursor<Value, Type>> boolean match(T tree, Environment<K, T> env) {
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
