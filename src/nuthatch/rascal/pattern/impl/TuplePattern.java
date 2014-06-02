package nuthatch.rascal.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.pattern.impl.AbstractPattern;
import nuthatch.tree.TreeCursor;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class TuplePattern extends AbstractPattern<IValue, Type> {

	private final Type type;
	private final Pattern<IValue, Type>[] children;
	private final boolean subTreeOnly;


	public TuplePattern(Type tupleType, Pattern<IValue, Type>[] children) {
		if(tupleType != null && !tupleType.isList()) {
			throw new IllegalArgumentException("Type should be tuple");
		}
		this.type = tupleType;
		if(children != null) {
			this.children = children.clone();
			boolean tmp = true;
			for(Pattern<IValue, Type> c : children) {
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
	public <T extends TreeCursor<IValue, Type>> T build(BuildContext<IValue, Type> context, Environment<T> env) throws NotBuildableException {
		@SuppressWarnings("unchecked")
		T[] childValues = (T[]) new TreeCursor[children.length];
		for(int i = 0; i < children.length; i++) {
			childValues[i] = children[i].build(context, env);
		}

		return context.create("()", type, null, childValues);
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> boolean doMatch(T tree, Environment<T> env) {
		if(!tree.getType().isTuple()) {
			return false;
		}
		if(type != null && !tree.getType().isSubtypeOf(type)) {
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
