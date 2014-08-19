package nuthatch.examples;

import nuthatch.examples.exprlang.Expr;
import nuthatch.examples.exprlang.ExprActionFactory;
import nuthatch.examples.exprlang.ExprWalker;
import nuthatch.library.Action;
import nuthatch.library.BaseAction;

public class ExprToString {
	public static void main(String[] args) {
		// Walk which outputs the tree in a term-like representation. The result are accumulated in the
		// stringbuffer 's'.
		ExprActionFactory af = ExprActionFactory.getInstance();

		final StringBuilder t = new StringBuilder();

		Action<ExprWalker> appendData = new BaseAction<ExprWalker>() {
			@Override
			public int step(ExprWalker walker) {
				t.append(walker.getData());
				return NEXT;
			}
		};
		Action<ExprWalker> appendName = new BaseAction<ExprWalker>() {
			@Override
			public int step(ExprWalker walker) {
				t.append(walker.getName() + "(");
				return NEXT;
			}
		};
		Action<ExprWalker> appendEnd = new BaseAction<ExprWalker>() {
			@Override
			public int step(ExprWalker walker) {
				t.append(")");
				return NEXT;
			}
		};
		Action<ExprWalker> appendComma = new BaseAction<ExprWalker>() {
			@Override
			public int step(ExprWalker walker) {
				t.append(", ");
				return NEXT;
			}
		};
		Action<ExprWalker> toTerm = af.seq(af.atLeaf(appendData), af.down(appendName), af.afterChild((af.beforeChild(appendComma))), af.up(appendEnd));

		for(Expr e : new Expr[] { ExampleExpr.expr1, ExampleExpr.expr2, ExampleExpr.expr3, ExampleExpr.expr4 }) {
			ExprWalker toTermWalker = new ExprWalker(e, af.walk(toTerm));
			t.delete(0, t.length());
			// run it
			toTermWalker.start();
			// print the contents of S
			System.out.println(t.toString());
		}

	}
}
