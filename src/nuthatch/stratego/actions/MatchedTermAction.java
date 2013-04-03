package nuthatch.stratego.actions;

import nuthatch.library.walks.MatchedAction;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.stratego.adapter.TermWalk;

import org.spoofax.interpreter.terms.IStrategoTerm;

public interface MatchedTermAction extends MatchedAction<IStrategoTerm, Integer, TermCursor, TermWalk> {

}
