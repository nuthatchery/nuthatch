package nuthatch.engine;

import nuthatch.tree.Tree;

public abstract class SimpleTransform implements Transform {

	@Override
	public Tree apply(Engine engine) {
		return apply(engine.currentTree());
	}

	/**
	 * Apply the transformation.
	 * 
	 * Override {@link #apply(Engine)} orr implement {@link Transform} directly
	 * if you need more control, or information about current state.
	 * 
	 * @param tree
	 *            The current tree node
	 * @return A replacement subtree, or null if no change
	 */
	public abstract Tree apply(Tree tree);

}
