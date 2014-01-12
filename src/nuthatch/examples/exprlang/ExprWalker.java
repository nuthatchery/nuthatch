package nuthatch.examples.exprlang;

import nuthatch.library.Walk;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.walker.impl.AbstractWalker;

public class ExprWalker extends AbstractWalker<Expr, Type, ExprWalker> {
	Environment<ExprCursor> env;


	public ExprWalker(TreeCursor<Expr, Type> cursor, Walk<ExprWalker> step) {
		super(cursor, step);
	}


	public Environment<ExprCursor> getEnv() {
		if(env == null) {
			env = EnvironmentFactory.env();
		}
		return env;
	}


	@Override
	public TreeCursor<Expr, Type> go(int i) throws BranchNotFoundError {
		env = null;
		return super.go(i);
	}


	public boolean match(Pattern<Expr, Type> pat) {
		return super.match(pat, getEnv());
	}


	public void replace(Expr e) {
		super.replace(new ExprCursor(e));
	}


	@Override
	protected ExprWalker subWalk(TreeCursor<Expr, Type> cursor, Walk<ExprWalker> step) {
		return new ExprWalker(cursor, step);
	}

}
