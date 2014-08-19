package nuthatch.library.impl.actions;

import java.util.HashMap;
import java.util.Map;

import nuthatch.library.Action;
import nuthatch.library.MatchAction;
import nuthatch.library.MatchBuilder;
import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

final class MatchActionBuilder<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> implements MatchBuilder<Value, Type, C, W> {

	private Map<Pattern<Value, Type>, MatchAction<Value, Type, C, W>> map = new HashMap<>();
	protected BuildContext<Value, Type, C> context;


	public MatchActionBuilder(BuildContext<Value, Type, C> context) {
		this.context = context;
	}


	@Override
	public void add(Pattern<Value, Type> pattern, MatchAction<Value, Type, C, W> action) {
		map.put(pattern, action);
	}


	@Override
	public void add(Pattern<Value, Type> pattern, final Pattern<Value, Type> replacement) {
		map.put(pattern, new MatchAction<Value, Type, C, W>() {

			@Override
			public void init(W walker) {
			}


			@Override
			public int step(W walker, Environment<C> env) {
				walker.replace(replacement.build(context, env));
				return PROCEED;
			}
		});
	}


	@Override
	public Action<W> all() {
		return new Match<Value, Type, C, W>(map, false);
	}


	@Override
	public MatchAction<Value, Type, C, W> allEnv() {
		return new Match<Value, Type, C, W>(map, false);
	}


	@Override
	public Action<W> one() {
		return new Match<Value, Type, C, W>(map, true);
	}


	@Override
	public MatchAction<Value, Type, C, W> oneEnv() {
		return new Match<Value, Type, C, W>(map, true);

	}
}
