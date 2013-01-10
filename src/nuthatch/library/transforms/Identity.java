package nuthatch.library.transforms;

import nuthatch.engine.Engine;
import nuthatch.strategy.Transform;
import nuthatch.tree.Tree;

public class Identity implements Transform {

	@Override
	public Tree apply(Engine engine) {
		return null;
	}

}
