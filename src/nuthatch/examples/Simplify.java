package nuthatch.examples;

import static nuthatch.examples.exprlang.ExprPatterns.Add;
import static nuthatch.examples.exprlang.ExprPatterns.Int;
import static nuthatch.examples.exprlang.ExprPatterns.var;
import static nuthatch.library.JoinPoints.up;
import nuthatch.examples.exprlang.Expr;
import nuthatch.examples.exprlang.ExprCursor;
import nuthatch.examples.exprlang.ExprWalker;
import nuthatch.examples.exprlang.Type;
import nuthatch.library.Action;
import nuthatch.library.ActionFactory;
import nuthatch.library.BaseWalk;

public class Simplify extends BaseWalk<ExprWalker> {
	public void alternative() {

	}


	@Override
	public int step(ExprWalker w) {
		if(up(w)) {
			if(w.match(Add(var("x"), Int(0)))) {
				w.replace(var("x"));
			}
			//	else if(w.match(Mul(var("x"), Int(0)))) {
			//		w.replace(Int(0));
			//	}
		}
		return NEXT;
	}


	public static void main(String[] args) {
		Expr e = Add(Int(5), Add(Add(Int(7), Int(3)), Int(0)));
		//e = Mul(Int(1), Mul(Int(1), Mul(Int(1), Int(0))));
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
}
