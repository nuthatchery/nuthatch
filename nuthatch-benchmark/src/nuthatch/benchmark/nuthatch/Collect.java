package nuthatch.benchmark.nuthatch;

import static nuthatch.stratego.actions.SActionFactory.down;
import static nuthatch.stratego.actions.SActionFactory.match;
import static nuthatch.stratego.actions.SActionFactory.walk;
import static nuthatch.stratego.pattern.SPatternFactory.string;

import java.util.ArrayList;
import java.util.List;

import nuthatch.benchmark.Benchmark;
import nuthatch.pattern.Environment;
import nuthatch.stratego.actions.SMatchAction;
import nuthatch.stratego.adapter.STermCursor;
import nuthatch.stratego.adapter.SWalker;
import nuthatch.stratego.adapter.StrategoAdapter;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class Collect extends Benchmark {
	private STermCursor tree;
	private List<IStrategoTerm> result;

	public Collect(IStrategoTerm term) {
		super("Collect", "(Nuthatch)");
		this.tree = StrategoAdapter.termToTree(term);
	}

	public List<IStrategoTerm> getResult() {
		return result;
	}

	@Override
	protected boolean check() {
		return true;
	}

	@Override
	protected void doIt() {
		result = new ArrayList<IStrategoTerm>();
		SWalker walker = new SWalker(tree, walk(down(match(string(), new SMatchAction() {
			@Override
			public int step(SWalker walker, Environment<STermCursor> env) {
				result.add(walker.getData());
				return PROCEED;
			}
		}))));
		walker.start();
	}

}
