package nuthatch.rascal.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.pattern.impl.MinimalAbstractPattern;
import nuthatch.rascal.adapter.ValuesCursor;
import nuthatch.tree.TreeCursor;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class PreserveAnnos extends MinimalAbstractPattern<IValue, Type> {

	private final Pattern<IValue, Type> pat;
	private final Pattern<IValue, Type> annoSrc;


	public PreserveAnnos(Pattern<IValue, Type> pat, Pattern<IValue, Type> annoSrc) {
		this.pat = pat;
		this.annoSrc = annoSrc;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T extends TreeCursor<IValue, Type>> T build(BuildContext<IValue, Type, T> context, Environment<T> env) throws NotBuildableException {
		T patBuild = pat.build(context, env);
		IValue patValue = patBuild.getData();
		IValue annoValue = annoSrc.build(context, env).getData();

		if(patValue.isAnnotatable() && annoValue.isAnnotatable()) {
			patValue = patValue.asAnnotatable().joinAnnotations(annoValue.asAnnotatable().getAnnotations());
			return (T) new ValuesCursor(patValue);
		}
		else {
			return patBuild;
		}
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> boolean isBound(Environment<T> env) {
		return pat.isBound(env);
	}


	@Override
	public boolean isVariable() {
		return pat.isVariable();
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> boolean match(T tree, Environment<T> env) {
		return pat.match(tree, env);
	}


	@Override
	public boolean subTreeOnly() {
		return pat.subTreeOnly();
	}

}
