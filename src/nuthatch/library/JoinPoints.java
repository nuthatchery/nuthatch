package nuthatch.library;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

public class JoinPoints {
	public static <W extends Walker<?, ?, W>> boolean afterChild(W walker) {
		return !(walker.isAtLeaf() || walker.from(Action.PARENT));
	}


	public static <W extends Walker<?, ?, W>> boolean beforeChild(W walker) {
		return !(walker.isAtLeaf() || (walker.isReversed() ? walker.from(Action.FIRST) : walker.from(Action.LAST)));
	}


	public static <W extends Walker<?, ?, W>> boolean down(W walker) {
		return walker.from(Action.PARENT);
	}


	public static <W extends Walker<?, ?, W>> boolean leaf(W walker) {
		return walker.isAtLeaf();
	}


	public static <W extends Walker<Type, Value, W>, Type, Value> boolean matches(W walker, Pattern<Type, Value> pat) {
		return walker.match(pat);
	}


	public static <W extends Walker<Type, Value, W>, Type, Value> boolean matches(W walker, Pattern<Type, Value> pat, Environment<? extends TreeCursor<Type, Value>> env) {
		return walker.match(pat, env);
	}


	public static <W extends Walker<?, ?, W>> boolean root(W walker) {
		return walker.isAtRoot();
	}


	public static <W extends Walker<?, ?, W>> boolean up(W walker) {
		return walker.isAtLeaf() || (walker.isReversed() ? walker.from(Action.FIRST) : walker.from(Action.LAST));
	}

}
