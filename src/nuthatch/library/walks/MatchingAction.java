package nuthatch.library.walks;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Walk;

public class MatchingAction<Value, Type, C extends TreeCursor<Value, Type>, W extends Walk<Value, Type>> implements Action<W> {

	private Map<Pattern<Value, Type>, MatchedAction<Value, Type, C, W>> patterns;
	private boolean matchOnlyOne;


	public MatchingAction(Map<Pattern<Value, Type>, MatchedAction<Value, Type, C, W>> patterns) {
		this(patterns, false);
	}


	public MatchingAction(Map<Pattern<Value, Type>, MatchedAction<Value, Type, C, W>> patterns, boolean matchOnlyOne) {
		super();
		this.patterns = new HashMap<>(patterns);
		this.matchOnlyOne = matchOnlyOne;
	}


	public MatchingAction(Pattern<Value, Type> pattern, MatchedAction<Value, Type, C, W> ifMatched) {
		this(pattern, ifMatched, false);
	}


	public MatchingAction(Pattern<Value, Type> pattern, MatchedAction<Value, Type, C, W> ifMatched, boolean matchOnlyOne) {
		super();
		this.patterns = new HashMap<>();
		this.patterns.put(pattern, ifMatched);
		this.matchOnlyOne = matchOnlyOne;
	}


	@Override
	public int action(W walk) {
		for(Entry<Pattern<Value, Type>, MatchedAction<Value, Type, C, W>> entry : patterns.entrySet()) {
			Environment<C> env = EnvironmentFactory.env();
			if(walk.match(entry.getKey(), env)) {
				int r = entry.getValue().action(walk, env);
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
