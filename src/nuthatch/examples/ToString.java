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
		StringTreeActionFactory actions = StringTreeActionFactory.getInstance();

		final StringBuffer s = new StringBuffer();
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
		StringTreeWalker toTermWalker = new StringTreeWalker(ExampleTree.TREE, actions.walk(toTerm));
		// run it
		toTermWalker.start();
		// print the contents of S
		System.out.println(s.toString());
	}
}
