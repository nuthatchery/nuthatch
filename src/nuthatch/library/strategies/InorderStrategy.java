package nuthatch.library.strategies;

import nuthatch.engine.Engine;
import nuthatch.strategy.AbstractStrategy;
import nuthatch.strategy.Transform;

public class InorderStrategy extends AbstractStrategy {
	private final Transform pre;
	private final Transform mid;
	private final Transform post;


	public InorderStrategy(Transform pre, Transform mid, Transform post) {
		this.pre = pre;
		this.mid = mid;
		this.post = post;
	}


	@Override
	public int visit(Engine e) {
		if(e.isLeaf() || e.from(FIRST)) {
			e.transform(mid);
		}
		else if(e.from(PARENT)) {
			e.transform(pre);
		}
		else if(e.from(LAST)) {
			e.transform(post);
		}
		return e.from() + 1;
	}
}
