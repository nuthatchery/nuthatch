package nuthatch.pattern;

import nuthatch.tree.TreeCursor;

/**
 * You will likely not want to use this StaticPatternFactory directly, as some
 * methods return Pattern<?,?>. It's better to us an instantiation of
 * PatternFactory, or a specific static factory tailored for your value and type
 * types.
 * 
 */
@SuppressWarnings("unchecked")
public class StaticPatternFactory {
	@SuppressWarnings("rawtypes")
	private static final PatternFactory PF = PatternFactory.getInstance();


	/**
	 * @see PatternFactory#any()
	 */
	public static Pattern<?, ?> _() {
		return PF.any();
	}


	/**
	 * @see PatternFactory#ancestor(Pattern)
	 */
	public static final <Value, Type> Pattern<Value, Type> ancestor(Pattern<Value, Type> ancestor) {
		return PF.ancestor(ancestor);
	}


	/**
	 * @see PatternFactory#and(Pattern, Pattern)
	 */
	public static final <Value, Type> Pattern<Value, Type> and(Pattern<Value, Type> a, Pattern<Value, Type> b) {
		return PF.and(a, b);
	}


	/**
	 * @see PatternFactory#children(Pattern[])
	 */
	public static final <Value, Type> Pattern<Value, Type> children(Pattern<Value, Type>... children) {
		return PF.children(children);
	}


	/**
	 * @see PatternFactory#node(java.lang.String, java.lang.Object,
	 *      java.lang.Object)
	 */
	public static final <Value, Type> Pattern<Value, Type> node(String name, Type type, Value data) {
		return PF.node(name, type, data);
	}


	/**
	 * @see PatternFactory#nodeData(java.lang.Object)
	 */
	public static <Value> Pattern<Value, ?> nodeData(Value data) {
		return PF.nodeData(data);
	}


	/**
	 * @see PatternFactory#nodeName(java.lang.String)
	 */
	public static Pattern<?, ?> nodeName(String name) {
		return PF.nodeName(name);
	}


	/**
	 * @see PatternFactory#nodeType(java.lang.Object)
	 */
	public static <Type> Pattern<?, Type> nodeType(Type type) {
		return PF.nodeType(type);
	}


	/**
	 * @see PatternFactory#nodeWithChildren(java.lang.String, Pattern[])
	 */
	public static final <Value, Type> Pattern<Value, Type> nodeWithChildren(String name, Pattern<Value, Type>... children) {
		return PF.nodeWithChildren(name, children);
	}


	/**
	 * @see PatternFactory#nodeWithChildren(java.lang.String, java.lang.Object,
	 *      java.lang.Object, Pattern[])
	 */
	public static final <Value, Type> Pattern<Value, Type> nodeWithChildren(String name, Type type, Value data, Pattern<Value, Type>... children) {
		return PF.nodeWithChildren(name, type, data, children);
	}


	/**
	 * @see PatternFactory#not(Pattern)
	 */
	public static <Value, Type> Pattern<Value, Type> not(Pattern<Value, Type> a) {
		return PF.not(a);
	}


	/**
	 * @see PatternFactory#or(Pattern, Pattern)
	 */
	public static <Value, Type> Pattern<Value, Type> or(Pattern<Value, Type> a, Pattern<Value, Type> b) {
		return PF.or(a, b);
	}


	/**
	 * @see PatternFactory#parent(Pattern)
	 */
	public static final <Value, Type> Pattern<Value, Type> parent(Pattern<Value, Type> parent) {
		return PF.parent(parent);
	}


	/**
	 * @see PatternFactory#tree(nuthatch.tree.TreeCursor)
	 */
	public static final <Value, Type> Pattern<Value, Type> tree(TreeCursor<Value, Type> tree) {
		return PF.tree(tree);
	}


	/**
	 * @see PatternFactory#var(java.lang.String)
	 */
	public static Pattern<?, ?> var(String name) {
		return PF.var(name);
	}


	/**
	 * @see PatternFactory#var(java.lang.String, Pattern)
	 */
	public static <Value, Type> Pattern<Value, Type> var(String name, Pattern<Value, Type> p) {
		return PF.var(name, p);
	}
}
