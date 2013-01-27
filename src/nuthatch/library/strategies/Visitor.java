package nuthatch.library.strategies;

import nuthatch.engine.Engine;
import nuthatch.strategy.AbstractStrategy;

public class Visitor<Value, Type> extends AbstractStrategy<Value, Type> {

	/**
	 * This method is called when a node is entered.
	 * 
	 * By default it does nothing.
	 * 
	 * @param eng
	 *            The engine
	 */
	public void onEntry(Engine<Value, Type> eng) {
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
	public void beforeChild(Engine<Value, Type> eng, int child) {
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
	public void afterChild(Engine<Value, Type> eng, int child) {
	}


	/**
	 * This method is called when a node is exited.
	 * 
	 * By default it does nothing.
	 * 
	 * @param eng
	 */
	public void onExit(Engine<Value, Type> eng) {
	}


	@Override
	public final int visit(Engine<Value, Type> eng) {
		if(eng.isLeaf()) {
			onEntry(eng);
			onExit(eng);
		}
		else if(eng.from(PARENT)) {
			onEntry(eng);
			beforeChild(eng, eng.from() + 1);
		}
		else if(eng.from(LAST)) {
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
