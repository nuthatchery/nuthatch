package nuthatch.examples;

import static nuthatch.examples.exprlang.ExprPatterns.*;
import nuthatch.examples.exprlang.Expr;
import nuthatch.examples.exprlang.ExprActionFactory;
import nuthatch.examples.exprlang.ExprCursor;
import nuthatch.examples.exprlang.ExprWalker;
import nuthatch.examples.exprlang.Type;
import nuthatch.library.Action;
import nuthatch.library.ActionBuilder;
import nuthatch.library.BaseMatchAction;
import nuthatch.pattern.Environment;

public class ExprBindings {
	public static void main(String[] args) {
		// Walk which outputs the tree in a term-like representation. The result are accumulated in the
		// stringbuffer 's'.
		ExprActionFactory af = ExprActionFactory.getInstance();
		BindingsPrinter printBinding = new BindingsPrinter(); // for more fun, try the BindingsReplacer

		// Example 1: A walker that makes many mistakes
		// This will call printBinding whenever a Var(x) is found for which there is a Let(Var(x),_,_) ancestor.
		Action<ExprWalker> naive = af.match(and(Var(var("x")), ancestor(Let(Var(var("x")), var("e"), _))), printBinding);

		// Example 2: A walker that avoids the first child of a Let
		ActionBuilder<ExprWalker> sb1 = af.sequenceBuilder(); // make a sequence of actions
		// Call printBinding whenever a Var(x) is found for which there is a Let(Var(x),_,_) ancestor.
		sb1.add(af.match(and(Var(var("x")), ancestor(Let(Var(var("x")), var("e"), _))), printBinding));
		// if we come down into a Let, go to the second child, skipping the first
		sb1.add(af.down(af.match(Let(_, _, _), af.action(af.go(2)))));
		Action<ExprWalker> lessNaive = sb1.done();

		// Example 3: A walker that avoids the first child of a Let, and also correctly
		// handles variables in the second child
		ActionBuilder<ExprWalker> sb2 = af.sequenceBuilder();
		// Call printBinding whenever a Var(x) is found for which there is a Let(Var(x),_,_) ancestor
		// for which we are somewhere in the third child.
		sb2.add(af.match(and(Var(var("x")), //
				ancestor(and(Let(Var(var("x")), var("e"), _), from(3)))), printBinding));
		sb2.add(af.down(af.match(Let(_, _, _), af.action(af.go(2)))));
		Action<ExprWalker> correct = sb2.done();

		// Example 4: A walker that inlines bound variables
		ActionBuilder<ExprWalker> as = af.sequenceBuilder();
		as.add(af.match(and(Var(var("x")), //
				ancestor(and(Let(Var(var("x")), var("e"), _), from(3)))), //
				af.replace(var("e")))); // replace by binding from matching Let
		as.add(af.down(af.match(Let(_, _, _), af.action(af.go(2)))));
		// Drop all Lets on the way up
		as.add(af.up(af.match(Let(_, _, var("e")), af.replace(var("e")))));
		Action<ExprWalker> inline = as.done();

		Action<ExprWalker> showBindings = lessNaive; // pick any example here to run
//		ExprWalker toTermWalker = new ExprWalker(ExampleExpr.expr6, af.walk(showBindings));
//		System.out.println(ExprToString.exprToString(ExampleExpr.expr6) + ":");
//		toTermWalker.start();

		for(Expr e : ExampleExpr.allExprs) {
			System.out.println(ExprToString.exprToString(e) + ":");
			ExprWalker toTermWalker = new ExprWalker(e, af.walk(showBindings));
			// run it
			toTermWalker.start();
			System.out.println("  => " + ExprToString.exprToString(toTermWalker.getData()));
			// print the contents of S
		}
	}


	private static final class BindingsPrinter extends BaseMatchAction<Expr, Type, ExprCursor, ExprWalker> {
		@Override
		public int step(ExprWalker walker, Environment<ExprCursor> env) {
			System.out.println("  Var(" + env.get("x").treeToString() + ") -> " + ExprToString.exprToString(env.get("e").getData()));
			return PROCEED;
		}
	}


	private static final class BindingsReplacer extends BaseMatchAction<Expr, Type, ExprCursor, ExprWalker> {
		@Override
		public int step(ExprWalker walker, Environment<ExprCursor> env) {
			walker.replace(env.get("e"));
			System.out.println("  Var(" + env.get("x").treeToString() + ") -> " + ExprToString.exprToString(env.get("e").getData()));
			return PROCEED;
		}
	}

}
