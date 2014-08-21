package nuthatch.pattern.impl;

import java.util.Comparator;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

public class NodePattern<Value, Type> extends AbstractPattern<Value, Type> {

	private final String name;
	private final Type type;
	private final Value data;
	private final Pattern<Value, Type>[] children;
	private final boolean subTreeOnly;
	private final Comparator<Type> comparator;


	public NodePattern(String name, Type type, Value data, Pattern<Value, Type>[] children, Comparator<Type> comparator) {
		this.name = name;
		this.type = type;
		this.data = data;
		this.comparator = comparator;
		if(children != null) {
			this.children = children.clone();
			boolean tmp = true;
			for(Pattern<Value, Type> c : children) {
				tmp &= c.subTreeOnly();
			}
			subTreeOnly = tmp;
		}
		else {
			this.children = null;
			subTreeOnly = true;
		}
	}


	@Override
	public <T extends TreeCursor<Value, Type>> T build(BuildContext<Value, Type, T> context, Environment<T> env) throws NotBuildableException {
		throw new UnsupportedOperationException();
	}


	@Override
	public <T extends TreeCursor<Value, Type>> boolean doMatch(T tree, Environment<T> env) {
		if(name != null && !name.equals(tree.getName())) {
			return false;
		}
		if(type != null) {
			Type t = tree.getType();
			if(comparator == null) {
				return type.equals(t);
			}
			else {
				return t != null && comparator.compare(t, type) == 0;
			}
		}
		if(data != null && !data.equals(tree.getData())) {
			return false;
		}
		if(children != null) {
			if(tree.getArity() != children.length) {
				return false;
			}
			for(int i = 0; i < children.length; i++) {
				T copy = subTreeOnly ? (T) tree.copySubtree() : (T) tree.copy();
				copy.go(i + 1);
				if(!children[i].match(copy, env)) {
					return false;
				}
			}
		}
		return true;
	}


	@Override
	public boolean subTreeOnly() {
		return subTreeOnly;
	}
}
