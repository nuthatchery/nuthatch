package nuthatch.strategy;

import nuthatch.engine.Engine;

public abstract class Before<E extends Engine<?, ?>> extends Aspect<E> {

	public Before(Strategy<E> s) {
		super(s);
	}


	@Override
	public int visitAfter(E eng) {
		return PROCEED;
	}

}
