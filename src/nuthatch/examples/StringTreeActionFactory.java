package nuthatch.examples;

import nuthatch.library.impl.actions.StandardActionFactory;
import nuthatch.tree.TreeCursor;

class StringTreeActionFactory extends StandardActionFactory<String, String, TreeCursor<String, String>, StringTreeWalker> {
	public static StringTreeActionFactory actionFactory = new StringTreeActionFactory();


	@SuppressWarnings("unchecked")
	public static StringTreeActionFactory getInstance() {
		return actionFactory;
	}
}