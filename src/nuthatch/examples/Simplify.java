package nuthatch.examples;

import static nuthatch.examples.exprlang.ExprPatterns.*;
import static nuthatch.library.JoinPoints.up;
import nuthatch.examples.exprlang.Expr;
import nuthatch.examples.exprlang.ExprCursor;
import nuthatch.examples.exprlang.ExprWalker;
import nuthatch.library.BaseWalk;

public class Simplify extends BaseWalk<ExprWalker> {
	@Override
	public int step(ExprWalker w) {
		if(up(w)) {
			if(w.match(Add(var("x"), Int(0)))) {
				w.replace(w.getEnv().get("x"));
			}
			else if(w.match(Mul(var("x"), Int(0)))) {
				w.replace(Int(0));
			}
		}
		return NEXT;
	}


	public static void main(String[] args) {
		Expr e = Add(Int(5), Mul(Add(Int(7), Int(3)), Int(0)));
		e = Mul(Int(1), Mul(Int(1), Mul(Int(1), Int(0))));
		System.out.println("Input:  " + e);

		ExprWalker walker = new ExprWalker(new ExprCursor(e), new Simplify());
		walker.start();
		System.out.println("Result: " + walker.treeToString());
	}
}
