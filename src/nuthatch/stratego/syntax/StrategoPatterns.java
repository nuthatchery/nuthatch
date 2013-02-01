package nuthatch.stratego.syntax;

import nuthatch.pattern.Pattern;
import nuthatch.stratego.pattern.TermPatternFactory;

import org.spoofax.interpreter.terms.IStrategoTerm;


public class StrategoPatterns {
	private static final TermPatternFactory pf = TermPatternFactory.getInstance();

	public static Pattern<IStrategoTerm, Integer> Conc(Pattern<IStrategoTerm, Integer> arg0, Pattern<IStrategoTerm, Integer> arg1) {
		return pf.appl("Conc", arg0, arg1);
	}

	public static Pattern<IStrategoTerm, Integer> Cons(Pattern<IStrategoTerm, Integer> arg0, Pattern<IStrategoTerm, Integer> arg1) {
		return pf.appl("Cons", arg0, arg1);
	}

	public static Pattern<IStrategoTerm, Integer> Constructors(Pattern<IStrategoTerm, Integer> arg0) {
		return pf.appl("Constructors", arg0);
	}

	public static Pattern<IStrategoTerm, Integer> ConstType(Pattern<IStrategoTerm, Integer> arg0) {
		return pf.appl("ConstType", arg0);
	}

	public static Pattern<IStrategoTerm, Integer> ExtOpDecl(Pattern<IStrategoTerm, Integer> arg0, Pattern<IStrategoTerm, Integer> arg1) {
		return pf.appl("ExtOpDecl", arg0, arg1);
	}

	public static Pattern<IStrategoTerm, Integer> ExtOpDeclInj(Pattern<IStrategoTerm, Integer> arg0) {
		return pf.appl("ExtOpDeclInj", arg0);
	}

	public static Pattern<IStrategoTerm, Integer> ExtOpDeclQ(Pattern<IStrategoTerm, Integer> arg0, Pattern<IStrategoTerm, Integer> arg1) {
		return pf.appl("ExtOpDeclQ", arg0, arg1);
	}

	public static Pattern<IStrategoTerm, Integer> FunType(Pattern<IStrategoTerm, Integer> arg0, Pattern<IStrategoTerm, Integer> arg1) {
		return pf.appl("FunType", arg0, arg1);
	}

	public static Pattern<IStrategoTerm, Integer> Module(Pattern<IStrategoTerm, Integer> arg0, Pattern<IStrategoTerm, Integer> arg1) {
		return pf.appl("Module", arg0, arg1);
	}

	public static Pattern<IStrategoTerm, Integer> Nil() {
		return pf.appl("Nil");
	}

	public static Pattern<IStrategoTerm, Integer> None() {
		return pf.appl("None");
	}

	public static Pattern<IStrategoTerm, Integer> OpDecl(Pattern<IStrategoTerm, Integer> arg0, Pattern<IStrategoTerm, Integer> arg1) {
		return pf.appl("OpDecl", arg0, arg1);
	}

	public static Pattern<IStrategoTerm, Integer> OpDeclInj(Pattern<IStrategoTerm, Integer> arg0) {
		return pf.appl("OpDeclInj", arg0);
	}

	public static Pattern<IStrategoTerm, Integer> OpDeclQ(Pattern<IStrategoTerm, Integer> arg0, Pattern<IStrategoTerm, Integer> arg1) {
		return pf.appl("OpDeclQ", arg0, arg1);
	}

	public static Pattern<IStrategoTerm, Integer> Signature(Pattern<IStrategoTerm, Integer> arg0) {
		return pf.appl("Signature", arg0);
	}

	public static Pattern<IStrategoTerm, Integer> Some(Pattern<IStrategoTerm, Integer> arg0) {
		return pf.appl("Some", arg0);
	}

	public static Pattern<IStrategoTerm, Integer> Sort(Pattern<IStrategoTerm, Integer> arg0, Pattern<IStrategoTerm, Integer> arg1) {
		return pf.appl("Sort", arg0, arg1);
	}

	public static Pattern<IStrategoTerm, Integer> SortNoArgs(Pattern<IStrategoTerm, Integer> arg0) {
		return pf.appl("SortNoArgs", arg0);
	}

	public static Pattern<IStrategoTerm, Integer> Sorts(Pattern<IStrategoTerm, Integer> arg0) {
		return pf.appl("Sorts", arg0);
	}

	public static Pattern<IStrategoTerm, Integer> SortVar(Pattern<IStrategoTerm, Integer> arg0) {
		return pf.appl("SortVar", arg0);
	}

}
