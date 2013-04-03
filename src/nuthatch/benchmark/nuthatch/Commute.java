package nuthatch.benchmark.nuthatch;

import nuthatch.library.walks.DefaultVisitor;
import static nuthatch.stratego.pattern.StaticTermPatternFactory.*;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.Pattern;
import nuthatch.stratego.adapter.StrategoAdapter;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.stratego.adapter.TermWalk;
import nuthatch.tree.TreeCursor;

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
	protected TermWalk walk(TermCursor c) {
		return new TermWalk(c, new DefaultVisitor<TermWalk>() {
			public void onExit(TermWalk w) {
				Environment<TreeCursor<IStrategoTerm, Integer>> env = EnvironmentFactory.env();

				if(pattern.match(w, env)) {
					TermCursor cursor = w.copySubtree();
					//System.out.println(cursor.treeToString());
					cursor.go(LAST).go(1);
					cursor = (TermCursor) cursor.copyAndReplaceSubtree(env.get("y"));
					cursor.go(PARENT).go(2);
					cursor = (TermCursor) cursor.copyAndReplaceSubtree(env.get("x"));
					cursor.go(PARENT).go(PARENT);
					//System.out.println(cursor.treeToString());
					w.replace(cursor);
				}
			}
		});
	}


	@Override
	protected boolean check() {
		return true;
	}
}
