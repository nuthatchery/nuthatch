package nuthatch.strategy;

import nuthatch.engine.Engine;

public abstract class After extends Aspect {

	public After(Strategy s) {
		super(s);
	}


	@Override
	public int visitBefore(Engine eng) {
		return PROCEED;
	}

}
