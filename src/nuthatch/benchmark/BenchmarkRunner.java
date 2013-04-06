package nuthatch.benchmark;

import java.io.IOException;

import nuthatch.benchmark.java.JavaCommute;
import nuthatch.benchmark.java.JavaTraverse;
import nuthatch.benchmark.nuthatch.BottomupBuild;
import nuthatch.benchmark.nuthatch.Commute;
import nuthatch.benchmark.nuthatch.Traverse;
import nuthatch.benchmark.stratego.StrategyBenchmark;
import nuthatch.benchmark.util.StrategoRunner;
import nuthatch.stratego.adapter.STermCursor;
import nuthatch.stratego.adapter.StrategoAdapter;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.terms.ParseError;

public class BenchmarkRunner {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseError
	 */
	public static void main(String[] args) throws ParseError, IOException {

		// IStrategoTerm term =
		// StrategoAdapter.loadTermFromStream(BenchmarkRunner.class.getResourceAsStream("trees/java.tree"));
		IStrategoTerm term = StrategoAdapter
				.loadTermFromFile("nuthatch/benchmark/trees/java.aterm");
		// IStrategoTerm term =
		// StrategoAdapter.loadTermFromFile("nuthatch/benchmark/trees/Simple.java.aterm");

		StrategoRunner runner = new StrategoRunner(StrategoAdapter
				.getParseTableManager().getFactory());

		System.out.println("Commute");
		System.out.println("-------------------");
		Commute commute = new Commute(term);
		commute.run();
		STermCursor commuteResult = commute.getResult(); // so we can check that
		// it's equal to the
		// Stratego version
		JavaCommute jcommute = new JavaCommute(term, commuteResult.getData());
		jcommute.run();
		// System.out.println("Input:    " + term);
		// System.out.println("Java:     " + jcommute.getResult());
		// System.out.println("Nuthatch: " + commuteResult.getData());
		new StrategyBenchmark("Commute (Topdown)",
				"topdown(try(\\Invoke(m,[x,y]) -> Invoke(m,[y,x])\\))",
				commuteResult.getTerm(), term, runner).run();
		// new StrategyBenchmark("Commute (Bottomup)",
		// "bottomup(try(\\Invoke(m,[x,y]) -> Invoke(m,[y,x])\\))",
		// commuteResult.getTerm(), term, runner).run();

		System.out.println();
		System.out.println("Bottomup Build 42");
		System.out.println("-------------------");
		new BottomupBuild(term).run();
		new StrategyBenchmark("BottomupBuild", "bottomup(!42)", "?42", term,
				runner).run();

		System.out.println();
		System.out.println("Identity Traversals");
		System.out.println("-------------------");
		new Traverse(term).run();
		new JavaTraverse(term).run();
		new StrategyBenchmark("TopDown", "topdown(id)", term, term, runner)
		.run();
		new StrategyBenchmark("BottomUp", "bottomup(id)", term, term, runner)
		.run();
		new StrategyBenchmark("DownUp", "downup(id)", term, term, runner).run();
		System.out.println();

	}

}
