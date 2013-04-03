package nuthatch.library.walks;

import nuthatch.walk.Step;
import nuthatch.walk.Walk;

public abstract class ComposableStep<W extends Walk<?, ?>> implements Step<W> {
	public static final int PROCEED = Integer.MIN_VALUE;

	Step<W> chain;


	public ComposableStep(Step<W> step) {
		chain = step;
	}


	@Override
	public int step(W walk) {
		int r = action(walk);
		if(r == PROCEED) {
			return chain.step(walk);
		}
		else {
			return r;
		}
	}


	protected abstract int action(W walk);
}
