package nuthatch.examples;

import static nuthatch.examples.xmpllang.full.XmplPatterns.*;
import nuthatch.examples.xmpllang.Stat;
import nuthatch.examples.xmpllang.Type;
import nuthatch.examples.xmpllang.XmplNode;
import nuthatch.examples.xmpllang.full.XmplActionFactory;
import nuthatch.examples.xmpllang.full.XmplCursor;
import nuthatch.examples.xmpllang.full.XmplWalker;
import nuthatch.library.Action;
import nuthatch.library.BaseAction;
import nuthatch.library.MatchBuilder;
import nuthatch.library.Walk;

public class EvalOrderWalk {
	public static void main(String[] args) {
		XmplActionFactory af = XmplActionFactory.getInstance();


		final int[] i = new int[]{0};
		Action<XmplWalker> myAction= new BaseAction<XmplWalker>(){
			@Override
			public int step(XmplWalker walker) {
				System.out.printf("  %3d: %s\n", i[0]++, Printer.toTerm(walker.getData()));
				return PROCEED;
			}
		};

		for(Stat e : ExampleStat.allStats) {
			i[0] = 0;
			System.out.println(Printer.toTerm(e) + ":");
			XmplWalker walker = new XmplWalker(e, new OrderedWalk(myAction));
			// run it
			walker.start();
			System.out.println(" => " + Printer.toTerm(walker.getData()));
			System.out.println();
			// print the contents of S
		}
	}


	static class OrderedWalk implements Walk<XmplWalker> {
		final Action<XmplWalker> userAction;
		final Action<XmplWalker> action;


		public OrderedWalk(Action<XmplWalker> action) {
			this.userAction = action;

			XmplActionFactory af = XmplActionFactory.getInstance();
			MatchBuilder<XmplNode, Type, XmplCursor, XmplWalker> mb = af.matchBuilder();

			mb.add(Let(_, _, _), af.combine(
					af.down(af.go(2)), // skip first child on way down
					af.from(1, userAction))); // perform action after visiting expression

			mb.add(Declare(_, _, _), af.combine(
					af.down(af.go(2)), // skip first child on way down
					af.from(1, userAction)));// perform action after visiting expression

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

	}


}
