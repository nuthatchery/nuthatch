package nuthatch.library;

import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

/**
 * @author anya
 * 
 * @param <Value>
 * @param <Type>
 * @param <C>
 * @param <W>
 */
public interface MatchBuilder<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> {
	/**
	 * Add a new pattern and action.
	 * 
	 * @param pattern
	 *            The pattern
	 * @param action
	 *            The action to be taken if the patten matches
	 */
	void add(Pattern<Value, Type> pattern, MatchAction<Value, Type, C, W> action);


	/**
	 * Add a new pattern and an action that builds a replacement
	 * 
	 * @param pattern
	 *            The pattern
	 * @param replacement
	 *            The replacement to be build if the pattern matches
	 */
	void add(Pattern<Value, Type> pattern, Pattern<Value, Type> replacement);


	/**
	 * @return An action that will execute the actions for all patterns that
	 *         match
	 */
	Action<W> all();


	/**
	 * @return An action that propagates the surrounding environment and will
	 *         execute the actions for all patterns that match
	 */
	MatchAction<Value, Type, C, W> allEnv();


	/**
	 * @return An action that will execute the actions for only one matching
	 *         pattern
	 */
	Action<W> one();


	/**
	 * @return An action that propagates the surrounding environment and will
	 *         execute the actions for only one matchin pattern
	 */
	MatchAction<Value, Type, C, W> oneEnv();
}
