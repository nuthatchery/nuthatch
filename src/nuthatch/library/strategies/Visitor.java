package nuthatch.library.strategies;

import nuthatch.engine.Engine;
import nuthatch.strategy.Strategy;
import nuthatch.tree.Tree;

public class Visitor<E extends Engine<?, ?>> implements Strategy<E> {

	/**
	 * This method is called when a node is entered.
	 * 
	 * By default it does nothing.
	 * 
	 * @param eng
	 *            The engine
	 */
	public void onEntry(E eng) {
	}


	/**
	 * This method is called before a child is visited.
	 * 
	 * By default it does nothing.
	 * 
	 * @param eng
	 *            The engine
	 * @param child
	 *            The branch number of the child we are going to visit
	 */
	public void beforeChild(E eng, int child) {
	}


	/**
	 * This method is called after a child is visited.
	 * 
	 * By default it does nothing.
	 * 
	 * @param eng
	 *            The engine
	 * @param child
	 *            The branch number of the child we just visited
	 */
	public void afterChild(E eng, int child) {
	}


	/**
	 * This method is called when a node is exited.
	 * 
	 * By default it does nothing.
	 * 
	 * @param eng
	 */
	public void onExit(E eng) {
	}


	@Override
	public final int visit(E eng) {
		if(eng.isLeaf()) {
			onEntry(eng);
			onExit(eng);
		}
		else if(eng.from(Tree.PARENT)) {
			onEntry(eng);
			beforeChild(eng, eng.from() + 1);
		}
		else if(eng.from(Tree.LAST)) {
			afterChild(eng, eng.from());
			onExit(eng);
		}
		else {
			afterChild(eng, eng.from());
			beforeChild(eng, eng.from() + 1);
		}

		return eng.from() + 1;
	}

}
