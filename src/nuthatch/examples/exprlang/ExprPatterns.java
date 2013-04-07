package nuthatch.examples.exprlang;

import nuthatch.pattern.Pattern;
import nuthatch.pattern.PatternFactory;

public class ExprPatterns {
	private static final PatternFactory<Expr, Type> PF = PatternFactory.getInstance();

	public static final Pattern<Expr, Type> zero = PF.nodeWithChildren("0");


	public static final Expr Add(Expr e1, Expr e2) {
		return new Add(e1, e2);
	}


	public static final Pattern<Expr, Type> Add(Expr e1, Pattern<Expr, Type> e2) {
		return PF.nodeWithChildren("Add", PF.tree(new ExprCursor(e1)), e2);
	}


	public static final Pattern<Expr, Type> Add(Pattern<Expr, Type> e1, Expr e2) {
		return PF.nodeWithChildren("Add", e1, PF.tree(new ExprCursor(e2)));
	}


	public static final Pattern<Expr, Type> Add(Pattern<Expr, Type> e1, Pattern<Expr, Type> e2) {
		return PF.nodeWithChildren("Add", e1, e2);
	}


	public static final Expr Int(int i) {
		return new Int(i);
	}


	public static final Expr Mul(Expr e1, Expr e2) {
		return new Mul(e1, e2);
	}


	public static final Pattern<Expr, Type> Mul(Expr e1, Pattern<Expr, Type> e2) {
		return PF.nodeWithChildren("Mul", PF.tree(new ExprCursor(e1)), e2);
	}


	public static final Pattern<Expr, Type> Mul(Pattern<Expr, Type> e1, Expr e2) {
		return PF.nodeWithChildren("Mul", e1, PF.tree(new ExprCursor(e2)));
	}


	public static final Pattern<Expr, Type> Mul(Pattern<Expr, Type> e1, Pattern<Expr, Type> e2) {
		return PF.nodeWithChildren("Mul", e1, e2);
	}


	public static final Pattern<Expr, Type> var(String name) {
		return PF.var(name);
	}
}
