package nuthatch.demo;

import nuthatch.engine.Engine;
import nuthatch.engine.impl.StrategicEngine;
import nuthatch.library.strategies.Visitor;
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
	 * @param term A term
	 * @return its string representation
	 */
	public static String termToString(IStrategoTerm term) {
		final StringBuilder builder = new StringBuilder();
		Visitor<TermEngine> visitor = new Visitor<TermEngine>() {
			@Override
			public void onEntry(TermEngine e) {
				if(e.getType() == IStrategoTerm.LIST) {
					builder.append("[");
				}
				else if(e.getType() == IStrategoTerm.TUPLE) {
					builder.append("(");
				}
				else if(e.getType() == IStrategoTerm.STRING) {
					builder.append(e.getData());
				}
				else if(e.getType() == IStrategoTerm.APPL) {
					builder.append(e.getName());
					if(e.getNumChildren() > 0)
						builder.append("(");
				}
				else {
					builder.append(e.getData());
				}
			}
			@Override
			public void onExit(TermEngine e) {
				if(e.getType() == IStrategoTerm.LIST) {
					builder.append("]");
				}
				else if(e.getType() == IStrategoTerm.TUPLE) {
					builder.append(")");
				}
				else if(e.getType() == IStrategoTerm.APPL && e.getNumChildren() > 0) {
					builder.append(")");
				}
			}
			@Override
			public void beforeChild(TermEngine e, int i) {
				if(i != 1) {
					builder.append(",");
				}
			}
		};
		Engine<IStrategoTerm, Integer> e = new TermEngine(StrategoAdapter.termToTree(term), visitor);
		long t0 = System.currentTimeMillis();
		e.engage();
		System.err.println("Engine finished in " + (System.currentTimeMillis()-t0) + "ms");
		String s1 = builder.toString();
		t0 = System.currentTimeMillis();
		String s2 = term.toString();
		System.err.println("toString() finished in " + (System.currentTimeMillis()-t0) + "ms");
		assert s1.equals(s2);
		return s1;
	}
}
