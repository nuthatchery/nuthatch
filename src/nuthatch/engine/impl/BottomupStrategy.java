package nuthatch.engine.impl;

import nuthatch.engine.Engine;
import nuthatch.engine.Strategy;
import nuthatch.engine.Transform;
import nuthatch.tree.Tree;

public class BottomupStrategy extends AbstractStrategy {
	private final Transform transform;
	
	public BottomupStrategy(Transform t) {
		this.transform = t;
	}
	@Override
	public int visit(Engine eng) {
		if(eng.isLeaf() || eng.from(LAST))
			eng.transform(transform);
		return eng.from() + 1;
	}
}
