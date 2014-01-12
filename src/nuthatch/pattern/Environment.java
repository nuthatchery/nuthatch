package nuthatch.pattern;

import java.util.Iterator;

public interface Environment<Value> extends Iterable<Object> {
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
	Environment<Value> enterScope();


	/**
	 * Exit a scope, abandoning all bindings in the current scope.
	 * 
	 * May return the same or a different object.
	 * 
	 * @return An environment with tall the top-level bindings discarded
	 */
	Environment<Value> exitScope();


	/**
	 * Lookup a variable.
	 * 
	 * @param var
	 *            Name of the variable
	 * @return The value, or null if not found
	 */
	<K> Value get(K var);


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
	Iterator<Object> iterator();


	/**
	 * Bind a new variable, overriding any previous binding.
	 * 
	 * @param var
	 *            Name of the variable
	 * @param value
	 *            The value
	 */
	<K> void put(K var, Value value);


	/**
	 * Rollback all bindings entered since the last begin().
	 */
	void rollback();
}
