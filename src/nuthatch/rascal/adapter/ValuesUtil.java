package nuthatch.rascal.adapter;

import nuthatch.library.Action;
import nuthatch.library.ActionFactory;
import nuthatch.library.BaseMatchAction;
import nuthatch.library.FactoryFactory;
import nuthatch.library.MatchBuilder;
import nuthatch.library.Walk;
import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.pattern.StaticPatternFactory;
import nuthatch.rascal.pattern.ValuesBuildContext;

import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeStore;

public class ValuesUtil {
	private static ActionFactory<IValue, Type, ValuesCursor, ValuesWalker> af = FactoryFactory.getActionFactory(IValue.class, Type.class, ValuesCursor.class, ValuesWalker.class);

	private final ValuesBuildContext context;


	public ValuesUtil(IValueFactory vf, TypeStore ts) {
		context = new ValuesBuildContext(vf, ts);
	}


	public boolean contains(IValue tree, IValue pattern) {
		Walk<ValuesWalker> walk = af.walk(af.down(af.match(StaticPatternFactory.tree(new ValuesCursor(pattern)), new BaseMatchAction<IValue, Type, ValuesCursor, ValuesWalker>() {

			@Override
			public int step(ValuesWalker walker, Environment<ValuesCursor> env) {
				throw new Value(null);
			}
		})));
		ValuesWalker valuesWalker = new ValuesWalker(tree, walk);

		try {
			valuesWalker.start();
		}
		catch(Value v) {
			return true;
		}
		return false;
	}


	public IValue findOne(IValue tree, Pattern<IValue, Type> pattern) {
		Walk<ValuesWalker> walk = af.walk(af.down(af.match(pattern, new BaseMatchAction<IValue, Type, ValuesCursor, ValuesWalker>() {

			@Override
			public int step(ValuesWalker walker, Environment<ValuesCursor> env) {
				throw new Value(walker.getData());
			}
		})));
		ValuesWalker valuesWalker = new ValuesWalker(tree, walk);

		try {
			valuesWalker.start();
		}
		catch(Value v) {
			return v.value;
		}
		return null;
	}


	public Action<ValuesWalker> renameAction(IMap renaming) {
		MatchBuilder<IValue, Type, ValuesCursor, ValuesWalker> matchBuilder = af.matchBuilder(context);

		for(IValue key : renaming) {
			matchBuilder.add(StaticPatternFactory.tree(new ValuesCursor(key)), af.replace(StaticPatternFactory.tree(new ValuesCursor(renaming.get(key)))));
		}

		return matchBuilder.one();
	}


	public Action<ValuesWalker> replaceAction(IValue from, IValue to) {
		return af.match(StaticPatternFactory.tree(new ValuesCursor(from)), af.replace(StaticPatternFactory.tree(new ValuesCursor(to))));
	}


	@SuppressWarnings("unchecked")
	public Walk<ValuesWalker> replaceWalk(IValue from, IValue to, boolean recursive) {
		if(recursive) {
			return af.walk(af.down(replaceAction(from, to)));
		}
		else {
			return af.walk(af.down(af.seq( //
			af.match(StaticPatternFactory.tree(new ValuesCursor(from)), af.replace(StaticPatternFactory.tree(new ValuesCursor(to)))), af.go(Action.PARENT))));
		}
	}


	public IValue transform(IValue tree, Walk<ValuesWalker> walk) {
		ValuesWalker valuesWalker = new ValuesWalker(tree, walk);
		valuesWalker.start();
		return valuesWalker.getData();
	}


	public static ActionFactory<IValue, Type, ValuesCursor, ValuesWalker> getActionFactory() {
		return af;
	}


	@SuppressWarnings("serial")
	private static class Value extends RuntimeException {
		final IValue value;


		Value(IValue v) {
			value = v;
		}
	}
}
