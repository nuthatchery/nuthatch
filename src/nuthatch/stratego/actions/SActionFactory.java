package nuthatch.stratego.actions;

import nuthatch.library.Action;
import nuthatch.library.ActionBuilder;
import nuthatch.library.ActionFactory;
import nuthatch.library.FactoryFactory;
import nuthatch.library.MatchAction;
import nuthatch.library.MatchBuilder;
import nuthatch.library.Walk;
import nuthatch.pattern.Pattern;
import nuthatch.stratego.adapter.STermCursor;
import nuthatch.stratego.adapter.SWalker;
import nuthatch.stratego.pattern.StrategoBuildContext;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class SActionFactory {
	public static final ActionFactory<IStrategoTerm, Integer, STermCursor, SWalker> factory = FactoryFactory.getActionFactory();


	public ActionFactory<IStrategoTerm, Integer, STermCursor, SWalker> getInstance() {
		return factory;
	}


	// The code below is generated by doing "Generate delegate methods" on 'factory', then putting 'static' in front of them.

	/**
	 * @see nuthatch.library.ActionFactory#afterChild(nuthatch.library.Action)
	 */
	public static Action<SWalker> afterChild(Action<SWalker> action) {
		return factory.afterChild(action);
	}


	/**
	 * @see nuthatch.library.ActionFactory#atLeaf(nuthatch.library.Action)
	 */
	public static Action<SWalker> atLeaf(Action<SWalker> action) {
		return factory.atLeaf(action);
	}


	/**
	 * @see nuthatch.library.ActionFactory#atRoot(nuthatch.library.Action)
	 */
	public static Action<SWalker> atRoot(Action<SWalker> action) {
		return factory.atRoot(action);
	}


	/**
	 * @see nuthatch.library.ActionFactory#beforeChild(nuthatch.library.Action)
	 */
	public static Action<SWalker> beforeChild(Action<SWalker> action) {
		return factory.beforeChild(action);
	}


	/**
	 * @see nuthatch.library.ActionFactory#combine(nuthatch.walk.Action[])
	 */
	@SafeVarargs
	public static final Action<SWalker> combine(Action<SWalker>... actions) {
		return factory.combine(actions);
	}


	/**
	 * @see nuthatch.library.ActionFactory#combineBuilder()
	 */
	public static ActionBuilder<SWalker> combineBuilder() {
		return factory.combineBuilder();
	}


	/**
	 * @see nuthatch.library.ActionFactory#down(nuthatch.library.Action)
	 */
	public static Action<SWalker> down(Action<SWalker> action) {
		return factory.down(action);
	}


	/**
	 * @see nuthatch.library.ActionFactory#downup(nuthatch.library.Action,
	 *      nuthatch.library.Action)
	 */
	public static Action<SWalker> downup(Action<SWalker> downAction, Action<SWalker> upAction) {
		return factory.downup(downAction, upAction);
	}


	/**
	 * @see nuthatch.library.ActionFactory#match(nuthatch.pattern.Pattern,
	 *      nuthatch.library.MatchAction)
	 */
	public static Action<SWalker> match(Pattern<IStrategoTerm, Integer> pat, MatchAction<IStrategoTerm, Integer, STermCursor, SWalker> action) {
		return factory.match(pat, action);
	}


	public static MatchBuilder<IStrategoTerm, Integer, STermCursor, SWalker> matchBuilder() {
		return factory.matchBuilder(new StrategoBuildContext());
	}


	public static Action<SWalker> nop() {
		return factory.nop();
	}


	/**
	 * @see nuthatch.library.impl.actions.ActionFactory#seq(nuthatch.walk.Action[])
	 */
	@SafeVarargs
	public static final Action<SWalker> seq(Action<SWalker>... actions) {
		return factory.seq(actions);
	}


	/**
	 * @see nuthatch.library.ActionFactory#sequenceBuilder()
	 */
	public static ActionBuilder<SWalker> sequenceBuilder() {
		return factory.sequenceBuilder();
	}


	/**
	 * @see nuthatch.library.ActionFactory#up(nuthatch.library.Action)
	 */
	public static Action<SWalker> up(Action<SWalker> action) {
		return factory.up(action);
	}


	/**
	 * @see nuthatch.library.ActionFactory#walk(nuthatch.library.Action)
	 */
	public static Walk<SWalker> walk(Action<SWalker> action) {
		return factory.walk(action);
	}

}
