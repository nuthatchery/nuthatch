package nuthatch.stratego.adapter;

import nuthatch.engine.impl.RegisteredEngine;
import nuthatch.strategy.Strategy;
import nuthatch.tree.TreeCursor;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class TermEngine extends RegisteredEngine<IStrategoTerm, Integer, TermEngine> {
	public TermEngine(TreeCursor<IStrategoTerm, Integer> cursor, Strategy<TermEngine> strat) {
		super(cursor, strat);
	}


	public TermEngine(IStrategoTerm term, Strategy<TermEngine> strat) {
		super(StrategoAdapter.termToTree(term), strat);
	}

}
