package nuthatch.library.strategies;

import nuthatch.engine.Engine;
import nuthatch.strategy.Strategy;
import nuthatch.tree.Tree;

public class Visitor<E extends Engine<?, ?>> implements Strategy<E> {

	/**
	 * This method is called after a child is visited.
	 * 
	 * By default it does nothing.
	 * 
	 * @param e
	 *            The engine
	 * @param child
	 *            The branch number of the child we just visited
	 */
	public void afterChild(E e, int child) {
	}


	/**
	 * This method is called before a child is visited.
	 * 
	 * By default it does nothing.
	 * 
	 * @param e
	 *            The engine
	 * @param child
	 *            The branch number of the child we are going to visit
	 */
	public void beforeChild(E e, int child) {
	}


	/**
	 * This method is called when a node is entered from the parent.
	 * 
	 * Visiting a leaf will call {@link #onEntry(Engine)} then
	 * {@link #onExit(Engine)}.
	 * 
	 * By default it does nothing.
	 * 
	 * @param e
	 *            The engine
	 */
	public void onEntry(E e) {
	}


	/**
	 * This method is called when a node is exited in the parent direction.
	 * 
	 * Visiting a leaf will call {@link #onEntry(Engine)} then
	 * {@link #onExit(Engine)}.
	 * 
	 * By default it does nothing.
	 * 
	 * @param e
	 */
	public void onExit(E e) {
	}


	@Override
	public final int visit(E e) {
		if(e.isLeaf()) {
			onEntry(e);
			onExit(e);
		}
		else if(e.from(Tree.PARENT)) {
			onEntry(e);
			beforeChild(e, e.from() + 1);
		}
		else if(e.from(Tree.LAST)) {
			afterChild(e, e.from());
			onExit(e);
		}
		else {
			afterChild(e, e.from());
			beforeChild(e, e.from() + 1);
		}

		return e.from() + 1;
	}

}
