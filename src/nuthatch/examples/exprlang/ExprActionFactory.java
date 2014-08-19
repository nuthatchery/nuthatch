package nuthatch.examples.exprlang;

import nuthatch.library.impl.actions.StandardActionFactory;
import nuthatch.tree.TreeCursor;

public class ExprActionFactory extends StandardActionFactory<Expr, Type, TreeCursor<Expr, Type>, ExprWalker> {
	public static ExprActionFactory actionFactory = new ExprActionFactory();


	@SuppressWarnings("unchecked")
	public static ExprActionFactory getInstance() {
		return actionFactory;
	}
}
