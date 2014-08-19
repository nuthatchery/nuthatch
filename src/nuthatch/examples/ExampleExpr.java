package nuthatch.examples;

import static nuthatch.examples.exprlang.ExprPatterns.*;
import nuthatch.examples.exprlang.Expr;

public class ExampleExpr {
	static final String x = "x";
	static final String y = "y";
	/**
	 * 5 + ((7 + 3) * 3)
	 */
	public static final Expr expr1 = Add(Int(5), Mul(Add(Int(7), Int(3)), Int(4)));
	public static final Expr expr2 = Let(Var(x), Int(4), Add(Var(x), Var(x)));
	public static final Expr expr3 = Let(Var(x), Int(3), Let(Var(y), Add(Var(x), Mul(Add(Int(7), Int(3)), Int(4))), Add(Var(x), Var(y))));
	public static final Expr expr4 = Let(Var(y), Int(0), Let(Var(x), Int(3), Let(Var(y), Int(4), Add(Var(x), Var(y)))));
	public static final Expr expr5 = Let(Var(y), Int(0), Var(y));
	public static final Expr expr6 = Let(Var(x), Int(1), Let(Var(x), Var(x), Var(x)));

	public static final Expr[] allExprs = new Expr[] { expr1, expr2, expr3, expr4, expr5, expr6 };
}
