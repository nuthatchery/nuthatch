package nuthatch.library.walks;

import nuthatch.walk.Walk;

public interface Action<W extends Walk<?, ?>> {
	public static final int PROCEED = ComposableStep.PROCEED;


	/**
	 * Perform an action.
	 * 
	 * @param walk
	 *            The current walk
	 * @return PROCEED if we should continue, or a branch number if we want to
	 *         redirect the walk
	 */
	int action(W walk);

}
