package nuthatch.library;

import nuthatch.walker.Walker;

public abstract class BaseComposedWalk<W extends Walker<?, ?, W>> implements Walk<W> {

	protected final Walk<W> walk;


	public BaseComposedWalk(Walk<W> walk) {
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
