package nuthatch.library.walks;

import nuthatch.walk.Action;
import nuthatch.walk.impl.AbstractTraversalStep;
import nuthatch.walker.Walker;

public class Inorder<Value, Type, W extends Walker<Value, Type>> extends AbstractTraversalStep<Value, Type, W> {
	private final Action<Value, Type> pre;
	private final Action<Value, Type> mid;
	private final Action<Value, Type> post;


	public Inorder(Action<Value, Type> pre, Action<Value, Type> mid, Action<Value, Type> post) {
		this.pre = pre;
		this.mid = mid;
		this.post = post;
	}


	@Override
	public int step(W walker) {
		if(walker.isLeaf() || walker.from(FIRST)) {
			act(mid, walker);
		}
		else if(walker.from(PARENT)) {
			act(pre, walker);
		}
		else if(walker.from(LAST)) {
			act(post, walker);
		}
		return walker.from() + 1;
	}
}
