package nuthatch.engine.impl;

import nuthatch.engine.Engine;
import nuthatch.engine.Transform;

public class VisitStrategy extends AbstractStrategy {
	private final Transform pre;
	private final Transform beforeChild;
	private final Transform afterChild;
	private final Transform post;


	public VisitStrategy(Transform pre, Transform post, Transform beforeChild, Transform afterChild) {
		this.pre = pre;
		this.post = post;
		this.beforeChild = beforeChild;
		this.afterChild = afterChild;
	}


	@Override
	public int visit(Engine e) {
		if(e.isLeaf()) {
			e.transform(pre);
			e.transform(post);
		}
		else if(e.from(PARENT)) {
			e.transform(pre);
			e.transform(beforeChild);
		}
		else if(e.from(LAST)) {
			e.transform(afterChild);
			e.transform(post);
		}
		else {
			e.transform(afterChild);
			e.transform(beforeChild);
		}

		return e.from() + 1;
	}
}
