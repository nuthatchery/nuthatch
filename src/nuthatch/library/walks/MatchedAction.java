package nuthatch.library.walks;

import nuthatch.pattern.Environment;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Walk;

public interface MatchedAction<Value, Type, C extends TreeCursor<Value, Type>, W extends Walk<Value, Type>> {
	public static final int PROCEED = ComposableStep.PROCEED;


	/**
	 * Perform an action, after a pattern has been matched.
	 * 
	 * @param walk
	 *            The current walk
	 * @param env
	 *            The environment, in which the variables of the pattern are
	 *            bound
	 * @return PROCEED if we should continue, or a branch number if we want to
	 *         redirect the walk
	 */

	int action(W walk, Environment<C> env);
}
