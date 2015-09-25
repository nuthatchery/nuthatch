package nuthatch.library;

import nuthatch.library.impl.actions.StandardActionFactory;
import nuthatch.pattern.PatternFactory;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

public class FactoryFactory {
	public static <Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type, W>> ActionFactory<Value, Type, C, W> getActionFactory() {
		return StandardActionFactory.getInstance();
	}


	public static <Value, Type, C extends TreeCursor<Value, Type>, W extends Walker<Value, Type, W>> ActionFactory<Value, Type, C, W> getActionFactory(Class<Value> valueClass, Class<Type> typeClass, Class<C> cursorClass, Class<W> walkerClass) {
		return StandardActionFactory.getInstance();
	}


	public static <Value, Type> PatternFactory<Value, Type> getPatternFactory() {
		return PatternFactory.getInstance();
	}


	public static <Value, Type> PatternFactory<Value, Type> getPatternFactory(Class<Value> valueClass, Class<Type> typeClass) {
		return PatternFactory.getInstance(valueClass, typeClass);
	}
}
