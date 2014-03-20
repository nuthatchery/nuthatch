package nuthatch.stratego.adapter;

import nuthatch.library.Walk;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.walker.impl.AbstractWalker;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class SWalker extends AbstractWalker<IStrategoTerm, Integer, SWalker> {
	public SWalker(IStrategoTerm term, Walk<SWalker> step) {
		super(StrategoAdapter.termToTree(term), step);
	}


	public SWalker(STermCursor cursor, Walk<SWalker> step) {
		super(cursor, step);
	}


	@Override
	public STermCursor copy() {
		return (STermCursor) super.copy();
	}


	@Override
	public STermCursor copyAndReplaceSubtree(TreeCursor<IStrategoTerm, Integer> replacement) {
		return (STermCursor) super.copyAndReplaceSubtree(replacement);
	}


	@Override
	public STermCursor copySubtree() {
		return (STermCursor) super.copySubtree();
	}


	@Override
	public STermCursor getBranchCursor(int i) throws BranchNotFoundError {
		return (STermCursor) super.getBranchCursor(i);
	}


	@Override
	protected SWalker subWalk(TreeCursor<IStrategoTerm, Integer> cursor, Walk<SWalker> step) {
		return new SWalker((STermCursor) cursor, step);
	}

}
