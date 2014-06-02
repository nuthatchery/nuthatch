package nuthatch.rascal.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.pattern.impl.AbstractPattern;
import nuthatch.tree.TreeCursor;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class TypedConsPattern extends AbstractPattern<IValue, Type> {

	private final Type consType;
	private final Pattern<IValue, Type>[] children;
	private final boolean subTreeOnly;


	public TypedConsPattern(Type type, Pattern<IValue, Type>[] children) {
		if(!type.isConstructor()) {
			throw new IllegalArgumentException("Type '" + type.getName() + "' should be a constructor");
		}
		this.consType = type;
		if(children != null) {
			if(type.getArity() != children.length) {
				throw new IllegalArgumentException("Wrong number of constructor arguments for '" + type.getName() + "', expected " + type.getArity());
			}
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
		if(consType == null) {
			throw new NotBuildableException("Constructor pattern must be typed in order to build");
		}
		@SuppressWarnings("unchecked")
		T[] childValues = (T[]) new TreeCursor[children.length];
		for(int i = 0; i < children.length; i++) {
			childValues[i] = children[i].build(context, env);
		}

		return context.create(null, consType, null, childValues);
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> boolean doMatch(T tree, Environment<T> env) {
		IValue data = tree.getData();
		if(data instanceof IConstructor) {
			if(!((IConstructor) data).getConstructorType().equivalent(consType)) {
				return false;
			}
		}
		else {
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
