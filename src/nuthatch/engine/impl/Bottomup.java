package nuthatch.engine.impl;

import nuthatch.engine.Transform;
import nuthatch.tree.Tree;

public class Bottomup extends AbstractStrategicEngine {
	public Bottomup(Tree tree, Transform transform) {
		super(tree, transform);
	}

	protected Bottomup(Tree tree, Tree top, Transform transform) {
		super(tree, top, transform);
	}

	@Override
	public int visit() {
		if(isLeaf() || from(last))
			transform();
		return from() + 1;
	}

	@Override
	protected Bottomup clone(Tree tree, Tree top) {
		return new Bottomup(tree, top, getTransform());
	}

}
