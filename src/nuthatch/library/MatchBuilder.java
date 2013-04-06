package nuthatch.library;

import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

public interface MatchBuilder<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> {
	void add(Pattern<Value, Type> pattern, MatchAction<Value, Type, C, W> action);


	Action<W> done();
}
