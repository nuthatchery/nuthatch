package nuthatch.rascal.pattern.impl;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.impl.AbstractPattern;
import nuthatch.tree.TreeCursor;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class PrimitivePattern extends AbstractPattern<IValue, Type> {

	private final IValue value;
	private final Type type;


	public PrimitivePattern(IValue value, Type type) {
		this.value = value;
		this.type = type;
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> T build(BuildContext<IValue, Type> context, Environment<T> env) throws NotBuildableException {
		if(value == null) {
			throw new NotBuildableException("no value defined");
		}
		return context.create(null, type, value, null);
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> boolean doMatch(T tree, Environment<T> env) {
		if(type != null && !tree.getType().isSubtypeOf(type)) {
			return false;
		}
		if(value != null && !value.equals(tree.getData())) {
			return false;
		}
		return true;
	}


	@Override
	public boolean subTreeOnly() {
		return true;
	}
}
