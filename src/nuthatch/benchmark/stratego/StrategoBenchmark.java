package nuthatch.benchmark.stratego;

import nuthatch.benchmark.Benchmark;
import nuthatch.benchmark.util.StrategoRunner;

import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;

public abstract class StrategoBenchmark extends Benchmark {
	StrategoRunner runner;
	private IStrategoTerm term;

	public StrategoBenchmark(String name, IStrategoTerm term,
			StrategoRunner runner) {
		super(name, "(Stratego)");
		this.term = term;
		this.runner = runner;
	}

	public StrategoBenchmark(String name, int n, IStrategoTerm term,
			StrategoRunner runner) {
		super(name, n, "(Stratego)");
		this.term = term;
		this.runner = runner;
	}

	protected IStrategoTerm invoke(IStrategoAppl program, IStrategoTerm term) {
		runner.setCurrent(term);
		if (runner.invoke(program)) {
			return runner.current();
		} else {
			return null;
		}
	}

	@Override
	protected void doIt() {
		doIt(term);
	}

	protected abstract void doIt(IStrategoTerm term);

	public IStrategoTerm getResult() {
		return runner.current();
	}

}
