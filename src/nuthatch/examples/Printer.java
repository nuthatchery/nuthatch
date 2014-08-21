package nuthatch.examples;

import nuthatch.examples.xmpllang.Expr;
import nuthatch.examples.xmpllang.XmplNode;
import nuthatch.examples.xmpllang.full.XmplActionFactory;
import nuthatch.examples.xmpllang.full.XmplWalker;
import nuthatch.library.Action;
import nuthatch.library.BaseAction;

public class Printer {
	public static Action<XmplWalker> printAction = new BaseAction<XmplWalker>(){
		@Override
		public int step(XmplWalker walker) {
			System.out.println(Printer.toTerm(walker.getData()));
			return PROCEED;
		}
	};


	public static void main(String[] args) {
		for(Expr e : new Expr[] { ExampleExpr.expr1, ExampleExpr.expr2, ExampleExpr.expr3, ExampleExpr.expr4 }) {
			System.out.println(toTerm(e));
		}
	}

	public static String toTerm(XmplNode expr) {
		// Walk which outputs the tree in a term-like representation. The result are accumulated in the
		// stringbuffer 's'.
		XmplActionFactory af = XmplActionFactory.getInstance();

		final StringBuilder t = new StringBuilder();

		Action<XmplWalker> appendData = new BaseAction<XmplWalker>() {
			@Override
			public int step(XmplWalker walker) {
				t.append(walker.getData());
				return NEXT;
			}
		};
		Action<XmplWalker> appendName = new BaseAction<XmplWalker>() {
			@Override
			public int step(XmplWalker walker) {
				t.append(walker.getName() + "(");
				return NEXT;
			}
		};
		Action<XmplWalker> appendEnd = new BaseAction<XmplWalker>() {
			@Override
			public int step(XmplWalker walker) {
				t.append(")");
				return NEXT;
			}
		};
		Action<XmplWalker> appendComma = new BaseAction<XmplWalker>() {
			@Override
			public int step(XmplWalker walker) {
				t.append(", ");
				return NEXT;
			}
		};

		Action<XmplWalker> toTerm = af.seq(af.atLeaf(appendData), af.down(appendName), af.afterChild((af.beforeChild(appendComma))), af.up(appendEnd));

		XmplWalker toTermWalker = new XmplWalker(expr, af.walk(toTerm));
		toTermWalker.start();
		return t.toString();
	}

}
