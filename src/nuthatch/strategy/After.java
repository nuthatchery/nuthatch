package nuthatch.strategy;

import nuthatch.engine.Engine;

public abstract class After<Value, Type> extends Aspect<Value, Type> {

	public After(Strategy<Value, Type> s) {
		super(s);
	}


	@Override
	public int visitBefore(Engine<Value, Type> eng) {
		return PROCEED;
	}

}
