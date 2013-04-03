package nuthatch.benchmark;

import java.io.IOException;
import java.io.InputStream;

import nuthatch.benchmark.nuthatch.BottomupBuild;
import nuthatch.benchmark.nuthatch.Commute;
import nuthatch.benchmark.nuthatch.Traverse;
import nuthatch.benchmark.stratego.StrategyBenchmark;
import nuthatch.benchmark.util.StrategoRunner;
import nuthatch.stratego.adapter.StrategoAdapter;
import nuthatch.stratego.adapter.TermCursor;

import org.spoofax.interpreter.ConcreteInterpreter;
import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.core.InterpreterErrorExit;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.core.InterpreterExit;
import org.spoofax.interpreter.core.UndefinedStrategyException;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.jsglr.client.InvalidParseTableException;
import org.spoofax.jsglr.client.ParseException;
import org.spoofax.jsglr.client.ParseTable;
import org.spoofax.jsglr.shared.BadTokenException;
import org.spoofax.jsglr.shared.SGLRException;
import org.spoofax.jsglr.shared.TokenExpectedException;
import org.spoofax.terms.ParseError;
import org.strategoxt.HybridInterpreter;
import org.strategoxt.lang.Context;
import org.strategoxt.stratego_lib.new_0_0;
import org.strategoxt.strj.Main;

public class BenchmarkRunner {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseError
	 */
	public static void main(String[] args) throws ParseError, IOException {

		// IStrategoTerm term =
		// StrategoAdapter.loadTermFromStream(BenchmarkRunner.class.getResourceAsStream("trees/java.tree"));
		IStrategoTerm term = StrategoAdapter.loadTermFromFile("nuthatch/benchmark/trees/java.aterm");
		//IStrategoTerm term = StrategoAdapter.loadTermFromFile("nuthatch/benchmark/trees/Simple.java.aterm");


		StrategoRunner runner = new StrategoRunner(StrategoAdapter.getParseTableManager().getFactory());
		
		
		System.out.println("Commute");
		System.out.println("-------------------");
		Commute commute = new Commute(term);
		commute.run();
		TermCursor commuteResult = commute.getResult(); // so we can check that it's equal to the Stratego version
		new StrategyBenchmark("Commute (Topdown)", "topdown(try(\\Invoke(m,[x,y]) -> Invoke(m,[y,x])\\))", commuteResult.getTerm(), term, runner).run();
		new StrategyBenchmark("Commute (Bottomup)", "bottomup(try(\\Invoke(m,[x,y]) -> Invoke(m,[y,x])\\))", commuteResult.getTerm(), term, runner).run();
		
		System.out.println("Bottomup Build 42");
		System.out.println("-------------------");
		new BottomupBuild(term).run();
		new StrategyBenchmark("BottomupBuild", "bottomup(!42)", "?42", term, runner).run();

		System.out.println("Identity Traversals");
		System.out.println("-------------------");
		new Traverse(term).run();
		new StrategyBenchmark("TopDown", "topdown(id)", term, term, runner).run();
		new StrategyBenchmark("BottomUp", "bottomup(id)", term, term, runner).run();
		new StrategyBenchmark("DownUp", "downup(id)", term, term, runner).run();
		System.out.println();


	}

}
