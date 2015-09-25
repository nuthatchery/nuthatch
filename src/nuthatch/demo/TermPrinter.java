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
		final StringBuilder s = new StringBuilder();

		Action<SWalker> visitor = new BaseVisitAction<SWalker>() {
			@Override
			public int beforeChild(SWalker e, int i) {
				if(i != 1) {
					s.append(",");
				}
				return PROCEED;
			}


			@Override
			public int down(SWalker e) {
				if(e.getType() == IStrategoTerm.LIST) {
					s.append("[");
				}
				else if(e.getType() == IStrategoTerm.TUPLE) {
					s.append("(");
				}
				else if(e.getType() == IStrategoTerm.STRING) {
					s.append(e.getData().toString());
				}
				else if(e.getType() == IStrategoTerm.APPL) {
					s.append(e.getName());
					if(e.getArity() > 0) {
						s.append("(");
					}
				}
				else {
					s.append(e.getData().toString());
				}
				return PROCEED;
			}


			@Override
			public int up(SWalker e) {
				if(e.getType() == IStrategoTerm.LIST) {
					s.append("]");
				}
				else if(e.getType() == IStrategoTerm.TUPLE) {
					s.append(")");
				}
				else if(e.getType() == IStrategoTerm.APPL && e.getArity() > 0) {
					s.append(")");
				}
				return PROCEED;
			}
		};
		SWalker e = new SWalker(StrategoAdapter.termToTree(term), SActionFactory.walk(visitor));
		long t0 = System.currentTimeMillis();
		e.start();
		System.err.println("Engine finished in " + (System.currentTimeMillis() - t0) + "ms");
		String s1 = s.toString();
		t0 = System.currentTimeMillis();
		String s2 = term.toString();
		System.err.println("toString() finished in " + (System.currentTimeMillis() - t0) + "ms");
		assert s1.equals(s2);
		return s1;
	}
}
