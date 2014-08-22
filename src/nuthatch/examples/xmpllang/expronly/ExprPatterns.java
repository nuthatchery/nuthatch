package nuthatch.examples.xmpllang.expronly;

import nuthatch.examples.xmpllang.Add;
import nuthatch.examples.xmpllang.Expr;
import nuthatch.examples.xmpllang.Int;
import nuthatch.examples.xmpllang.Let;
import nuthatch.examples.xmpllang.Mul;
import nuthatch.examples.xmpllang.Type;
import nuthatch.examples.xmpllang.Var;
import nuthatch.pattern.Pattern;
import nuthatch.pattern.PatternFactory;

public class ExprPatterns {
	private static final PatternFactory<Expr, Type> PF = PatternFactory.getInstance();

	public static final Pattern<Expr, Type> zero = PF.nodeWithChildren("0");

	public static final Pattern<Expr, Type> _ = PF.any();


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


	public static final Pattern<Expr, Type> ancestor(Pattern<Expr, Type> ancestor) {
		return PF.ancestor(ancestor);
	}


	public static final Pattern<Expr, Type> and(Pattern<Expr, Type> a, Pattern<Expr, Type> b) {
		return PF.and(a, b);
	}


	public static final Pattern<Expr, Type> descendant(Pattern<Expr, Type> descendant) {
		return PF.descendant(descendant);
	}


	public static final Pattern<Expr, Type> from(int b) {
		return PF.from(b);
	}


	public static final Expr Int(int i) {
		return new Int(i);
	}


	public static final Pattern<Expr, Type> Let(Pattern<Expr, Type> x, Pattern<Expr, Type> e1, Pattern<Expr, Type> e2) {
		return PF.nodeWithChildren("Let", x, e1, e2);
	}


	public static final Expr Let(Var x, Expr e1, Expr e2) {
		return new Let(x, e1, e2);
	}


	public static final Expr Mul(Expr e1, Expr e2) {
		return new Mul(e1, e2);
	}


	public static final Pattern<Expr, Type> Mul(Expr e1, Pattern<Expr, Type> e2) {
		return PF.nodeWithChildren("Seq", PF.tree(new ExprCursor(e1)), e2);
	}


	public static final Pattern<Expr, Type> Mul(Pattern<Expr, Type> e1, Expr e2) {
		return PF.nodeWithChildren("Seq", e1, PF.tree(new ExprCursor(e2)));
	}


	public static final Pattern<Expr, Type> Mul(Pattern<Expr, Type> e1, Pattern<Expr, Type> e2) {
		return PF.nodeWithChildren("Seq", e1, e2);
	}


	public static final Pattern<Expr, Type> not(Pattern<Expr, Type> a) {
		return PF.not(a);
	}


	public static final Pattern<Expr, Type> or(Pattern<Expr, Type> a, Pattern<Expr, Type> b) {
		return PF.or(a, b);
	}


	public static final Pattern<Expr, Type> parent(Pattern<Expr, Type> parent) {
		return PF.parent(parent);
	}


	public static final Pattern<Expr, Type> type(Type type) {
		return PF.nodeType(type);
	}


	public static final Pattern<Expr, Type> var(String name) {
		return PF.var(name);
	}


	public static final Pattern<Expr, Type> var(String name, Pattern<Expr, Type> expr) {
		return PF.var(name, expr);
	}


	public static final Pattern<Expr, Type> Var() {
		return PF.nodeType(Type.VAR);
	}


	public static final Pattern<Expr, Type> Var(Pattern<Expr, Type> name) {
		return and(Var(), name);
	}


	public static final Var Var(String s) {
		return new Var(s);
	}

}
