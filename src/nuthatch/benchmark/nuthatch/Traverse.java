package nuthatch.benchmark.nuthatch;

import static nuthatch.stratego.actions.SActionFactory.nop;
import static nuthatch.stratego.actions.SActionFactory.walk;
import nuthatch.stratego.adapter.STermCursor;
import nuthatch.stratego.adapter.SWalker;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class Traverse extends NuthatchBenchmark {

	public Traverse(IStrategoTerm term) {
		super("DefaultWalk", term);
	}

	@Override
	protected boolean check() {
		return true;
	}

	@Override
	protected SWalker getWalk(STermCursor c) {
		return new SWalker(c, walk(nop()));
	}
}
