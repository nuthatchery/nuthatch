package nuthatch.benchmark.nuthatch;

import static nuthatch.stratego.actions.SActionFactory.match;
import static nuthatch.stratego.actions.SActionFactory.up;
import static nuthatch.stratego.actions.SActionFactory.walk;
import static nuthatch.stratego.pattern.SPatternFactory.appl;
import static nuthatch.stratego.pattern.SPatternFactory.list;
import static nuthatch.stratego.pattern.SPatternFactory.var;
import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.stratego.actions.SMatchAction;
import nuthatch.stratego.adapter.STermCursor;
import nuthatch.stratego.adapter.SWalker;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class Commute extends NuthatchBenchmark {
	private Pattern<IStrategoTerm, Integer> pattern;
	private Pattern<IStrategoTerm, Integer> replace;

	public Commute(IStrategoTerm term) {
		super("Commute", term);
		pattern = appl("Invoke", var("m"), list(var("x"), var("y")));
		replace = appl("Invoke", var("m"), list(var("y"), var("x")));
	}

	@Override
	protected boolean check() {
		return true;
	}

	@Override
	protected SWalker getWalk(STermCursor c) {
		return new SWalker(c, walk(up(match(pattern, new SMatchAction() {
			@Override
			public int step(SWalker w, Environment<STermCursor> env) {
				STermCursor cursor = w.copySubtree();
				// System.out.println(cursor.treeToString());
				cursor.go(LAST).go(1);
				cursor = (STermCursor) cursor.copyAndReplaceSubtree(env
						.get("y"));
				cursor.go(PARENT).go(2);
				cursor = (STermCursor) cursor.copyAndReplaceSubtree(env
						.get("x"));
				cursor.go(PARENT).go(PARENT);
				// System.out.println(cursor.treeToString());
				w.replace(cursor);
				return PROCEED;
			}
		}))));
	}
}
