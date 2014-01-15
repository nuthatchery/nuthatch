package nuthatch.rascal.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.rascal.adapter.PdbCursor;
import nuthatch.rascal.pattern.ValuesBuildContext;
import nuthatch.tree.TreeCursor;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;

public class ListElementPattern implements Pattern<IValue, Type> {

	private final Type listType;
	private final Pattern<IValue, Type> prefix;
	private final Pattern<IValue, Type> element;
	private final Pattern<IValue, Type> suffix;
	private final boolean subTreeOnly;


	public ListElementPattern(Type listType, Pattern<IValue, Type> prefix, Pattern<IValue, Type> element, Pattern<IValue, Type> suffix) {
		if(listType != null && !listType.isList()) {
			throw new IllegalArgumentException("Type must be a list");
		}
		this.listType = listType;
		this.prefix = prefix;
		this.element = element;
		this.suffix = suffix;
		this.subTreeOnly = (prefix == null || prefix.subTreeOnly()) && element.subTreeOnly() && (suffix == null || suffix.subTreeOnly());
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T extends TreeCursor<IValue, Type>> T build(BuildContext<IValue, Type> context, Environment<T> env) throws NotBuildableException {
		IList pre = null;
		if(prefix != null) {
			pre = (IList) prefix.build(context, env).getData();
		}
		IValue ele = element.build(context, env).getData();
		IList suf = null;
		if(suffix != null) {
			suf = (IList) suffix.build(context, env).getData();
		}

		if(context instanceof ValuesBuildContext) {
			IValueFactory vf = ((ValuesBuildContext) context).getValueFactory();
			IListWriter listWriter = vf.listWriter();
			if(pre != null) {
				listWriter.appendAll(pre);
			}
			listWriter.append(ele);
			if(suf != null) {
				listWriter.appendAll(suf);
			}
			return context.create(null, listType, listWriter.done(), null);
		}
		else {
			int len = 1;
			if(pre != null) {
				len += pre.length();
			}
			if(suf != null) {
				len += suf.length();
			}
			TreeCursor<IValue, Type>[] childValues = new TreeCursor[len];
			int i = 0;
			if(pre != null) {
				for(IValue v : pre) {
					childValues[i++] = new PdbCursor(v);
				}
			}

			childValues[i++] = new PdbCursor(ele);

			if(suf != null) {
				for(IValue v : suf) {
					childValues[i++] = new PdbCursor(v);
				}
			}

			return (T) context.create(null, listType, null, childValues);
		}
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> boolean isBound(Environment<T> env) {
		return false;
	}


	@Override
	public boolean isVariable() {
		return false;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T extends TreeCursor<IValue, Type>> boolean match(T tree, Environment<T> env) {
		if(tree.getType().isList()) {
			IList list = (IList) tree.getData();
			env.begin();
			for(int i = 0; i < list.length(); i++) {
				T copy = subTreeOnly ? (T) tree.copySubtree() : (T) tree.copy();
				copy.go(i + 1);
				if(element.match(copy, env)) {
					if(prefix == null || prefix.match((T) new PdbCursor(list.sublist(0, i)), env)) {
						if(suffix == null || suffix.match((T) new PdbCursor(list.sublist(i + 1, list.length() - i - 1)), env)) {
							env.commit();
							return true;
						}
					}
					System.err.println("List matching: found element, but prefix/suffix doesn't match");
					env.rollback();
					env.begin();
				}

			}
			env.rollback();
			return false;
		}
		else {
			return false;
		}
	}


	@Override
	public boolean subTreeOnly() {
		return subTreeOnly;
	}

}
