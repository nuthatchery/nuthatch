package nuthatch.strategy;

import nuthatch.engine.Engine;

public abstract class Before extends Aspect {

	public Before(Strategy s) {
		super(s);
	}


	@Override
	public int visitAfter(Engine eng) {
		return PROCEED;
	}

}
