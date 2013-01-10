package nuthatch.strategy;

import nuthatch.engine.Engine;

public abstract class Aspect implements Strategy {
	public final int PROCEED = Integer.MIN_VALUE;
	private final Strategy strategy;


	public abstract int visitBefore(Engine eng);


	public abstract int visitAfter(Engine eng);


	public Aspect(Strategy s) {
		strategy = s;
	}


	@Override
	public int visit(Engine eng) {
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
