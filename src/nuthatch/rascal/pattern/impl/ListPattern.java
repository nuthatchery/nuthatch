package nuthatch.rascal.pattern.impl;

import java.util.ArrayList;
import java.util.List;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.pattern.impl.AbstractPattern;
import nuthatch.rascal.adapter.PdbCursor;
import nuthatch.tree.TreeCursor;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class ListPattern extends AbstractPattern<IValue, Type> {

	private final Type type;
	private final Pattern<IValue, Type>[] children;
	private final boolean subTreeOnly;


	public ListPattern(Type listType, Pattern<IValue, Type>[] elements) {
		if(listType != null && !listType.isList()) {
			throw new IllegalArgumentException("Type should be list");
		}
		this.type = listType;
		if(elements != null) {
			this.children = elements.clone();
			boolean tmp = true;
			for(Pattern<IValue, Type> c : elements) {
				tmp &= c.subTreeOnly();
			}
			subTreeOnly = tmp;
		}
		else {
			this.children = null;
			subTreeOnly = true;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T extends TreeCursor<IValue, Type>> T build(BuildContext<IValue, Type, T> context, Environment<T> env) throws NotBuildableException {
		List<T> childValues = new ArrayList<T>(children.length);

		for(Pattern<IValue, Type> child : children) {
			if(child instanceof ListVarPattern) {
				for(IValue v : ((IList) child.build(context, env).getData())) {
					childValues.add((T) new PdbCursor(v));
				}
			}
			else {
				childValues.add(child.build(context, env));
			}

		}

		return context.create("[]", type, null, (T[]) childValues.toArray(new TreeCursor[childValues.size()]));
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> boolean doMatch(T tree, Environment<T> env) {
		if(!tree.getType().isList()) {
			return false;
		}
		if(type != null && !tree.getType().isSubtypeOf(type)) {
			return false;
		}
		if(children != null) {
			if(children.length == 1 && children[0] instanceof ListVarPattern) {
				return children[0].match(tree, env);
			}
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
