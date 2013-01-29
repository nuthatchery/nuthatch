package nuthatch.strategy;

import nuthatch.engine.Engine;

public abstract class Aspect<E extends Engine<?, ?>> implements Strategy<E> {
	public static final int PROCEED = Integer.MIN_VALUE;
	private final Strategy<E> strategy;


	public Aspect(Strategy<E> s) {
		strategy = s;
	}


	@Override
	public int visit(E eng) {
		int goBefore = visitBefore(eng);
		if(goBefore == PROCEED) {
			int go = strategy.visit(eng);
			int goAfter = visitAfter(eng);
			if(goAfter == PROCEED) {
				return go;
			}
			else {
				return goAfter;
			}
		}
		else {
			return goBefore;
		}
	}


	public abstract int visitAfter(E eng);


	public abstract int visitBefore(E eng);

}
