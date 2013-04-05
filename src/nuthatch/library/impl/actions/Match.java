package nuthatch.library.impl.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Action;
import nuthatch.walk.MatchAction;
import nuthatch.walk.Walker;

final class Match<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> implements Action<W> {

	private Map<Pattern<Value, Type>, MatchAction<Value, Type, C, W>> patterns;
	private boolean matchOnlyOne;


	public Match(Map<Pattern<Value, Type>, MatchAction<Value, Type, C, W>> patterns) {
		this(patterns, false);
	}


	public Match(Map<Pattern<Value, Type>, MatchAction<Value, Type, C, W>> patterns, boolean matchOnlyOne) {
		super();
		this.patterns = new HashMap<>(patterns);
		this.matchOnlyOne = matchOnlyOne;
	}


	public Match(Pattern<Value, Type> pattern, MatchAction<Value, Type, C, W> ifMatched) {
		this(pattern, ifMatched, false);
	}


	public Match(Pattern<Value, Type> pattern, MatchAction<Value, Type, C, W> ifMatched, boolean matchOnlyOne) {
		super();
		this.patterns = new HashMap<>();
		this.patterns.put(pattern, ifMatched);
		this.matchOnlyOne = matchOnlyOne;
	}


	@Override
	public void init(W walker) {
		for(MatchAction<Value, Type, C, W> a : patterns.values()) {
			a.init(walker);
		}
	}


	@Override
	public int step(W walk) {
		for(Entry<Pattern<Value, Type>, MatchAction<Value, Type, C, W>> entry : patterns.entrySet()) {
			Environment<C> env = EnvironmentFactory.env();
			if(walk.match(entry.getKey(), env)) {
				int r = entry.getValue().step(walk, env);
				if(r != PROCEED) {
					return r;
				}
				if(matchOnlyOne) {
					break;
				}
			}
		}
		return PROCEED;
	}
}
