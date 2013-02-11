package nuthatch.library.walks;

import nuthatch.tree.Tree;
import nuthatch.walk.Step;
import nuthatch.walker.Walker;

public class VisitorAspect<E extends Walker<?, ?>> implements Step<E> {
	public static final int PROCEED = Integer.MIN_VALUE;
	private final Step<E> strategy;


	public VisitorAspect(Step<E> s) {
		strategy = s;
	}


	/**
	 * This method is called on every visit to a node, after the visit() method.
	 * 
	 * If the return value is not PROCEED, it will override whatever the main
	 * visit method returned.
	 * 
	 * @param e
	 *            The engine
	 * @return PROCEED, or direction override
	 */
	public int after(E e) {
		return PROCEED;
	}


	/**
	 * This method is called on every visit to a node, before the visit()
	 * method.
	 * 
	 * If the return value is not PROCEED, the visit() method will not be
	 * called, and the engine will go in the given direction. If the direction
	 * is PARENT, beforeExit() may be called if applicable.
	 * 
	 * @param e
	 *            The engine
	 * @return PROCEED, or a direction override
	 */
	public int before(E e) {
		return PROCEED;

	}


	/**
	 * This method is called when a node is entered from the parent, before the
	 * main visiting is performed.
	 * 
	 * @param e
	 *            The engine
	 */
	public void beforeEntry(E e) {
	}


	/**
	 * This method is called after visiting a node, before going to the parent.
	 * 
	 * @param e
	 *            The engine
	 */
	public void beforeExit(E e) {
	}


	@Override
	public int step(E e) {
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

		int goAfter = after(e);
		if(goAfter != PROCEED) {
			go = goAfter;
		}

		if(go == Tree.PARENT) {
			beforeExit(e);
		}

		return go;
	}
}
