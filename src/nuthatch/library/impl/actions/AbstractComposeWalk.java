package nuthatch.library.impl.actions;

import nuthatch.library.Walk;
import nuthatch.walker.Walker;

public abstract class AbstractComposeWalk<W extends Walker<?, ?>> implements Walk<W> {

	protected final Walk<W> walk;


	public AbstractComposeWalk(Walk<W> walk) {
		this.walk = walk;
	}


	@Override
	public void init(W walker) {
		walk.init(walker);
	}


	@Override
	public int step(W walker) {
		return walk.step(walker);
	}
}
