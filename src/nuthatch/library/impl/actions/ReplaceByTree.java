package nuthatch.library.impl.actions;

import nuthatch.library.Action;
import nuthatch.library.MatchAction;
import nuthatch.pattern.Environment;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

class ReplaceByTree<Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type>> implements MatchAction<Value, Type, C, W>, Action<W> {

	private C cursor;


	public ReplaceByTree(C cursor) {
		this.cursor = cursor;
	}

	@Override
	public void init(W walker) {
	}

	@Override
	public int step(W walker) {
		walker.replace(cursor);
		return Action.PROCEED;
	}


	@Override
	public int step(W walker, Environment<C> env) {
		walker.replace(cursor);
		return Action.PROCEED;
	}


	@Override
	public String toString() {
		return "replace(" + cursor.treeToString() + ")";
	}
}
