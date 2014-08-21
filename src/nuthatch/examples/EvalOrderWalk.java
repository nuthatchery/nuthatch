package nuthatch.examples;

import static nuthatch.examples.xmpllang.full.XmplPatterns.*;
import nuthatch.examples.xmpllang.Stat;
import nuthatch.examples.xmpllang.Type;
import nuthatch.examples.xmpllang.XmplNode;
import nuthatch.examples.xmpllang.full.XmplActionFactory;
import nuthatch.examples.xmpllang.full.XmplCursor;
import nuthatch.examples.xmpllang.full.XmplWalker;
import nuthatch.library.Action;
import nuthatch.library.ActionBuilder;
import nuthatch.library.BaseAction;
import nuthatch.library.MatchBuilder;
import nuthatch.library.Walk;

public class EvalOrderWalk implements Walk<XmplWalker> {
	final Action<XmplWalker> userAction;



	final Action<XmplWalker> action;
	public EvalOrderWalk(Action<XmplWalker> action) {
		this.userAction = action;

		XmplActionFactory af = XmplActionFactory.getInstance();
		MatchBuilder<XmplNode, Type, XmplCursor, XmplWalker> mb = af.matchBuilder();

		mb.add(Let(_, _, _), af.combine(
				af.down(af.go(2)), // skip first child on way down
				af.from(2, userAction), // perform action after visiting expression
				af.from(3, userAction))); // also perform action after visiting body

		mb.add(Declare(_, _, _), af.combine(
				af.down(af.go(2)),        // skip first child on way down
				af.from(2, userAction),   // perform action after visiting expression
				af.from(3, userAction))); // also perform action after visiting body

		mb.add(If(_, _, _), af.combine(
				af.from(1, userAction), // perform action after visiting condition
				af.from(2, af.go(PARENT)))); // skip else-branch if we took then-branch

		mb.add(While(_, _), af.combine(
				af.from(1, userAction), // perform action after visiting condition
				af.from(2, userAction))); // also perform action after visiting body

		mb.add(Assign(_, _), af.combine(//
				af.down(af.go(2)), // skip first child on way down
				af.from(2, userAction))); // perform action after visiting expression

		mb.add(Seq(___), af.nop()); // don't visit sequences explicitly

		mb.add(isExpr(), af.up(userAction));// any other expression, perform action on the way up
		mb.add(isStat(), af.up(userAction));// any other statement, perform action on the way up

		this.action = mb.first(); // do the first case that matches
	}


	@Override
	public void init(XmplWalker walker) {
		action.init(walker);
	}


	@Override
	public int step(XmplWalker walker) {
		int step = action.step(walker);
		if(step == PROCEED) {
			return NEXT;
		}
		else {
			return step;
		}
	}


	public static void main(String[] args) {
		XmplActionFactory af = XmplActionFactory.getInstance();


		final int[] i = new int[]{0};
		Action<XmplWalker> printAction = new BaseAction<XmplWalker>(){
			@Override
			public int step(XmplWalker walker) {
				System.out.printf("  %3d: %s\n", i[0]++, Printer.toTerm(walker.getData()));
				return PROCEED;
			}
		};

		ActionBuilder<XmplWalker> as = af.sequenceBuilder();

		as.add(af.match(and(Var(var("x")), //
				ancestor(and(Let(Var(var("x")), var("e"), _), from(3)))), //
				af.replace(var("e")))); // replace by binding from matching Let
		as.add(af.match(and(Var(var("x")), //
				ancestor(and(Declare(Var(var("x")), var("e"), _), from(3)))), //
				af.replace(var("e")))); // replace by binding from matching Let
		as.add(af.down(af.match(Let(_, _, _), af.action(af.go(2)))));
		// Drop all Lets on the way up
		as.add(af.up(af.match(Let(_, _, var("e")), af.replace(var("e")))));
		as.add(af.up(af.match(Declare(_, _, var("s")), af.replace(var("s")))));
		Action<XmplWalker> inline = as.done();

		for(Stat e : ExampleStat.allStats) {
			i[0] = 0;
			System.out.println(Printer.toTerm(e) + ":");
			XmplWalker walker = new XmplWalker(e, new EvalOrderWalk(af.seq(printAction, inline)));
			// run it
			walker.start();
			System.out.println(" => " + Printer.toTerm(walker.getData()));
			System.out.println();
			// print the contents of S
		}
	}

}

