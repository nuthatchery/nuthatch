package nuthatch.examples;

import static nuthatch.examples.xmpllang.expronly.ExprPatterns.*;

import nuthatch.examples.xmpllang.Expr;
import nuthatch.examples.xmpllang.Type;
import nuthatch.examples.xmpllang.expronly.ExprActionFactory;
import nuthatch.examples.xmpllang.expronly.ExprCursor;
import nuthatch.examples.xmpllang.expronly.ExprWalker;
import nuthatch.library.Action;
import nuthatch.library.ActionBuilder;
import nuthatch.library.BaseMatchAction;
import nuthatch.pattern.Environment;

public class ExprBindings {
	private static final class BindingsPrinter extends BaseMatchAction<Expr, Type, ExprCursor, ExprWalker> {
		@Override
		public int step(ExprWalker walker, Environment<ExprCursor> env) {
			System.out.println("  Var(" + env.get("x").treeToString() + ") -> " + Printer.toTerm(env.get("e").getData()));
			return PROCEED;
		}
	}


	private static final class BindingsReplacer extends BaseMatchAction<Expr, Type, ExprCursor, ExprWalker> {
		@Override
		public int step(ExprWalker walker, Environment<ExprCursor> env) {
			walker.replace(env.get("e"));
			System.out.println("  Var(" + env.get("x").treeToString() + ") -> " + Printer.toTerm(env.get("e").getData()));
			return PROCEED;
		}
	}


	public static void main(String[] args) {
		// Walk which outputs the tree in a term-like representation. The result are accumulated in the
		// stringbuffer 's'.
		ExprActionFactory af = ExprActionFactory.getInstance();
		BindingsPrinter printBinding = new BindingsPrinter(); // for more fun, try the BindingsReplacer


		// ---- Example 1: A walker that makes many mistakes
		// This will call printBinding whenever a Var(x) is found for which there is a Let(Var(x),_,_) ancestor.
		Action<ExprWalker> naive = af.match(and(Var(var("x")), ancestor(Let(Var(var("x")), var("e"), _))), printBinding);
		Expr expr = ExampleExpr.expr6;
		System.out.println("Naive: ");
		System.out.println(expr);
		ExprWalker walker = new ExprWalker(expr, af.walk(naive));
		// run it
		walker.start();
		System.out.println("  => " + Printer.toTerm(walker.getData()));
		System.out.println();

//		System.exit(0);

		// ---- Example 2: A walker that avoids the first child of a Let
		ActionBuilder<ExprWalker> sb1 = af.sequenceBuilder(); // make a sequence of actions

		// Call printBinding whenever a Var(x) is found for which there is a Let(Var(x),_,_) ancestor.
		sb1.add(af.match(and(Var(var("x")), ancestor(Let(Var(var("x")), var("e"), _))), printBinding));
		// if we come down into a Let, go to the second child, skipping the first
		sb1.add(af.down(af.match(Let(_, _, _), af.action(af.go(2)))));
		Action<ExprWalker> lessNaive = sb1.done();
		System.out.println("Less Naive: ");
		System.out.println(expr);
		walker = new ExprWalker(expr, af.walk(lessNaive));
		// run it
		walker.start();
		System.out.println("  => " + Printer.toTerm(walker.getData()));
		System.out.println();
//		System.exit(0);


		// ---- Example 3: A walker that avoids the first child of a Let, and also correctly
		// handles variables in the second child
		ActionBuilder<ExprWalker> sb2 = af.sequenceBuilder();

		// Call printBinding whenever a Var(x) is found for which there is a Let(Var(x),_,_) ancestor
		// for which we are somewhere in the third child.
		sb2.add(af.match(and(Var(var("x")), //
				ancestor(and(Let(Var(var("x")), var("e"), _), from(3)))), printBinding));
		sb2.add(af.down(af.match(Let(_, _, _), af.action(af.go(2)))));
		Action<ExprWalker> correct = sb2.done();
		System.out.println("Correct: ");
		System.out.println(expr);
		walker = new ExprWalker(expr, af.walk(correct));
		// run it
		walker.start();
		System.out.println("  => " + Printer.toTerm(walker.getData()));
		System.out.println();
//		System.exit(0);


		// ---- Example 4: A walker that inlines bound variables
		ActionBuilder<ExprWalker> as = af.sequenceBuilder();

		as.add(af.match(and(Var(var("x")), //
				ancestor(and(Let(Var(var("x")), var("e"), _), from(3)))), //
				af.replace(var("e")))); // replace by binding from matching Let
		as.add(af.down(af.match(Let(_, _, _), af.action(af.go(2)))));
		// Drop all Lets on the way up
		as.add(af.up(af.match(Let(_, _, var("e")), af.replace(var("e")))));
		Action<ExprWalker> inline = as.done();
		System.out.println("Inline: ");
		System.out.println(expr);
		walker = new ExprWalker(expr, af.walk(inline));
		// run it
		walker.start();
		System.out.println("  => " + Printer.toTerm(walker.getData()));
		System.out.println();

		System.exit(0);
		Action<ExprWalker> showBindings = lessNaive; // pick any example here to run
//		XmplWalker toTermWalker = new XmplWalker(ExampleExpr.expr6, af.walk(showBindings));
//		System.out.println(Printer.exprToString(ExampleExpr.expr6) + ":");
//		toTermWalker.start();

		for(Expr e : ExampleExpr.allExprs) {
			System.out.println(Printer.toTerm(e) + ":");
			ExprWalker toTermWalker = new ExprWalker(e, af.walk(showBindings));
			// run it
			toTermWalker.start();
			System.out.println("  => " + Printer.toTerm(toTermWalker.getData()));
			// print the contents of S
		}
	}

}
