package nuthatch.strategy;

import nuthatch.engine.Engine;

public abstract class Aspect<Value, Type> implements Strategy<Value, Type> {
	public static final int PROCEED = Integer.MIN_VALUE;
	private final Strategy<Value, Type> strategy;


	public abstract int visitBefore(Engine<Value, Type> eng);


	public abstract int visitAfter(Engine<Value, Type> eng);


	public Aspect(Strategy<Value, Type> s) {
		strategy = s;
	}


	@Override
	public int visit(Engine<Value, Type> eng) {
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

}
