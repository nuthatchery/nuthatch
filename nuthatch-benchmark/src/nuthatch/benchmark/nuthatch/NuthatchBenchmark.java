package nuthatch.benchmark.nuthatch;

import nuthatch.benchmark.Benchmark;
import nuthatch.stratego.adapter.STermCursor;
import nuthatch.stratego.adapter.SWalker;
import nuthatch.stratego.adapter.StrategoAdapter;

import org.spoofax.interpreter.terms.IStrategoTerm;

public abstract class NuthatchBenchmark extends Benchmark {

	private STermCursor tree;
	private SWalker walk;

	public NuthatchBenchmark(String name, int n, IStrategoTerm term) {
		super(name, n, "(Nuthatch)");
		this.tree = StrategoAdapter.termToTree(term);
	}

	public NuthatchBenchmark(String name, IStrategoTerm term) {
		super(name, "(Nuthatch)");
		this.tree = StrategoAdapter.termToTree(term);
	}

	public STermCursor getResult() {
		return walk.copy();
	}

	@Override
	protected void doIt() {
		walk = getWalk(tree);
		walk.start();
	}

	protected abstract SWalker getWalk(STermCursor tree);

}
