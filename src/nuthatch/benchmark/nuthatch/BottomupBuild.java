package nuthatch.benchmark.nuthatch;

import static nuthatch.stratego.actions.SActionFactory.up;
import static nuthatch.stratego.actions.SActionFactory.walk;
import nuthatch.stratego.actions.SAction;
import nuthatch.stratego.adapter.STermCursor;
import nuthatch.stratego.adapter.SWalker;
import nuthatch.stratego.adapter.StrategoAdapter;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class BottomupBuild extends NuthatchBenchmark {
	private STermCursor replacement;

	public BottomupBuild(IStrategoTerm term) {
		super("BottomupBuild", term);
		replacement = StrategoAdapter.termToTree(StrategoAdapter
				.getTermFactory().makeInt(42));
	}

	@Override
	protected boolean check() {
		return getResult().subtreeEquals(replacement);
	}

	@Override
	protected SWalker getWalk(STermCursor c) {
		return new SWalker(c, walk(up(new SAction() {
			@Override
			public int step(SWalker w) {
				w.replace(replacement);
				return PROCEED;
			}
		})));
	}
}
