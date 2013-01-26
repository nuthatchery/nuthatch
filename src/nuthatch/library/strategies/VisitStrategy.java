package nuthatch.library.strategies;

import nuthatch.engine.Engine;
import nuthatch.strategy.AbstractStrategy;
import nuthatch.strategy.Transform;

public class VisitStrategy<Value, Type> extends AbstractStrategy<Value, Type> {
	private final Transform<Value, Type> pre;
	private final Transform<Value, Type> beforeChild;
	private final Transform<Value, Type> afterChild;
	private final Transform<Value, Type> post;


	public VisitStrategy(Transform<Value, Type> pre, Transform<Value, Type> post, Transform<Value, Type> beforeChild, Transform<Value, Type> afterChild) {
		this.pre = pre;
		this.post = post;
		this.beforeChild = beforeChild;
		this.afterChild = afterChild;
	}


	@Override
	public int visit(Engine<Value, Type> e) {
		if(e.isLeaf()) {
			applyTransform(pre, e);
			applyTransform(post, e);
		}
		else if(e.from(PARENT)) {
			applyTransform(pre, e);
			applyTransform(beforeChild, e);
		}
		else if(e.from(LAST)) {
			applyTransform(afterChild, e);
			applyTransform(post, e);
		}
		else {
			applyTransform(afterChild, e);
			applyTransform(beforeChild, e);
		}

		return e.from() + 1;
	}
}
