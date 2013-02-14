package nuthatch.stratego.adapter;

import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.walk.Step;
import nuthatch.walk.impl.EnvWalk;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class TermWalk extends EnvWalk<IStrategoTerm, Integer, TermWalk> {
	public TermWalk(IStrategoTerm term, Step<TermWalk> step) {
		super(StrategoAdapter.termToTree(term), step);
	}


	public TermWalk(TermCursor cursor, Step<TermWalk> step) {
		super(cursor, step);
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
