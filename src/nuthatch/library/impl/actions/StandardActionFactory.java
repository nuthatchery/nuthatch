package nuthatch.library.impl.actions;

import java.util.Comparator;

import nuthatch.library.Action;
import nuthatch.library.ActionBuilder;
import nuthatch.library.BaseAction;
import nuthatch.library.MatchAction;
import nuthatch.library.MatchBuilder;
import nuthatch.library.Walk;
import nuthatch.library.impl.walks.Default;
import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

public class StandardActionFactory<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> implements nuthatch.library.ActionFactory<Value, Type, C, W> {
	@SuppressWarnings("rawtypes")
	private static final StandardActionFactory instance = new StandardActionFactory();

	@SuppressWarnings("rawtypes")
	private static final NopAction nopAction = new NopAction();


	/**
	 * Execute an action as a match action, dropping the environment.
	 * 
	 * @param action
	 *            An action
	 * @return A corresponding match action
	 */
	@Override
	public MatchAction<Value, Type, C, W> action(final Action<W> action) {
		return new NoMatchAction<Value, Type, C, W>(action);
	}


	/**
	 * Create an 'after child' joinpoint action for the default tree walk.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if the walker's came from a
	 *         child
	 */
	@Override
	public Action<W> afterChild(Action<W> action) {
		return new CondActions.AfterChild<W>(action);
	}


	/**
	 * Create atLeaf conditional action.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if walker is at a leaf.
	 */
	@Override
	public Action<W> atLeaf(Action<W> action) {
		return new CondActions.Leaf<W>(action);
	}


	/**
	 * Create atNonLeaf conditional action.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if walker is not at a leaf.
	 */
	@Override
	public Action<W> atNonLeaf(Action<W> action) {
		return new CondActions.Leaf<W>(action, true);
	}


	/**
	 * Create atRoot conditional action.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if walker is at the root.
	 */
	@Override
	public Action<W> atRoot(Action<W> action) {
		return new CondActions.Root<W>(action);
	}


	/**
	 * Create a 'before child' joinpoint action for the default tree walk.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if the walker's next move is to
	 *         a child
	 */
	@Override
	public Action<W> beforeChild(Action<W> action) {
		return new CondActions.BeforeChild<W>(action);
	}


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
	@Override
	@SafeVarargs
	public final Action<W> combine(Action<W>... actions) {
		return new Combine<W>(actions);
	}


	@Override
	public ActionBuilder<W> combineBuilder() {
		return new CombineBuilder<W>();
	}


	/**
	 * Create a 'down' joinpoint action for the default tree walk.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if walker is on the way down
	 */
	@Override
	public Action<W> down(Action<W> action) {
		return new CondActions.Down<W>(action);
	}


	/**
	 * Create an 'up' or 'down' joinpoint action for the default tree walk.
	 * 
	 * @param downAction
	 *            Action to be performed on the way down
	 * @param upAction
	 *            Action to be performed on the way up
	 * @return An action that performs actions on the way up or down
	 */
	@Override
	public Action<W> downup(Action<W> downAction, Action<W> upAction) {
		return new Combine<W>(new CondActions.Down<W>(downAction), new CondActions.Up<W>(upAction));
	}


	@Override
	public Action<W> from(int branch, Action<W> action) {
		return new CondActions.From(branch, action);
	}


	@Override
	public Action<W> go(int branch) {
		return new GoAction<W>(branch);
	}


	@Override
	public Action<W> hasArity(int arity, Action<W> action) {
		return new CondActions.HasArity<>(arity, action);
	}


	@Override
	public Action<W> hasType(Type type, Action<W> action) {
		return new CondActions.HasType<Type, W>(type, action);
	}


	@Override
	public Action<W> hasType(Type type, Comparator<Type> comp, Action<W> action) {
		return new CondActions.HasTypeComp<Type, W>(type, comp, action);
	}


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
	@Override
	public Action<W> match(Pattern<Value, Type> pat, MatchAction<Value, Type, C, W> action) {
		return new Match<Value, Type, C, W>(pat, action);
	}


	@Override
	public MatchBuilder<Value, Type, C, W> matchBuilder(BuildContext<Value, Type, C> context) {
		return new MatchActionBuilder<>(context);
	}


	@Override
	@SuppressWarnings("unchecked")
	public Action<W> nop() {
		return nopAction;
	}


	@Override
	public Action<W> redirect(Action<W> action, int... redirections) {
		int max = 0;

		assert redirections.length % 2 == 0;

		for(int i = 0; i < redirections.length; i += 2) {
			max = Math.max(max, redirections[i]);
		}

		int[] redirects = new int[max+1];

		for(int i = 0; i < redirections.length; i += 2) {
			redirects[redirections[i]] = redirections[i+1];
		}

		return new RedirectAction<W>(action, redirects);
	}

	@Override
	public Action<W> redirect(int... redirections) {
		return redirect(null, redirections);
	}


	@Override
	public MatchAction<Value, Type, C, W> replace(C replacement) {
		return new ReplaceByTree<Value, Type, C, W>(replacement);
	}


	@Override
	public MatchAction<Value, Type, C, W> replace(Pattern<Value, Type> replacement) {
		return new ReplaceByPattern<Value, Type, C, W>(replacement);
	}


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
	@Override
	@SafeVarargs
	public final Action<W> seq(Action<W>... actions) {
		return new Combine<W>(actions);
	}


	@Override
	public ActionBuilder<W> sequenceBuilder() {
		return new SequenceBuilder<W>();
	}


	/**
	 * Create an 'up' joinpoint action for the default tree walk.
	 * 
	 * @param action
	 *            Action to be performed
	 * @return An action that performs 'action' if the walker is on its way up
	 */
	@Override
	public Action<W> up(Action<W> action) {
		return new CondActions.Up<W>(action);
	}


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
	@Override
	public Walk<W> walk(Action<W> action) {
		return new Default<W>(action);
	}


	public static <Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> StandardActionFactory<Value, Type, C, W> getInstance() {
		return instance;
	}


	static final class GoAction<W extends Walker<?, ?>> extends BaseAction<W> {
		private final int branch;


		public GoAction(int branch) {
			this.branch = branch;
		}


		@Override
		public int step(W walker) {
			return branch;
		}
	}


	static final class NoMatchAction<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> implements MatchAction<Value, Type, C, W> {
		private final Action<W> action;


		NoMatchAction(Action<W> action) {
			this.action = action;
		}


		@Override
		public void init(W walker) {
			action.init(walker);
		}


		@Override
		public int step(W walker, Environment<C> env) {
			return action.step(walker);
		}
	}


	static final class NopAction<W extends Walker<?, ?>> extends BaseAction<W> {
		@Override
		public int step(W walker) {
			return PROCEED;
		}
	}
}
