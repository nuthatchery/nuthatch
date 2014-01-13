package nuthatch.rascal.pattern;

import nuthatch.pattern.Pattern;
import nuthatch.pattern.StaticPatternFactory;
import nuthatch.rascal.pattern.impl.ValuesPatternFactory;

import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IRational;
import org.eclipse.imp.pdb.facts.IReal;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class PdbPatternFactory extends StaticPatternFactory {
	public static final ValuesPatternFactory PF = ValuesPatternFactory.getInstance();

	public static final Pattern<IValue, Type> _ = (Pattern<IValue, Type>) _();


	private PdbPatternFactory() {
	}


	/**
	 * @param name
	 * @param children
	 * @return
	 * @see
	 *      nuthatch.rascal.pattern.impl.ValuesPatternFactory#cons(java.lang.
	 *      String
	 *      , nuthatch.pattern.Pattern<org.eclipse.imp.pdb.facts.IValue,org.
	 *      eclipse.imp.pdb.facts.type.Type>[])
	 */
	public static final Pattern<IValue, Type> cons(String name, Pattern<IValue, Type>... children) {
		return PF.cons(name, children);
	}


	/**
	 * @return
	 * @see nuthatch.rascal.pattern.impl.ValuesPatternFactory#integer()
	 */
	public static Pattern<IValue, Type> integer() {
		return PF.integer();
	}


	/**
	 * @param i
	 * @return
	 * @see nuthatch.rascal.pattern.impl.ValuesPatternFactory#integer(org.eclipse.imp.pdb.facts.IInteger)
	 */
	public static Pattern<IValue, Type> integer(IInteger i) {
		return PF.integer(i);
	}


	/**
	 * @return
	 * @see nuthatch.rascal.pattern.impl.ValuesPatternFactory#isList()
	 */
	public static final Pattern<IValue, Type> isList() {
		return PF.isList();
	}


	/**
	 * @param listType
	 * @return
	 * @see nuthatch.rascal.pattern.impl.ValuesPatternFactory#isList(org.eclipse.imp.pdb.facts.type.Type)
	 */
	public static final Pattern<IValue, Type> isList(Type listType) {
		return PF.isList(listType);
	}


	/**
	 * @param children
	 * @return
	 * @see
	 *      nuthatch.rascal.pattern.impl.ValuesPatternFactory#list(nuthatch.
	 *      pattern
	 *      .Pattern<org.eclipse.imp.pdb.facts.IValue,org.eclipse.imp.pdb.facts.
	 *      type.Type>[])
	 */
	public static final Pattern<IValue, Type> list(Pattern<IValue, Type>... children) {
		return PF.list(children);
	}


	/**
	 * @param listType
	 * @param children
	 * @return
	 * @see
	 *      nuthatch.rascal.pattern.impl.ValuesPatternFactory#list(org.eclipse.
	 *      imp
	 *      .pdb.facts.type.Type,
	 *      nuthatch.pattern.Pattern<org.eclipse.imp.pdb.facts
	 *      .IValue,org.eclipse.imp.pdb.facts.type.Type>[])
	 */
	public static final Pattern<IValue, Type> list(Type listType, Pattern<IValue, Type>... children) {
		return PF.list(listType, children);
	}


	public static final Pattern<IValue, Type> preserveAnnos(Pattern<IValue, Type> pat, Pattern<IValue, Type> annoSrc) {
		return PF.preserveAnnos(pat, annoSrc);
	}


	/**
	 * @return
	 * @see nuthatch.rascal.pattern.impl.ValuesPatternFactory#rat()
	 */
	public static Pattern<IValue, Type> rat() {
		return PF.rat();
	}


	/**
	 * @param r
	 * @return
	 * @see nuthatch.rascal.pattern.impl.ValuesPatternFactory#rat(org.eclipse.imp.pdb.facts.IRational)
	 */
	public static Pattern<IValue, Type> rat(IRational r) {
		return PF.rat(r);
	}


	/**
	 * @return
	 * @see nuthatch.rascal.pattern.impl.ValuesPatternFactory#real()
	 */
	public static Pattern<IValue, Type> real() {
		return PF.real();
	}


	/**
	 * @param r
	 * @return
	 * @see nuthatch.rascal.pattern.impl.ValuesPatternFactory#real(org.eclipse.imp.pdb.facts.IReal)
	 */
	public static Pattern<IValue, Type> real(IReal r) {
		return PF.real(r);
	}


	/**
	 * @return
	 * @see nuthatch.rascal.pattern.impl.ValuesPatternFactory#string()
	 */
	public static Pattern<IValue, Type> string() {
		return PF.string();
	}


	/**
	 * @param s
	 * @return
	 * @see nuthatch.rascal.pattern.impl.ValuesPatternFactory#string(org.eclipse.imp.pdb.facts.IString)
	 */
	public static Pattern<IValue, Type> string(IString s) {
		return PF.string(s);
	}


	/**
	 * @param children
	 * @return
	 * @see
	 *      nuthatch.rascal.pattern.impl.ValuesPatternFactory#tuple(nuthatch.
	 *      pattern
	 *      .Pattern<org.eclipse.imp.pdb.facts.IValue,org.eclipse.imp.pdb.facts.
	 *      type.Type>[])
	 */
	public static final Pattern<IValue, Type> tuple(Pattern<IValue, Type>... children) {
		return PF.tuple(children);
	}


	public static Pattern<IValue, Type> var(String s) {
		return (Pattern<IValue, Type>) StaticPatternFactory.var(s);
	}
}
