package nuthatch.library.strategies;

import nuthatch.engine.Engine;
import nuthatch.strategy.AbstractStrategy;
import nuthatch.strategy.Transform;

public class InorderStrategy<Value, Type> extends AbstractStrategy<Value, Type> {
	private final Transform<Value, Type> pre;
	private final Transform<Value, Type> mid;
	private final Transform<Value, Type> post;


	public InorderStrategy(Transform<Value, Type> pre, Transform<Value, Type> mid, Transform<Value, Type> post) {
		this.pre = pre;
		this.mid = mid;
		this.post = post;
	}


	@Override
	public int visit(Engine<Value, Type> e) {
		if(e.isLeaf() || e.from(FIRST)) {
			applyTransform(mid, e);
		}
		else if(e.from(PARENT)) {
			applyTransform(pre, e);
		}
		else if(e.from(LAST)) {
			applyTransform(post, e);
		}
		return e.from() + 1;
	}
}
