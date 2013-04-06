package nuthatch.demo;

import nuthatch.library.AbstractVisitAction;
import nuthatch.library.Action;
import nuthatch.stratego.actions.SActionFactory;
import nuthatch.stratego.adapter.StrategoAdapter;
import nuthatch.stratego.adapter.TermWalk;

import org.spoofax.interpreter.terms.IStrategoTerm;

/**
 * Demo: A term printer.
 * 
 * @author anya
 */
public class TermPrinter {

	/**
	 * Convert a Stratego term to a string.
	 * 
	 * Should yield the same result as term.toString()
	 * 
	 * @param term
	 *            A term
	 * @return its string representation
	 */
	public static String termToString(IStrategoTerm term) {
		Action<TermWalk> visitor = new AbstractVisitAction<TermWalk>() {
			@Override
			public int beforeChild(TermWalk e, int i) {
				if(i != 1) {
					e.appendToS(",");
				}
				return PROCEED;
			}


			@Override
			public int down(TermWalk e) {
				if(e.getType() == IStrategoTerm.LIST) {
					e.appendToS("[");
				}
				else if(e.getType() == IStrategoTerm.TUPLE) {
					e.appendToS("(");
				}
				else if(e.getType() == IStrategoTerm.STRING) {
					e.appendToS(e.getData().toString());
				}
				else if(e.getType() == IStrategoTerm.APPL) {
					e.appendToS(e.getName());
					if(e.getNumChildren() > 0) {
						e.appendToS("(");
					}
				}
				else {
					e.appendToS(e.getData().toString());
				}
				return PROCEED;
			}


			@Override
			public int up(TermWalk e) {
				if(e.getType() == IStrategoTerm.LIST) {
					e.appendToS("]");
				}
				else if(e.getType() == IStrategoTerm.TUPLE) {
					e.appendToS(")");
				}
				else if(e.getType() == IStrategoTerm.APPL && e.getNumChildren() > 0) {
					e.appendToS(")");
				}
				return PROCEED;
			}
		};
		TermWalk e = new TermWalk(StrategoAdapter.termToTree(term), SActionFactory.walk(visitor));
		long t0 = System.currentTimeMillis();
		e.start();
		System.err.println("Engine finished in " + (System.currentTimeMillis() - t0) + "ms");
		String s1 = e.getS();
		t0 = System.currentTimeMillis();
		String s2 = term.toString();
		System.err.println("toString() finished in " + (System.currentTimeMillis() - t0) + "ms");
		assert s1.equals(s2);
		return s1;
	}
}
