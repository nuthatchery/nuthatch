package nuthatch.engine;

import nuthatch.tree.Tree;

public abstract class SimpleTransform implements Transform {

	@Override
	public Tree apply(Engine engine) {
		return apply(engine.getCurrent());
	}
	
	public abstract Tree apply(Tree tree);

}
