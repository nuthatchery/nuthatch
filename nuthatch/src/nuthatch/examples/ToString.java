package nuthatch.examples;

import static nuthatch.library.JoinPoints.down;
import static nuthatch.library.JoinPoints.leaf;
import static nuthatch.library.JoinPoints.up;
import nuthatch.library.Action;
import nuthatch.library.BaseAction;

public class ToString {
	public static void main(String[] args) {
		// Walk which outputs the tree in a term-like representation. The result are accumulated in the
		// stringbuffer 's'.
		StringTreeActionFactory af = StringTreeActionFactory.getInstance();

		final StringBuilder s = new StringBuilder();
		Action<StringTreeWalker> toTerm = new BaseAction<StringTreeWalker>() {
			@Override
			public int step(StringTreeWalker w) {
				if(leaf(w)) { // we're at a leaf, print data value
					s.append(w.getData());
				}
				else if(down(w)) { // first time we see this node, print constructor name
					s.append(w.getName() + "(");
				}
				else if(up(w)) { // just finished with children, close parenthesis
					s.append(")");
				}
				else { // coming up from a child (not the last), insert a comma
					s.append(", ");
				}

				return NEXT;
			}
		};

		// instantiate walker with an example tree and the above step function
		StringTreeWalker toTermWalker = new StringTreeWalker(ExampleTree.TREE, af.walk(toTerm));
		// run it
		toTermWalker.start();
		// print the contents of S
		System.out.println(s.toString());

		final StringBuilder t = new StringBuilder();

		Action<StringTreeWalker> appendData = new BaseAction<StringTreeWalker>() {
			@Override
			public int step(StringTreeWalker walker) {
				t.append(walker.getData());
				return NEXT;
			}
		};
		Action<StringTreeWalker> appendName = new BaseAction<StringTreeWalker>() {
			@Override
			public int step(StringTreeWalker walker) {
				t.append(walker.getName() + "(");
				return NEXT;
			}
		};
		Action<StringTreeWalker> appendEnd = new BaseAction<StringTreeWalker>() {
			@Override
			public int step(StringTreeWalker walker) {
				t.append(")");
				return NEXT;
			}
		};
		Action<StringTreeWalker> appendComma = new BaseAction<StringTreeWalker>() {
			@Override
			public int step(StringTreeWalker walker) {
				t.append(", ");
				return NEXT;
			}
		};
		toTerm = af.seq(af.atLeaf(appendData), af.down(appendName), af.afterChild((af.beforeChild(appendComma))), af.up(appendEnd));

		toTermWalker = new StringTreeWalker(ExampleTree.TREE, af.walk(toTerm));
		// run it
		toTermWalker.start();
		// print the contents of S
		System.out.println(t.toString());

	}
}
