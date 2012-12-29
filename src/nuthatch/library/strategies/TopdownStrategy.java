package nuthatch.library.strategies;

import nuthatch.engine.Engine;
import nuthatch.strategy.AbstractStrategy;
import nuthatch.strategy.Transform;

public class TopdownStrategy extends AbstractStrategy {
	private final Transform transform;


	public TopdownStrategy(Transform transform) {
		this.transform = transform;
	}


	@Override
	public int visit(Engine e) {
		if(e.from(PARENT)) {
			e.transform(transform);
		}
		return e.from() + 1;
	}
}
