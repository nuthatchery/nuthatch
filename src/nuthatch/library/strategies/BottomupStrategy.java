package nuthatch.library.strategies;

import nuthatch.engine.Engine;
import nuthatch.strategy.AbstractStrategy;
import nuthatch.strategy.Transform;

public class BottomupStrategy<Value, Type> extends AbstractStrategy<Value, Type> {
	private final Transform<Value, Type> transform;


	public BottomupStrategy(Transform<Value, Type> t) {
		this.transform = t;
	}


	@Override
	public int visit(Engine<Value, Type> eng) {
		if(eng.isLeaf() || eng.from(LAST)) {
			applyTransform(transform, eng);
		}
		return eng.from() + 1;
	}
}
