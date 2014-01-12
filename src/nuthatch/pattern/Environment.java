package nuthatch.pattern;

import java.util.Iterator;

public interface Environment<Key, Value> extends Iterable<Key> {
	/**
	 * Begin a transaction.
	 * 
	 */
	void begin();


	/**
	 * Commit all bindings entered since the last begin().
	 */
	void commit();


	/**
	 * Enter a new scope.
	 * 
	 * May return the same or a different object.
	 * 
	 * @return An environment with the empty scope at the top.
	 */
	Environment<Key, Value> enterScope();


	/**
	 * Exit a scope, abandoning all bindings in the current scope.
	 * 
	 * May return the same or a different object.
	 * 
	 * @return An environment with tall the top-level bindings discarded
	 */
	Environment<Key, Value> exitScope();


	/**
	 * Lookup a variable.
	 * 
	 * @param var
	 *            Name of the variable
	 * @return The value, or null if not found
	 */
	Value get(Key var);


	/**
	 * @return True if no variables are bound in any scope
	 */
	boolean isEmpty();


	/**
	 * @return True if no variables are bound in current scope
	 */
	boolean isEmptyScope();


	/**
	 * Iterate over all the variable names in the environment.
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	Iterator<Key> iterator();


	/**
	 * Bind a new variable, overriding any previous binding.
	 * 
	 * @param var
	 *            Name of the variable
	 * @param value
	 *            The value
	 */
	void put(Key var, Value value);


	/**
	 * Rollback all bindings entered since the last begin().
	 */
	void rollback();
}
