package nuthatch.library.transforms;

import nuthatch.engine.Engine;
import nuthatch.strategy.Transform;
import nuthatch.tree.TreeCursor;

public class Identity implements Transform {

	@Override
	public TreeCursor apply(Engine engine) {
		return null;
	}
}
