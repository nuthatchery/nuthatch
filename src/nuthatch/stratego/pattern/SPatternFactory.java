package nuthatch.stratego.pattern;

import nuthatch.pattern.Pattern;
import nuthatch.pattern.PatternFactory;
import nuthatch.pattern.StaticPatternFactory;
import nuthatch.stratego.pattern.impl.TermPatternFactory;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class SPatternFactory extends StaticPatternFactory {
	public static final TermPatternFactory PF = TermPatternFactory.getInstance();

	public static final Pattern<IStrategoTerm, Integer> _ = PF.any();


	private SPatternFactory() {
	}


	@SafeVarargs
	public static final Pattern<IStrategoTerm, Integer> appl(String name, Pattern<IStrategoTerm, Integer>... children) {
		return PF.appl(name, children);
	}


	public static Pattern<IStrategoTerm, Integer> integer() {
		return PF.integer();
	}


	public static Pattern<IStrategoTerm, Integer> integer(int i) {
		return PF.integer(i);
	}


	public static Pattern<IStrategoTerm, Integer> isList() {
		return PF.isList();
	}


	@SafeVarargs
	public static final Pattern<IStrategoTerm, Integer> list(Pattern<IStrategoTerm, Integer>... children) {
		var("foo");
		return PF.list(children);
	}


	/**
	 * @see PatternFactory#nodeData(java.lang.Object)
	 */
	public static Pattern<IStrategoTerm, Integer> nodeData(IStrategoTerm data) {
		return PF.nodeData(data);
	}


	public static Pattern<?, ?> nodeName(String name) {
		return PF.nodeName(name);
	}


	/**
	 * @see PatternFactory#nodeType(java.lang.Object)
	 */
	public static Pattern<IStrategoTerm, Integer> nodeType(Integer type) {
		return PF.nodeType(type);
	}


	public static Pattern<IStrategoTerm, Integer> string() {
		return PF.string();
	}


	public static Pattern<IStrategoTerm, Integer> string(String string) {
		return PF.string(string);
	}


	@SafeVarargs
	public static final Pattern<IStrategoTerm, Integer> tuple(Pattern<IStrategoTerm, Integer>... children) {
		return PF.tuple(children);
	}


	/**
	 * @see PatternFactory#var(java.lang.String)
	 */
	public static Pattern<IStrategoTerm, Integer> var(String name) {
		return PF.var(name);
	}

}
