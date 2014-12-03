package nuthatch.library;

import java.util.Comparator;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

public interface ActionFactory<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> {
	public static final ActionFactory<?, ?, ?, ?> actionFactory = nuthatch.library.impl.actions.StandardActionFactory.getInstance();


	/**
	 * Create an 'after child' joinpoint action for the default tree walk.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if the walker's came from a
	 *         child
	 */
	public Action<W> afterChild(Action<W> action);


	/**
	 * Create atLeaf conditional action.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if walker is at a leaf.
	 */
	public Action<W> atLeaf(Action<W> action);


	/**
	 * Create atRoot conditional action.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if walker is at the root.
	 */
	public Action<W> atRoot(Action<W> action);


	/**
	 * Create a 'before child' joinpoint action for the default tree walk.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if the walker's next move is to
	 *         a child
	 */
	public Action<W> beforeChild(Action<W> action);


	/**
	 * Create a combined action.
	 * 
	 * When the resulting action is run, all the arguments actions will be
	 * performed.
	 * If any of them returns something other than PROCEED, the combined action
	 * will abort.
	 * 
	 * Warning: you should not rely on the actions being performed in a
	 * particular order. Use {@link #seq(Action...)} for that.
	 * 
	 * @param actions
	 *            The actions to be performed
	 * @return An action that performs 'action' all the given actions.
	 */
	public Action<W> combine(@SuppressWarnings("unchecked") Action<W>... actions);


	public ActionBuilder<W> combineBuilder();


	/**
	 * Create a 'down' joinpoint action for the default tree walk.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if walker is on the way down
	 */
	public Action<W> down(Action<W> action);


	/**
	 * Create an 'up' or 'down' joinpoint action for the default tree walk.
	 * 
	 * @param downAction
	 *            Action to be performed on the way down
	 * @param upAction
	 *            Action to be performed on the way up
	 * @return An action that performs actions on the way up or down
	 */
	public Action<W> downup(Action<W> downAction, Action<W> upAction);


	/**
	 * Create conditional action.
	 * 
	 * 
	 * @param branch
	 *            The branch number to check form
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' only if we arrived at the
	 *         current node through 'branch'
	 */
	public Action<W> from(int branch, Action<W> action);


	/**
	 * @param b
	 *            A branch number
	 * @return An action that override the next step with b.
	 */
	public Action<W> go(int b);


	/**
	 * Create conditional action.
	 * 
	 * 
	 * @param arity
	 *            The desired arity
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' only if the current node has the
	 *         given arity
	 */
	public Action<W> hasArity(int arity, Action<W> action);


	/**
	 * Create conditional action.
	 * 
	 * Types are compared using {@link Type#equals(Object)}.
	 * 
	 * @param type
	 *            The desired type
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' only if the current node has the
	 *         given type
	 */
	public Action<W> hasType(Type type, Action<W> action);


	/**
	 * Create conditional action.
	 * 
	 * Types are compared using the supplied comparator, and the
	 * action is only taken if it returns 0. The type of the current node will
	 * the be the first argument, and the supplied type will be the second.
	 * 
	 * E.g., the action will be: if comparator.compare(walker.getType(), type)
	 * == 0 then perform 'action'.
	 * 
	 * @param type
	 *            The desired type
	 * @param comparator
	 *            A comparator to determine if two types are equal
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' only if the current node has the
	 *         given type
	 */
	public Action<W> hasType(Type type, Comparator<Type> comparator, Action<W> action);


	/**
	 * Create matching conditional action.
	 * 
	 * The variables in the patterns will be bound in the environment given to
	 * the action.
	 * 
	 * @param pat
	 *            A pattern
	 * @param action
	 *            Action to be performed if pattern matches
	 * @return An action that performs 'action' if the pattern matches
	 */
	public Action<W> match(Pattern<Value, Type> pat, MatchAction<Value, Type, C, W> action);


	public MatchBuilder<Value, Type, C, W> matchBuilder(BuildContext<Value, Type, C> context);


	/**
	 * @return An action that does nothing.
	 */
	public Action<W> nop();


	/**
	 * Create a replacement action.
	 * 
	 * The result will be a MatchAction which, given an environment,
	 * replaces the current node with a new the replacement.
	 * 
	 * @param replacement
	 *            A replacement tree cursor
	 * @return A match action that performs the replacement
	 */
	public MatchAction<Value, Type, C, W> replace(C replacement);


	/**
	 * Create a sequential combined action.
	 * 
	 * When the resulting action is run, all the arguments actions will be
	 * performed in sequence, until all are done, or one returns something other
	 * than PROCEED.
	 * 
	 * If any of them returns something other than PROCEED, the combined action
	 * will abort.
	 * 
	 * Use {@link #combine(Action...)} for a combinator which is
	 * order-independent.
	 * 
	 * @param actions
	 *            The actions to be performed
	 * @return An action that performs 'action' all the given actions.
	 */
	public Action<W> seq(@SuppressWarnings("unchecked") Action<W>... actions);


	public ActionBuilder<W> sequenceBuilder();


	/**
	 * Create an 'up' joinpoint action for the default tree walk.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if the walker is on its way up
	 */
	public Action<W> up(Action<W> action);


	/**
	 * Create a new default walk.
	 * 
	 * The given action may override the default walk if its step method returns
	 * a branch number instead of PROCEED.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return A walk following the default path through the tree, performing
	 *         'action'.
	 */
	public Walk<W> walk(Action<W> action);


	/**
	 * Execute an action as a match action, dropping the environment.
	 * 
	 * @param action
	 *            An action
	 * @return A corresponding match action
	 */
	MatchAction<Value, Type, C, W> action(Action<W> action);


	/**
	 * Create atNonLeaf conditional action.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if walker is not at a leaf.
	 */
	Action<W> atNonLeaf(Action<W> action);

	/**
	 * Redirect the next step according to the list of redirections.
	 * 
	 * Redirections should be a list of pairs, from1, to1, from2, to2... so that if
	 * the next step will go to from1, this action will redirect it to to1 instead.
	 * 
	 * If the given action specifies which branch to follow next, this action may
	 * override that.
	 * 
	 * @param action An action to be taken.
	 * @param redirections A list of from1, to1, from2, to2... redirection
	 * @return A redirecting action
	 */
	Action<W> redirect(Action<W> action, int... redirections);

	/**
	 * Redirect the next step according to the list of redirections.
	 * 
	 * Redirections should be a list of pairs, from1, to1, from2, to2... so that if
	 * the next step will go to from1, this action will redirect it to to1 instead.
	 * 
	 * @param redirections A list of from1, to1, from2, to2... redirection
	 * @return A redirecting action
	 */
	Action<W> redirect(int... redirections);

	MatchAction<Value, Type, C, W> replace(Pattern<Value, Type> replacement);
}
