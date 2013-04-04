package nuthatch.benchmark.nuthatch;

import nuthatch.library.walks.Default;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.stratego.adapter.TermWalk;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class Traverse extends NuthatchBenchmark {

	public Traverse(IStrategoTerm term) {
		super("DefaultWalk", term);
	}

	@Override
	protected TermWalk walk(TermCursor c) {
		return new TermWalk(c, new Default<TermWalk>());
	}

	@Override
	protected boolean check() {
		return true;
	}
}
