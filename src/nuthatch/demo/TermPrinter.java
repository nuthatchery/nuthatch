package nuthatch.demo;

import nuthatch.library.walks.DefaultVisitor;
import nuthatch.library.walks.Visitor;
import nuthatch.stratego.adapter.StrategoAdapter;
import nuthatch.stratego.adapter.TermEngine;

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
		Visitor<TermEngine> visitor = new DefaultVisitor<TermEngine>() {
			@Override
			public void beforeChild(TermEngine e, int i) {
				if(i != 1) {
					e.appendToS(",");
				}
			}


			@Override
			public void onEntry(TermEngine e) {
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
			}


			@Override
			public void onExit(TermEngine e) {
				if(e.getType() == IStrategoTerm.LIST) {
					e.appendToS("]");
				}
				else if(e.getType() == IStrategoTerm.TUPLE) {
					e.appendToS(")");
				}
				else if(e.getType() == IStrategoTerm.APPL && e.getNumChildren() > 0) {
					e.appendToS(")");
				}
			}
		};
		TermEngine e = new TermEngine(StrategoAdapter.termToTree(term), visitor);
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
