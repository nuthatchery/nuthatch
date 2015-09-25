package nuthatch.stratego.actions;

import nuthatch.library.MatchAction;
import nuthatch.stratego.adapter.STermCursor;
import nuthatch.stratego.adapter.SWalker;

import org.spoofax.interpreter.terms.IStrategoTerm;

public abstract class SMatchAction implements MatchAction<IStrategoTerm, Integer, STermCursor, SWalker> {

	@Override
	public void init(SWalker walker) {
	}
}
