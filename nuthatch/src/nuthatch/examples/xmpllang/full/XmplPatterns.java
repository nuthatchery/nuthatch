package nuthatch.examples.xmpllang.full;

import nuthatch.examples.xmpllang.Add;
import nuthatch.examples.xmpllang.Assign;
import nuthatch.examples.xmpllang.Declare;
import nuthatch.examples.xmpllang.Expr;
import nuthatch.examples.xmpllang.If;
import nuthatch.examples.xmpllang.Int;
import nuthatch.examples.xmpllang.Let;
import nuthatch.examples.xmpllang.Mul;
import nuthatch.examples.xmpllang.Nop;
import nuthatch.examples.xmpllang.Seq;
import nuthatch.examples.xmpllang.Stat;
import nuthatch.examples.xmpllang.Sum;
import nuthatch.examples.xmpllang.Type;
import nuthatch.examples.xmpllang.Var;
import nuthatch.examples.xmpllang.While;
import nuthatch.examples.xmpllang.XmplNode;
import nuthatch.pattern.Pattern;
import nuthatch.pattern.PatternFactory;

public class XmplPatterns {
	private static final PatternFactory<XmplNode, Type> PF = PatternFactory.getInstance();

	public static final Pattern<XmplNode, Type> zero = PF.nodeWithChildren("0");

	public static final Pattern<XmplNode, Type> _ = PF.any();
	public static final Ellipsis ___ = new Ellipsis();


	public static final Expr Add(Expr e1, Expr e2) {
		return new Add(e1, e2);
	}

	public static final Pattern<XmplNode, Type> Add(Pattern<XmplNode, Type> e1, Pattern<XmplNode, Type> e2) {
		return PF.nodeWithChildren("Add", e1, e2);
	}


	public static final Pattern<XmplNode, Type> Add(Pattern<XmplNode, Type> e1, XmplNode e2) {
		return PF.nodeWithChildren("Add", e1, PF.tree(new XmplCursor(e2)));
	}


	public static final Pattern<XmplNode, Type> Add(XmplNode e1, Pattern<XmplNode, Type> e2) {
		return PF.nodeWithChildren("Add", PF.tree(new XmplCursor(e1)), e2);
	}


	public static final Pattern<XmplNode, Type> ancestor(Pattern<XmplNode, Type> ancestor) {
		return PF.ancestor(ancestor);
	}


	public static final Pattern<XmplNode, Type> and(Pattern<XmplNode, Type> a, Pattern<XmplNode, Type> b) {
		return PF.and(a, b);
	}


	public static final Pattern<XmplNode, Type> Assign(Pattern<XmplNode, Type> v, Pattern<XmplNode, Type> e) {
		return PF.nodeWithChildren("Assign", v, e);
	}


	public static final Stat Assign(Var v, Expr e) {
		return new Assign(v, e);
	}


	public static final Pattern<XmplNode, Type> Declare(Pattern<XmplNode, Type> x, Pattern<XmplNode, Type> e, Pattern<XmplNode, Type> s) {
		return PF.nodeWithChildren("Declare", x, e, s);
	}


	public static final Stat Declare(Var x, Expr e, Stat s) {
		return new Declare(x, e, s);
	}


	public static final Pattern<XmplNode, Type> descendant(Pattern<XmplNode, Type> descendant) {
		return PF.descendant(descendant);
	}


	public static final Pattern<XmplNode, Type> from(int b) {
		return PF.from(b);
	}


	public static final Stat If(Expr e, Stat s1, Stat s2) {
		return new If(e, s1, s2);
	}


	public static final Pattern<XmplNode, Type> If(Pattern<XmplNode, Type> c, Pattern<XmplNode, Type> s1, Pattern<XmplNode, Type> s2) {
		return PF.nodeWithChildren("If", c, s1, s2);
	}


	@SuppressWarnings("unused")
	public static final Pattern<XmplNode, Type> Int(Ellipsis _) {
		return PF.nodeType(Type.INT);
	}
	public static final Expr Int(int i) {
		return new Int(i);
	}


	public static final Pattern<XmplNode, Type> Int(Pattern<XmplNode, Type> pat) {
		return and(PF.nodeType(Type.INT), pat);
	}

	public static final Pattern<XmplNode, Type> isExpr() {
		return PF.nodeType(Type.EXPR, Type.isSubtype);
	}

	public static final Pattern<XmplNode, Type> isStat() {
		return PF.nodeType(Type.STAT);
	}


	public static final Pattern<XmplNode, Type> Let(Pattern<XmplNode, Type> x, Pattern<XmplNode, Type> e1, Pattern<XmplNode, Type> e2) {
		return PF.nodeWithChildren("Let", x, e1, e2);
	}


	public static final Expr Let(Var x, Expr e1, Expr e2) {
		return new Let(x, e1, e2);
	}

	public static final Expr Mul(Expr e1, Expr e2) {
		return new Mul(e1, e2);
	}

	public static final Pattern<XmplNode, Type> Mul(Pattern<XmplNode, Type> e1, Pattern<XmplNode, Type> e2) {
		return PF.nodeWithChildren("Mul", e1, e2);
	}


	public static final Pattern<XmplNode, Type> Mul(Pattern<XmplNode, Type> e1, XmplNode e2) {
		return PF.nodeWithChildren("Mul", e1, PF.tree(new XmplCursor(e2)));
	}


	public static final Pattern<XmplNode, Type> Mul(XmplNode e1, Pattern<XmplNode, Type> e2) {
		return PF.nodeWithChildren("Mul", PF.tree(new XmplCursor(e1)), e2);
	}


	public static final Stat Nop() {
		return new Nop();
	}


	public static final Pattern<XmplNode, Type> not(Pattern<XmplNode, Type> a) {
		return PF.not(a);
	}


	public static final Pattern<XmplNode, Type> or(Pattern<XmplNode, Type> a, Pattern<XmplNode, Type> b) {
		return PF.or(a, b);
	}


	public static final Pattern<XmplNode, Type> parent(Pattern<XmplNode, Type> parent) {
		return PF.parent(parent);
	}


	@SuppressWarnings("unused")
	public static final Pattern<XmplNode, Type> Seq(Ellipsis _) {
		return PF.nodeName("Seq");
	}


	@SafeVarargs
	public static final Pattern<XmplNode, Type> Seq(Pattern<XmplNode, Type>... ss) {
		return PF.nodeWithChildren("Seq", ss);
	}


	public static final Stat Seq(Stat... ss) {
		return new Seq(ss);
	}


	public static final Pattern<XmplNode, Type> Sum() {
		return PF.nodeName("Sum");
	}


	public static final Expr Sum(Expr... es) {
		return new Sum(es);
	}


	@SafeVarargs
	public static final Pattern<XmplNode, Type> Sum(Pattern<XmplNode, Type>... es) {
		return PF.nodeWithChildren("Sum", es);
	}


	public static Pattern<XmplNode, Type> tree(XmplNode node) {
		return PF.tree(new XmplCursor(node));
	}


	public static final Pattern<XmplNode, Type> type(Type type) {
		return PF.nodeType(type);
	}


	public static final Pattern<XmplNode, Type> var(String name) {
		return PF.var(name);
	}


	public static final Pattern<XmplNode, Type> var(String name, Pattern<XmplNode, Type> expr) {
		return PF.var(name, expr);
	}


	@SuppressWarnings("unused")
	public static final Pattern<XmplNode, Type> Var(Ellipsis _) {
		return PF.nodeType(Type.VAR);
	}


	public static final Pattern<XmplNode, Type> Var(Pattern<XmplNode, Type> pat) {
		return and(PF.nodeType(Type.VAR), pat);
	}


	public static final Var Var(String s) {
		return new Var(s);
	}


	public static final Stat While(Expr e, Stat ss) {
		return new While(e, ss);
	}


	public static final Pattern<XmplNode, Type> While(Pattern<XmplNode, Type> c, Pattern<XmplNode, Type> s) {
		return PF.nodeWithChildren("While", c, s);
	}


	static final class Ellipsis {

	}
}
