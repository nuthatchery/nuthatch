package nuthatch.demo;

import nuthatch.library.Action;
import nuthatch.library.BaseVisitAction;
import nuthatch.stratego.actions.SActionFactory;
import nuthatch.stratego.adapter.SWalker;
import nuthatch.stratego.adapter.StrategoAdapter;

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
		Action<SWalker> visitor = new BaseVisitAction<SWalker>() {
			@Override
			public int beforeChild(SWalker e, int i) {
				if(i != 1) {
					e.appendToS(",");
				}
				return PROCEED;
			}


			@Override
			public int down(SWalker e) {
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
			public int up(SWalker e) {
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
		SWalker e = new SWalker(StrategoAdapter.termToTree(term), SActionFactory.walk(visitor));
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
