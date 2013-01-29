package nuthatch.strategy;

import nuthatch.engine.Engine;

public abstract class After<E extends Engine<?, ?>> extends Aspect<E> {

	public After(Strategy<E> s) {
		super(s);
	}


	@Override
	public int visitBefore(E eng) {
		return PROCEED;
	}

}
