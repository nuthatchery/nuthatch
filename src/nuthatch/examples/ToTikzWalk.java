package nuthatch.examples;

import nuthatch.library.impl.actions.AbstractAction;
import nuthatch.library.impl.actions.AbstractComposeBuilder;
import nuthatch.library.impl.actions.ActionFactory;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Action;
import nuthatch.walk.Walk;
import nuthatch.walk.impl.SimpleWalker;

final class ToTikzWalk implements Walk<SimpleWalker<String, String>> {
	StringBuilder string = new StringBuilder();
	private Action<SimpleWalker<String, String>> action;
	private static final ActionFactory<String, String, TreeCursor<String, String>, SimpleWalker<String, String>> AF = ActionFactory.getInstance();


	public String getResult() {
		return string.toString();
	}


	@Override
	public void init(SimpleWalker<String, String> walker) {
		string = new StringBuilder();
		AbstractComposeBuilder<SimpleWalker<String, String>> seq = AF.sequenceBuilder();

		seq.add(AF.down(new AbstractAction<SimpleWalker<String, String>>() {
			@Override
			public int step(SimpleWalker<String, String> walker) {
				ToLaTeX.spaces(walker);
				string.append("node[treenode] (" + walker.getPathId() + ") {" + walker.getName() + "} [->]\n");
				return PROCEED;
			}

		}));
		seq.add(AF.afterChild(new AbstractAction<SimpleWalker<String, String>>() {
			@Override
			public int step(SimpleWalker<String, String> walker) {
				ToLaTeX.spaces(walker);
				string.append("}\n");
				return PROCEED;
			}

		}));
		seq.add(AF.beforeChild(new AbstractAction<SimpleWalker<String, String>>() {
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
