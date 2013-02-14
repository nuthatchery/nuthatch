package nuthatch.library.walks;

import nuthatch.tree.Tree;
import nuthatch.walk.Step;
import nuthatch.walk.Walk;

public class VisitorAspect<W extends Walk<?, ?>> implements Step<W> {
	public static final int PROCEED = Integer.MIN_VALUE;
	private final Step<W> strategy;


	public VisitorAspect(Step<W> s) {
		strategy = s;
	}


	/**
	 * This method is called on every visit to a node, after the visit() method.
	 * 
	 * If the return value is not PROCEED, it will override whatever the main
	 * visit method returned.
	 * 
	 * @param walker
	 *            The walker
	 * @param go
	 *            Where the walker is going next
	 * @return PROCEED, or direction override
	 */
	public int after(W walker, int go) {
		return PROCEED;
	}


	/**
	 * This method is called on every visit to a node, before the visit()
	 * method.
	 * 
	 * If the return value is not PROCEED, the visit() method will not be
	 * called, and the walker will go in the given direction. If the direction
	 * is PARENT, beforeExit() may be called if applicable.
	 * 
	 * @param e
	 *            The walker
	 * @return PROCEED, or a direction override
	 */
	public int before(W e) {
		return PROCEED;

	}


	/**
	 * This method is called when a node is entered from the parent, before the
	 * main visiting is performed.
	 * 
	 * @param e
	 *            The walker
	 */
	public void beforeEntry(W e) {
	}


	/**
	 * This method is called after visiting a node, before going to the parent.
	 * 
	 * @param e
	 *            The walker
	 */
	public void beforeExit(W e) {
	}


	@Override
	public int step(W e) {
		if(e.from(Tree.PARENT)) {
			beforeEntry(e);
		}

		int go = before(e);
		if(go != PROCEED) {
			if(go == Tree.PARENT) {
				beforeExit(e);
			}
			return go;
		}

		go = strategy.step(e);

		int goAfter = after(e, go);
		if(goAfter != PROCEED) {
			go = goAfter;
		}

		if(go == Tree.PARENT) {
			beforeExit(e);
		}

		return go;
	}
}
