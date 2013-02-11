package nuthatch.library.walks;

import nuthatch.walk.Action;
import nuthatch.walk.impl.AbstractTraversalStep;
import nuthatch.walker.Walker;

public class Bottomup<Value, Type, W extends Walker<Value, Type>> extends AbstractTraversalStep<Value, Type, W> {
	private final Action<Value, Type> action;


	public Bottomup(Action<Value, Type> action) {
		this.action = action;
	}


	@Override
	public int step(W walker) {
		if(walker.isLeaf() || walker.from(LAST)) {
			act(action, walker);
		}
		return walker.from() + 1;
	}
}
