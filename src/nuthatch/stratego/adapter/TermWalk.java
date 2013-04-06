package nuthatch.stratego.adapter;

import nuthatch.library.Walk;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.walker.impl.EnvWalker;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class TermWalk extends EnvWalker<IStrategoTerm, Integer, TermWalk> {
	public TermWalk(IStrategoTerm term, Walk<TermWalk> step) {
		super(StrategoAdapter.termToTree(term), step);
	}


	public TermWalk(TermCursor cursor, Walk<TermWalk> step) {
		super(cursor, step);
	}


	@Override
	public TermCursor copy() {
		return (TermCursor) super.copy();
	}


	@Override
	public TermCursor copyAndReplaceSubtree(TreeCursor<IStrategoTerm, Integer> replacement) {
		return (TermCursor) super.copyAndReplaceSubtree(replacement);
	}


	@Override
	public TermCursor copySubtree() {
		return (TermCursor) super.copySubtree();
	}


	@Override
	public TermCursor getBranch(int i) throws BranchNotFoundError {
		return (TermCursor) super.getBranch(i);
	}


	@Override
	protected TermWalk subWalk(TreeCursor<IStrategoTerm, Integer> cursor, Walk<TermWalk> step) {
		return new TermWalk((TermCursor) cursor, step);
	}

}
