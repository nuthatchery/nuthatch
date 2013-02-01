package nuthatch.stratego.pattern;

import org.spoofax.interpreter.terms.IStrategoTerm;

import nuthatch.pattern.Pattern;
import nuthatch.pattern.PatternFactory;

public class TermPatternFactory extends PatternFactory<IStrategoTerm, Integer> {
	static final TermPatternFactory instance = new TermPatternFactory();
	
	private TermPatternFactory() {
	}

	@SafeVarargs
	public final Pattern<IStrategoTerm, Integer> appl(String name, Pattern<IStrategoTerm, Integer>... children) {
		return nodeWithChildren(name, IStrategoTerm.APPL, null, children);
	}

	public Pattern<IStrategoTerm, Integer> integer() {
		return node(null, IStrategoTerm.INT, null);
	}

	public Pattern<IStrategoTerm, Integer> integer(int i) {
		return node(String.valueOf(i), IStrategoTerm.INT, null);
	}
	

	@SafeVarargs
	public final Pattern<IStrategoTerm, Integer> list(Pattern<IStrategoTerm, Integer>... children) {
		return nodeWithChildren(null, IStrategoTerm.LIST, null, children);
	}

	public Pattern<IStrategoTerm, Integer> string() {
		return node(null, IStrategoTerm.STRING, null);
	}
	
	public Pattern<IStrategoTerm, Integer> string(String string) {
		return node(string, IStrategoTerm.STRING, null);
	}

	@SafeVarargs
	public final Pattern<IStrategoTerm, Integer> tuple(Pattern<IStrategoTerm, Integer>... children) {
		return nodeWithChildren(null, IStrategoTerm.TUPLE, null, children);
	}
	public static TermPatternFactory getInstance() {
		return instance;
	}
	
}
