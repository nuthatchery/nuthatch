package nuthatch.rascal.adapter;

import nuthatch.library.MatchAction;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public abstract class ValuesMatchAction implements MatchAction<IValue, Type, ValuesCursor, ValuesWalker> {

	@Override
	public void init(ValuesWalker walker) {
		// TODO Auto-generated method stub

	}
}
