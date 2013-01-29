package nuthatch.strategy;

import nuthatch.engine.Engine;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;

public abstract class AbstractStrategy<Value, Type, E extends Engine<Value, Type>> implements Strategy<E> {
	protected static final int FIRST = Tree.FIRST;
	protected static final int LAST = Tree.LAST;
	protected static final int PARENT = Tree.PARENT;


	protected void applyTransform(Transform<Value, Type> transform, E engine) {
		TreeCursor<Value, Type> result = transform.apply(engine);
		if(result != null) {
			engine.replace(result);
		}
	}
}
