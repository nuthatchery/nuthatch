package nuthatch.library;

import nuthatch.walker.Walker;

public abstract class AbstractVisitAction<W extends Walker<?, ?>> implements Action<W> {
	/**
	 * Visit a node.
	 * 
	 * This method is called whenever the walker visits a node, after visiting
	 * each child.
	 * 
	 * @param walker
	 *            The walker
	 * @param i
	 *            Number of the child just visited
	 * @return Next branch to visit, or PROCEED for default
	 */
	public int afterChild(W walker, int i) {
		return PROCEED;
	}


	/**
	 * Visit a node.
	 * 
	 * This method is called whenever the walker visits a node, before visiting
	 * each child.
	 * 
	 * @param walker
	 *            The walker
	 * @param i
	 *            Number of the child to visit
	 * @return Next branch to visit, or PROCEED for default
	 */
	public int beforeChild(W walker, int i) {
		return PROCEED;
	}


	/**
	 * Visit a node.
	 * 
	 * This method is called whenever the walker visits a node on the way down.
	 * 
	 * @param walker
	 *            The walker
	 * @return Next branch to visit, or PROCEED for default
	 */
	public int down(W walker) {
		return PROCEED;
	}


	@Override
	public void init(W walker) {
		// TODO Auto-generated method stub

	}


	@Override
	public final int step(W walker) {
		if(JoinPoints.down(walker)) {
			int r = down(walker);
			if(r != PROCEED) {
				return r;
			}
		}
		if(JoinPoints.beforeChild(walker)) {
			int r = beforeChild(walker, walker.from() + 1);
			if(r != PROCEED) {
				return r;
			}
		}
		if(JoinPoints.afterChild(walker)) {
			int r = afterChild(walker, walker.from());
			if(r != PROCEED) {
				return r;
			}
		}

		if(JoinPoints.up(walker)) {
			int r = up(walker);
			if(r != PROCEED) {
				return r;
			}
		}
		return PROCEED;
	}


	/**
	 * Visit a node.
	 * 
	 * This method is called whenever the walker visits a node on the way up.
	 * 
	 * @param walker
	 *            The walker
	 * @return Next branch to visit, or PROCEED for default
	 */
	public int up(W walker) {
		return PROCEED;
	}

}
