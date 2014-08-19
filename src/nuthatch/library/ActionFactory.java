package nuthatch.library;

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
	 * @param b
	 *            A branch number
	 * @return An action that override the next step with b.
	 */
	public Action<W> go(int b);


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


	MatchAction<Value, Type, C, W> replace(Pattern<Value, Type> replacement);
}
