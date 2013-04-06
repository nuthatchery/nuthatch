package nuthatch.library;

import nuthatch.walker.Walker;

public class JoinPoints {
	public static <W extends Walker<?, ?>> boolean afterChild(W walker) {
		return !(walker.isAtLeaf() || walker.from(Action.PARENT));
	}


	public static <W extends Walker<?, ?>> boolean beforeChild(W walker) {
		return !(walker.isAtLeaf() || walker.from(Action.LAST));
	}


	public static <W extends Walker<?, ?>> boolean down(W walker) {
		return walker.from(Action.PARENT);
	}


	public static <W extends Walker<?, ?>> boolean leaf(W walker) {
		return walker.isAtLeaf();
	}


	public static <W extends Walker<?, ?>> boolean root(W walker) {
		return walker.isAtRoot();
	}


	public static <W extends Walker<?, ?>> boolean up(W walker) {
		return walker.isAtLeaf() || walker.from(Action.LAST);
	}
}
