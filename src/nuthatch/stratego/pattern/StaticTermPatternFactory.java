package nuthatch.stratego.pattern;

import nuthatch.pattern.Pattern;
import nuthatch.pattern.PatternFactory;
import nuthatch.pattern.StaticPatternFactory;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class StaticTermPatternFactory extends StaticPatternFactory {
	public static final TermPatternFactory PF = TermPatternFactory.instance;

	public static final Pattern<IStrategoTerm, Integer> _ = TermPatternFactory.instance.any();


	private StaticTermPatternFactory() {
	}


	@SafeVarargs
	public static final Pattern<IStrategoTerm, Integer> appl(String name, Pattern<IStrategoTerm, Integer>... children) {
		return TermPatternFactory.instance.appl(name, children);
	}


	public static Pattern<IStrategoTerm, Integer> integer() {
		return TermPatternFactory.instance.integer();
	}


	public static Pattern<IStrategoTerm, Integer> integer(int i) {
		return TermPatternFactory.instance.integer(i);
	}


	@SafeVarargs
	public static final Pattern<IStrategoTerm, Integer> list(Pattern<IStrategoTerm, Integer>... children) {
		var("foo");
		return TermPatternFactory.instance.list(children);
	}


	/**
	 * @see PatternFactory#nodeData(java.lang.Object)
	 */
	public static Pattern<IStrategoTerm, Integer> nodeData(IStrategoTerm data) {
		return TermPatternFactory.instance.nodeData(data);
	}


	public static Pattern<?, ?> nodeName(String name) {
		return TermPatternFactory.instance.nodeName(name);
	}


	/**
	 * @see PatternFactory#nodeType(java.lang.Object)
	 */
	public static Pattern<IStrategoTerm, Integer> nodeType(Integer type) {
		return TermPatternFactory.instance.nodeType(type);
	}


	public static Pattern<IStrategoTerm, Integer> string() {
		return TermPatternFactory.instance.string();
	}


	public static Pattern<IStrategoTerm, Integer> string(String string) {
		return TermPatternFactory.instance.string(string);
	}


	@SafeVarargs
	public static final Pattern<IStrategoTerm, Integer> tuple(Pattern<IStrategoTerm, Integer>... children) {
		return TermPatternFactory.instance.tuple(children);
	}


	/**
	 * @see PatternFactory#var(java.lang.String)
	 */
	public static Pattern<IStrategoTerm, Integer> var(String name) {
		return TermPatternFactory.instance.var(name);
	}

}
