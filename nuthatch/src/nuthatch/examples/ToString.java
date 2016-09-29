package nuthatch.examples;

import static nuthatch.library.JoinPoints.down;
import static nuthatch.library.JoinPoints.leaf;
import static nuthatch.library.JoinPoints.up;

import nuthatch.library.Action;

public class ToString {
	public static String indent(int n) {
		StringBuilder b = new StringBuilder(n*2);
		for(int i = 0; i < n; i++) {
			b.append("  ");
		}
		return b.toString();
	}

	public static void main(String[] args) {
		// Walk which outputs the tree in a term-like representation. The result are accumulated in the
		// stringbuffer 's'.
		// 			System.out.println("Visiting: " + indent(w.depth())  + w.getData());

		StringTreeActionFactory af = StringTreeActionFactory.getInstance();

		final StringBuilder s = new StringBuilder();
		Action<StringTreeWalker> toTerm = (StringTreeWalker w) -> {
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

			return Action.NEXT;
		};

		// instantiate walker with an example tree and the above step function
		StringTreeWalker toTermWalker = new StringTreeWalker(ExampleTree.TREE, af.walk(toTerm));
		// run it
		toTermWalker.start();
		// print the contents of S
		System.out.println(s.toString());

		final StringBuilder t = new StringBuilder();

		toTerm = af.seq(//
				af.atLeaf((w) -> { t.append(w.getData()); return Action.NEXT;}), //
				af.down((w) -> { t.append(w.getName() + "("); return Action.NEXT;}), //
				af.afterChild(af.beforeChild((w) -> { t.append(", "); return Action.NEXT;})), //
				af.up((w) -> { t.append(")"); return Action.NEXT;}));

		toTermWalker = new StringTreeWalker(ExampleTree.TREE, af.walk(toTerm));
		// run it
		toTermWalker.start();
		// print the contents of S
		System.out.println(t.toString());

	}
}
