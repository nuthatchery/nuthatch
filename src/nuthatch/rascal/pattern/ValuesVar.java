package nuthatch.rascal.pattern;

import nuthatch.pattern.Pattern;
import nuthatch.pattern.impl.Var;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class ValuesVar extends Var<IValue, Type> implements Pattern<IValue, Type> {

	public ValuesVar(String name) {
		super(name);
	}

}
