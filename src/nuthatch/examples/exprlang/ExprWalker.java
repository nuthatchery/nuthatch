package nuthatch.examples.exprlang;

import nuthatch.library.ActionFactory;
import nuthatch.library.FactoryFactory;
import nuthatch.library.Walk;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.walker.impl.AbstractWalker;

public class ExprWalker extends AbstractWalker<Expr, Type, ExprWalker> {
	public ExprWalker(TreeCursor<Expr, Type> cursor, Walk<ExprWalker> step) {
		super(cursor, step);
	}


	@Override
	public TreeCursor<Expr, Type> go(int i) throws BranchNotFoundError {
		return super.go(i);
	}


	public void replace(Expr e) {
		super.replace(new ExprCursor(e));
	}


	@Override
	protected ExprWalker subWalk(TreeCursor<Expr, Type> cursor, Walk<ExprWalker> step) {
		return new ExprWalker(cursor, step);
	}


	public static ActionFactory<Expr, Type, ExprCursor, ExprWalker> getActionFactory() {
		return FactoryFactory.getActionFactory();
	}
}
