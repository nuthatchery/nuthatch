package nuthatch.stratego.actions;

import nuthatch.library.MatchAction;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.stratego.adapter.TermWalk;

import org.spoofax.interpreter.terms.IStrategoTerm;

public abstract class SMatchAction implements MatchAction<IStrategoTerm, Integer, TermCursor, TermWalk> {

	@Override
	public void init(TermWalk walker) {
	}
}
