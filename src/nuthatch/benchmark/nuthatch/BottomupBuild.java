package nuthatch.benchmark.nuthatch;

import nuthatch.library.walks.DefaultVisitor;
import nuthatch.stratego.adapter.StrategoAdapter;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.stratego.adapter.TermWalk;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class BottomupBuild extends NuthatchBenchmark {
	private TermCursor replacement;
	

	public BottomupBuild(IStrategoTerm term) {
		super("BottomupBuild", term);
		replacement = StrategoAdapter.termToTree(StrategoAdapter.getTermFactory().makeInt(42));
	}


	@Override
	protected TermWalk walk(TermCursor c) {
		return new TermWalk(c, new DefaultVisitor<TermWalk>() {
			public void onExit(TermWalk w) {
				w.replace(replacement);
			}
		});
	}


	@Override
	protected boolean check() {
		return getResult().subtreeEquals(replacement);
	}
}
