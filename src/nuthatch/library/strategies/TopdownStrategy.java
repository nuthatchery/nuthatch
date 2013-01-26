package nuthatch.library.strategies;

import nuthatch.engine.Engine;
import nuthatch.strategy.AbstractStrategy;
import nuthatch.strategy.Transform;

public class TopdownStrategy<Value, Type> extends AbstractStrategy<Value, Type> {
	private final Transform<Value, Type> transform;


	public TopdownStrategy(Transform<Value, Type> transform) {
		this.transform = transform;
	}


	@Override
	public int visit(Engine<Value, Type> e) {
		if(e.from(PARENT)) {
			applyTransform(transform, e);
		}
		return e.from() + 1;
	}
}
