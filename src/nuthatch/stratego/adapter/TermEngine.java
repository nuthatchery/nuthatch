package nuthatch.stratego.adapter;

import nuthatch.engine.impl.EnvEngine;
import nuthatch.strategy.Strategy;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class TermEngine extends EnvEngine<IStrategoTerm, Integer, TermEngine> {
	public TermEngine(IStrategoTerm term, Strategy<TermEngine> strat) {
		super(StrategoAdapter.termToTree(term), strat);
	}


	public TermEngine(TermCursor cursor, Strategy<TermEngine> strat) {
		super(cursor, strat);
	}


	@Override
	public TermCursor copy() {
		return (TermCursor) super.copy();
	}


	@Override
	public TermCursor copyAndReplaceSubtree(
			TreeCursor<IStrategoTerm, Integer> replacement) {
		return (TermCursor) super.copyAndReplaceSubtree(replacement);
	}

	@Override
	public TermCursor copySubtree() {
		return (TermCursor) super.copySubtree();
	}


	@Override
	public TermCursor getBranch(int i)
			throws BranchNotFoundError {
		return (TermCursor) super.getBranch(i);
	}

}
