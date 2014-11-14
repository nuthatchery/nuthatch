package nuthatch.rascal.adapter;

import nuthatch.library.Action;
import nuthatch.library.ActionFactory;
import nuthatch.library.FactoryFactory;
import nuthatch.library.MatchBuilder;
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


	public Action<ValuesWalker> renameAction(IMap renaming) {
		MatchBuilder<IValue, Type, ValuesCursor, ValuesWalker> matchBuilder = af.matchBuilder(context);

		for(IValue key : renaming) {
			matchBuilder.add(StaticPatternFactory.tree(new ValuesCursor(key)), af.replace(StaticPatternFactory.tree(new ValuesCursor(renaming.get(key)))));
		}

		return matchBuilder.one();
	}


	public static ActionFactory<IValue, Type, ValuesCursor, ValuesWalker> getActionFactory() {
		return af;
	}
}
