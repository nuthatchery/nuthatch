package nuthatch.rascal.adapter;

import nuthatch.library.MatchAction;

import org.rascalmpl.value.IValue;
import org.rascalmpl.value.type.Type;

public abstract class ValuesMatchAction implements MatchAction<IValue, Type, ValuesCursor, ValuesWalker> {

	@Override
	public void init(ValuesWalker walker) {
		// TODO Auto-generated method stub

	}
}
