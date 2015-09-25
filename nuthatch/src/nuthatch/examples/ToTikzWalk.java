package nuthatch.examples;

import nuthatch.library.Action;
import nuthatch.library.ActionBuilder;
import nuthatch.library.ActionFactory;
import nuthatch.library.BaseAction;
import nuthatch.library.FactoryFactory;
import nuthatch.library.Walk;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.impl.SimpleWalker;

final class ToTikzWalk implements Walk<SimpleWalker<String, String>> {
	StringBuilder string = new StringBuilder();
	private Action<SimpleWalker<String, String>> action;
	private static final ActionFactory<String, String, TreeCursor<String, String>, SimpleWalker<String, String>> AF = FactoryFactory.getActionFactory();


	public String getResult() {
		return string.toString();
	}


	@Override
	public void init(SimpleWalker<String, String> walker) {
		string = new StringBuilder();
		ActionBuilder<SimpleWalker<String, String>> seq = AF.sequenceBuilder();

		seq.add(AF.down(new BaseAction<SimpleWalker<String, String>>() {
			@Override
			public int step(SimpleWalker<String, String> walker) {
				ToLaTeX.spaces(walker);
				string.append("node[treenode] (" + walker.getPathId() + ") {" + walker.getName() + "} [->]\n");
				return PROCEED;
			}

		}));
		seq.add(AF.afterChild(new BaseAction<SimpleWalker<String, String>>() {
			@Override
			public int step(SimpleWalker<String, String> walker) {
				ToLaTeX.spaces(walker);
				string.append("}\n");
				return PROCEED;
			}

		}));
		seq.add(AF.beforeChild(new BaseAction<SimpleWalker<String, String>>() {
			@Override
			public int step(SimpleWalker<String, String> walker) {
				ToLaTeX.spaces(walker);
				string.append("child{\n");
				return PROCEED;
			}

		}));
		System.out.println("ToTikzWalk init!");
		action = seq.done();
	}


	@Override
	public int step(SimpleWalker<String, String> walker) {
		action.step(walker);
		return NEXT;
	}
}
