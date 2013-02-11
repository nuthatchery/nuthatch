package nuthatch.walk.impl;

import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Action;
import nuthatch.walk.Step;
import nuthatch.walker.Walker;

public abstract class AbstractTraversalStep<Value, Type, E extends Walker<Value, Type>> implements Step<E> {
	protected static final int FIRST = Tree.FIRST;
	protected static final int LAST = Tree.LAST;
	protected static final int PARENT = Tree.PARENT;


	protected void act(Action<Value, Type> transform, E engine) {
		TreeCursor<Value, Type> result = transform.apply(engine);
		if(result != null) {
			engine.replace(result);
		}
	}
}
