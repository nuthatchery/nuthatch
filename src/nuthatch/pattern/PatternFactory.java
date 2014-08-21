package nuthatch.pattern;

import java.util.Comparator;

import nuthatch.pattern.impl.AncestorPattern;
import nuthatch.pattern.impl.AndPattern;
import nuthatch.pattern.impl.AnyPattern;
import nuthatch.pattern.impl.FromPattern;
import nuthatch.pattern.impl.NodePattern;
import nuthatch.pattern.impl.NotPattern;
import nuthatch.pattern.impl.OrPattern;
import nuthatch.pattern.impl.ParentPattern;
import nuthatch.pattern.impl.TreePattern;
import nuthatch.pattern.impl.VarPattern;
import nuthatch.tree.TreeCursor;

public class PatternFactory<Value, Type> {
	@SuppressWarnings("rawtypes")
	protected static final PatternFactory pfInstance = new PatternFactory();


	protected PatternFactory() {
	}


	public Pattern<Value, Type> ancestor(Pattern<Value, Type> ancestor) {
		return new AncestorPattern<Value, Type>(ancestor);
	}


	public Pattern<Value, Type> and(Pattern<Value, Type> a, Pattern<Value, Type> b) {
		return new AndPattern<Value, Type>(a, b);
	}


	public Pattern<Value, Type> any() {
		return new AnyPattern<Value, Type>();
	}


	@SafeVarargs
	public final Pattern<Value, Type> children(Pattern<Value, Type>... children) {
		return nodeWithChildren(null, null, null, children);
	}


	public Pattern<Value, Type> from(int b) {
		return new FromPattern<Value, Type>(b);
	}


	public Pattern<Value, Type> node(String name, Type type, Value data) {
		return new NodePattern<Value, Type>(name, type, data, null, null);
	}


	public Pattern<Value, Type> nodeData(Value data) {
		return node(null, null, data);
	}


	public Pattern<Value, Type> nodeName(String name) {
		return node(name, null, null);
	}


	public Pattern<Value, Type> nodeType(Type type) {
		return node(null, type, null);
	}


	public Pattern<Value, Type> nodeType(Type type, Comparator<Type> comparator) {
		return new NodePattern<Value, Type>(null, type, null, null, comparator);
	}


	@SafeVarargs
	public final Pattern<Value, Type> nodeWithChildren(String name, Pattern<Value, Type>... children) {
		return new NodePattern<Value, Type>(name, null, null, children, null);
	}


	@SafeVarargs
	public final Pattern<Value, Type> nodeWithChildren(String name, Type type, Value data, Pattern<Value, Type>... children) {
		return new NodePattern<Value, Type>(name, type, data, children, null);
	}


	public Pattern<Value, Type> not(Pattern<Value, Type> a) {
		return new NotPattern<Value, Type>(a);
	}


	public Pattern<Value, Type> or(Pattern<Value, Type> a, Pattern<Value, Type> b) {
		return new OrPattern<Value, Type>(a, b);
	}


	public Pattern<Value, Type> parent(Pattern<Value, Type> parent) {
		return new ParentPattern<Value, Type>(parent);
	}


	public Pattern<Value, Type> tree(TreeCursor<Value, Type> tree) {
		return new TreePattern<Value, Type>(tree);
	}


	public Pattern<Value, Type> var(String name) {
		return new VarPattern<Value, Type>(name, null);
	}


	public Pattern<Value, Type> var(String name, Pattern<Value, Type> p) {
		return new AndPattern<Value, Type>(p, new VarPattern<Value, Type>(name, null));
	}


	public Pattern<Value, Type> var(VarName<?> name) {
		return new VarPattern<Value, Type>(name, null);
	}


	public Pattern<Value, Type> var(VarName<?> name, Pattern<Value, Type> p) {
		return new AndPattern<Value, Type>(p, new VarPattern<Value, Type>(name, null));
	}


	@SuppressWarnings("unchecked")
	public static <Value, Type> PatternFactory<Value, Type> getInstance() {
		return pfInstance;
	}


	/**
	 * Get an instance of the pattern factory.
	 * 
	 * @param valueClass
	 *            Value type, for correct return type
	 * @param typeClass
	 *            Type type, for correct return type
	 * @return The pattern factory
	 */
	@SuppressWarnings("unchecked")
	public static <Value, Type> PatternFactory<Value, Type> getInstance(Class<Value> valueClass, Class<Type> typeClass) {
		return pfInstance;
	}
}
