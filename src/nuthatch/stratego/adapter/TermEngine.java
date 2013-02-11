package nuthatch.stratego.adapter;

import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.walk.Step;
import nuthatch.walker.impl.EnvWalker;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class TermEngine extends EnvWalker<IStrategoTerm, Integer, TermEngine> {
	public TermEngine(IStrategoTerm term, Step<TermEngine> strat) {
		super(StrategoAdapter.termToTree(term), strat);
	}


	public TermEngine(TermCursor cursor, Step<TermEngine> strat) {
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
