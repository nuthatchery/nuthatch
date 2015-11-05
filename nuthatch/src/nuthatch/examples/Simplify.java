package nuthatch.examples;

import static nuthatch.examples.xmpllang.expronly.ExprPatterns.Add;
import static nuthatch.examples.xmpllang.expronly.ExprPatterns.Int;
import static nuthatch.examples.xmpllang.expronly.ExprPatterns.var;
import static nuthatch.library.JoinPoints.up;

import nuthatch.examples.xmpllang.Expr;
import nuthatch.examples.xmpllang.Type;
import nuthatch.examples.xmpllang.expronly.ExprCursor;
import nuthatch.examples.xmpllang.expronly.ExprWalker;
import nuthatch.library.Action;
import nuthatch.library.ActionFactory;
import nuthatch.library.BaseWalk;
import nuthatch.pattern.Pattern;

public class Simplify extends BaseWalk<ExprWalker> {
	public static void main(String[] args) {
		Expr e = Add(Int(5), Add(Add(Int(7), Int(3)), Int(0)));
		//e = Seq(Int(1), Seq(Int(1), Seq(Int(1), Int(0))));
		System.out.println("Input:  " + e);

		ExprWalker walker = new ExprWalker(new ExprCursor(e), new Simplify());
		walker.start();
		System.out.println("Result: " + walker.treeToString());

		ActionFactory<Expr, Type, ExprCursor, ExprWalker> af = ExprWalker.getActionFactory();

		Action<ExprWalker> action = af.up(af.match(Add(var("x"), Int(0)), af.replace(var("x"))));

		walker = new ExprWalker(new ExprCursor(e), af.walk(action));
		walker.start();

		System.out.println("Result: " + walker.treeToString());
	}


	@Override
	public int step(ExprWalker w) {
		if(up(w)) {
			Pattern<Expr, Type> x = var("x");
			if(w.match(Add(x, Int(0)))) {
				w.replace(x);
			}
		}
		return NEXT;
	}
}
