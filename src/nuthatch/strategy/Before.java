package nuthatch.strategy;

import nuthatch.engine.Engine;

public abstract class Before<Value, Type> extends Aspect<Value, Type> {

	public Before(Strategy<Value, Type> s) {
		super(s);
	}


	@Override
	public int visitAfter(Engine<Value, Type> eng) {
		return PROCEED;
	}

}
