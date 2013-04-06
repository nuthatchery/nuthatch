package nuthatch.library.impl.actions;

import java.util.HashMap;
import java.util.Map;

import nuthatch.library.Action;
import nuthatch.library.MatchAction;
import nuthatch.library.MatchBuilder;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

final class MatchActionBuilder<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> implements MatchBuilder<Value, Type, C, W> {

	private Map<Pattern<Value, Type>, MatchAction<Value, Type, C, W>> map = new HashMap<>();


	@Override
	public void add(Pattern<Value, Type> pattern, MatchAction<Value, Type, C, W> action) {
		map.put(pattern, action);
	}


	@Override
	public Action<W> done() {
		return new Match<Value, Type, C, W>(map);
	}


	public Action<W> done(boolean matchOnlyOne) {
		return new Match<Value, Type, C, W>(map, matchOnlyOne);
	}
}
