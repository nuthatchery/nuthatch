package nuthatch.engine.impl;

import nuthatch.engine.Transform;
import nuthatch.tree.Tree;

public class Topdown extends AbstractStrategicEngine {
	public Topdown(Tree tree, Transform transform) {
		super(tree, transform);
	}

	protected Topdown(Tree tree, Tree top, Transform transform) {
		super(tree, top, transform);
	}

	@Override
	public int visit() {
		if(from(parent))
			transform();
		return from() + 1;
	}
	
	@Override
	protected Topdown clone(Tree tree, Tree top) {
		return new Topdown(tree, top, getTransform());
	}
}
