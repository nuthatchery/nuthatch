package nuthatch.benchmark.nuthatch;

import nuthatch.benchmark.Benchmark;
import nuthatch.stratego.adapter.StrategoAdapter;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.stratego.adapter.TermWalk;

import org.spoofax.interpreter.terms.IStrategoTerm;

public abstract class NuthatchBenchmark extends Benchmark {

	private TermCursor tree;
	private TermWalk walk;

	public NuthatchBenchmark(String name, IStrategoTerm term) {
		super(name, "(Nuthatch)");
		this.tree = StrategoAdapter.termToTree(term);
	}

	public NuthatchBenchmark(String name, int n, IStrategoTerm term) {
		super(name, n, "(Nuthatch)");
		this.tree = StrategoAdapter.termToTree(term);
	}

	@Override
	protected void doIt() {
		walk = walk(tree);
		walk.start();
	}

	public TermCursor getResult() {
		return walk.copy();
	}

	protected abstract TermWalk walk(TermCursor tree);

}
