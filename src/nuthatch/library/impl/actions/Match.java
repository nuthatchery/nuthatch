package nuthatch.library.impl.actions;

import java.util.ArrayList;
import java.util.List;

import nuthatch.library.Action;
import nuthatch.library.MatchAction;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.util.Pair;
import nuthatch.walker.Walker;

/**
 * Note: this class searches the patterns in order, this could certainly be
 * improved.
 * 
 * Because of this, the behaviour of {@link nuthatch.library.MatchBuilder#one()}
 * and {@link nuthatch.library.MatchBuilder#one()} is the same.
 */
final class Match<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> implements Action<W>, MatchAction<Value, Type, C, W> {

	private List<Pair<Pattern<Value, Type>, MatchAction<Value, Type, C, W>>> patterns;
	private boolean matchOnlyOne; // pick the first match


	public Match(List<Pair<Pattern<Value, Type>, MatchAction<Value, Type, C, W>>> patterns) {
		this(patterns, false);
	}


	public Match(List<Pair<Pattern<Value, Type>, MatchAction<Value, Type, C, W>>> patterns, boolean matchOnlyOne) {
		super();
		this.patterns = new ArrayList<>(patterns);
		this.matchOnlyOne = matchOnlyOne;
	}


	public Match(Pattern<Value, Type> pattern, MatchAction<Value, Type, C, W> ifMatched) {
		this(pattern, ifMatched, false);
	}


	public Match(Pattern<Value, Type> pattern, MatchAction<Value, Type, C, W> ifMatched, boolean matchOnlyOne) {
		super();
		this.patterns = new ArrayList<>();
		this.patterns.add(new Pair<>(pattern, ifMatched));
		this.matchOnlyOne = matchOnlyOne;
	}


	@Override
	public void init(W walker) {
		for(Pair<Pattern<Value, Type>, MatchAction<Value, Type, C, W>> a : patterns) {
			a.getSecond().init(walker);
		}
	}


	@Override
	public int step(W walk) {
		for(Pair<Pattern<Value, Type>, MatchAction<Value, Type, C, W>> entry : patterns) {
			Environment<C> env = EnvironmentFactory.env();
			if(walk.match(entry.getFirst(), env)) {
				int r = entry.getSecond().step(walk, env);
				if(r != Action.PROCEED) {
					return r;
				}
				if(matchOnlyOne) {
					break;
				}
			}
		}
		return Action.PROCEED;
	}


	@Override
	public int step(W walk, Environment<C> env) {
		for(Pair<Pattern<Value, Type>, MatchAction<Value, Type, C, W>> entry : patterns) {
			if(walk.match(entry.getFirst(), env)) {
				int r = entry.getSecond().step(walk, env);
				if(r != Action.PROCEED) {
					return r;
				}
				if(matchOnlyOne) {
					break;
				}
			}
		}
		return Action.PROCEED;
	}
}
