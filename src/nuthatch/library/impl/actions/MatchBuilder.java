package nuthatch.library.impl.actions;

import java.util.HashMap;
import java.util.Map;

import nuthatch.library.Action;
import nuthatch.library.MatchAction;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

public class MatchBuilder<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> {

	private Map<Pattern<Value, Type>, MatchAction<Value, Type, C, W>> map = new HashMap<>();


	public void add(Pattern<Value, Type> pattern, MatchAction<Value, Type, C, W> action) {
		map.put(pattern, action);
	}


	public Action<W> done() {
		return new Match<Value, Type, C, W>(map);
	}


	public Action<W> done(boolean matchOnlyOne) {
		return new Match<Value, Type, C, W>(map, matchOnlyOne);
	}
}
