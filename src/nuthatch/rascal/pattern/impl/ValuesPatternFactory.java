package nuthatch.rascal.pattern.impl;

import nuthatch.pattern.Pattern;

import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IRational;
import org.eclipse.imp.pdb.facts.IReal;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;

public class ValuesPatternFactory {
	static final ValuesPatternFactory vpfInstance = new ValuesPatternFactory();
	static final TypeFactory tf = TypeFactory.getInstance();


	private ValuesPatternFactory() {

	}


	@SafeVarargs
	public final Pattern<IValue, Type> cons(String name, Pattern<IValue, Type>... children) {
		return new NamedConsPattern(name, null, children);
	}


	@SafeVarargs
	public final Pattern<IValue, Type> cons(String name, Type type, Pattern<IValue, Type>... children) {
		return new NamedConsPattern(name, type, children);
	}


	@SafeVarargs
	public final Pattern<IValue, Type> cons(Type consType, Pattern<IValue, Type>... children) {
		return new TypedConsPattern(consType, children);
	}


	public Pattern<IValue, Type> integer() {
		return new PrimitivePattern(null, tf.integerType());
	}


	public Pattern<IValue, Type> integer(IInteger i) {
		return new PrimitivePattern(i, tf.integerType());
	}


	public final Pattern<IValue, Type> isList() {
		return new ListPattern(null, null);
	}


	public final Pattern<IValue, Type> isList(Type listType) {
		return new ListPattern(listType, null);
	}


	@SafeVarargs
	public final Pattern<IValue, Type> list(Pattern<IValue, Type>... children) {
		return new ListPattern(null, children);
	}


	@SafeVarargs
	public final Pattern<IValue, Type> list(Type listType, Pattern<IValue, Type>... children) {
		return new ListPattern(listType, children);
	}


	public Pattern<IValue, Type> listElement(Type listType, Pattern<IValue, Type> prefix, Pattern<IValue, Type> element, Pattern<IValue, Type> suffix) {
		return new ListElementPattern(listType, prefix, element, suffix);
	}


	public Pattern<IValue, Type> rat() {
		return new PrimitivePattern(null, tf.rationalType());
	}


	public Pattern<IValue, Type> rat(IRational r) {
		return new PrimitivePattern(r, tf.rationalType());
	}


	public Pattern<IValue, Type> real() {
		return new PrimitivePattern(null, tf.realType());
	}


	public Pattern<IValue, Type> real(IReal r) {
		return new PrimitivePattern(r, tf.realType());
	}


	public Pattern<IValue, Type> string() {
		return new PrimitivePattern(null, tf.stringType());
	}


	public Pattern<IValue, Type> string(IString s) {
		return new PrimitivePattern(s, tf.stringType());
	}


	@SafeVarargs
	public final Pattern<IValue, Type> tuple(Pattern<IValue, Type>... children) {
		return new TuplePattern(null, children);
	}


	public static ValuesPatternFactory getInstance() {
		return vpfInstance;
	}


	public static final Pattern<IValue, Type> preserveAnnos(Pattern<IValue, Type> pat, Pattern<IValue, Type> annoSrc) {
		return new PreserveAnnos(pat, annoSrc);
	}

}
