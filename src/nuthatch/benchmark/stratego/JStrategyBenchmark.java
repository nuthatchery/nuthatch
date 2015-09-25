package nuthatch.benchmark.stratego;

import nuthatch.benchmark.Benchmark;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;
import org.strategoxt.lang.Strategy;

public class JStrategyBenchmark extends Benchmark {
	private Strategy strategy;
	private IStrategoTerm termCheck;
	private IStrategoTerm result;
	private IStrategoTerm term;
	private Context context;

	public JStrategyBenchmark(String name, Strategy strategy, IStrategoTerm check,
			int n, IStrategoTerm term, Context context) {
		super(name, n, "(Stratego/J)");
		this.term = term;
		this.strategy = strategy;
		this.termCheck = check;
		this.context = context;
	}

	public JStrategyBenchmark(String name, Strategy strategy, IStrategoTerm check,
			IStrategoTerm term, Context context) {
		super(name, "(Stratego/J)");
		this.term = term;
		this.strategy = strategy;
		this.termCheck = check;
		this.context = context;
	}


	public IStrategoTerm getResult() {
		return result;
	}
	@Override
	protected boolean check() {
		if (termCheck != null) {
			return termCheck.equals(result);
		} else {
			return true;
		}
	}

	@Override
	protected void doIt() {
		result = strategy.invoke(context, term);
	}

}
