package nuthatch.benchmark.stratego;

import nuthatch.benchmark.util.StrategoRunner;

import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class StrategyBenchmark extends StrategoBenchmark {
	private IStrategoAppl strategy;
	private IStrategoAppl stratCheck;
	private IStrategoTerm termCheck;

	public StrategyBenchmark(String name, String strategy, String check,
			IStrategoTerm term, StrategoRunner runner) {
		super(name, term, runner);
		this.strategy = runner.compile(strategy);
		this.stratCheck = runner.compile(check);
	}

	public StrategyBenchmark(String name, String strategy, String check, int n,
			IStrategoTerm term, StrategoRunner runner) {
		super(name, n, term, runner);
		this.strategy = runner.compile(strategy);
		this.stratCheck = runner.compile(check);
	}

	public StrategyBenchmark(String name, String strategy, IStrategoTerm check,
			IStrategoTerm term, StrategoRunner runner) {
		super(name, term, runner);
		this.strategy = runner.compile(strategy);
		this.termCheck = check;
	}

	public StrategyBenchmark(String name, String strategy, IStrategoTerm check,
			int n, IStrategoTerm term, StrategoRunner runner) {
		super(name, n, term, runner);
		this.strategy = runner.compile(strategy);
		this.termCheck = check;
	}

	@Override
	protected void doIt(IStrategoTerm term) {
		invoke(strategy, term);
	}

	@Override
	protected boolean check() {
		if (stratCheck != null)
			return runner.invoke(stratCheck);
		else if (termCheck != null)
			return termCheck.equals(runner.current());
		else
			return true;
	}

}
