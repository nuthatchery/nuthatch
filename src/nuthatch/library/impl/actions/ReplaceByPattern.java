package nuthatch.library.impl.actions;

import nuthatch.library.MatchAction;
import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

class ReplaceByPattern<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> implements MatchAction<Value, Type, C, W> {

	private Pattern<Value, Type> pattern;


	public ReplaceByPattern(Pattern<Value, Type> pattern) {
		this.pattern = pattern;
	}


	@Override
	public void init(W walker) {
	}


	@Override
	public int step(W walker, Environment<?, C> env) {
		walker.replace(pattern, env);
		return PROCEED;
	}

}
