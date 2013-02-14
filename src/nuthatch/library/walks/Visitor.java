package nuthatch.library.walks;

import nuthatch.tree.Tree;
import nuthatch.walk.Step;
import nuthatch.walk.Walk;

public abstract class Visitor<W extends Walk<?, ?>> implements Step<W> {

	/**
	 * This method is called after a child is visited.
	 * 
	 * By default it does nothing.
	 * 
	 * @param walker
	 *            The walker
	 * @param child
	 *            The branch number of the child we just visited
	 */
	public abstract void afterChild(W walker, int child);


	/**
	 * This method is called before a child is visited.
	 * 
	 * By default it does nothing.
	 * 
	 * @param walker
	 *            The walker
	 * @param child
	 *            The branch number of the child we are going to visit
	 */
	public abstract void beforeChild(W walker, int child);


	/**
	 * This method is called when a node is entered from the parent.
	 * 
	 * Visiting a leaf will call {@link #onEntry(Walk)} then
	 * {@link #onExit(Walk)}.
	 * 
	 * By default it does nothing.
	 * 
	 * @param walker
	 *            The walker
	 */
	public abstract void onEntry(W walker);


	/**
	 * This method is called when a node is exited in the parent direction.
	 * 
	 * Visiting a leaf will call {@link #onEntry(Walk)} then
	 * {@link #onExit(Walk)}.
	 * 
	 * By default it does nothing.
	 * 
	 * @param walker
	 */
	public abstract void onExit(W walker);


	@Override
	public final int step(W walker) {
		if(walker.isLeaf()) {
			onEntry(walker);
			onExit(walker);
		}
		else if(walker.from(Tree.PARENT)) {
			onEntry(walker);
			beforeChild(walker, walker.from() + 1);
		}
		else if(walker.from(Tree.LAST)) {
			afterChild(walker, walker.from());
			onExit(walker);
		}
		else {
			afterChild(walker, walker.from());
			beforeChild(walker, walker.from() + 1);
		}

		return walker.from() + 1;
	}

}
