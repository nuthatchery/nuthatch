package nuthatch.library.strategies;

import nuthatch.engine.Engine;
import nuthatch.strategy.AbstractStrategy;
import nuthatch.strategy.Transform;

public class BottomupStrategy extends AbstractStrategy {
	private final Transform transform;


	public BottomupStrategy(Transform t) {
		this.transform = t;
	}


	@Override
	public int visit(Engine eng) {
		if(eng.isLeaf() || eng.from(LAST)) {
			eng.transform(transform);
		}
		return eng.from() + 1;
	}
}
