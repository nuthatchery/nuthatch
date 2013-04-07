package nuthatch.examples;

import static nuthatch.library.JoinPoints.down;
import static nuthatch.library.JoinPoints.leaf;
import static nuthatch.library.JoinPoints.up;
import nuthatch.library.BaseWalk;
import nuthatch.library.Walk;
import nuthatch.walker.impl.SimpleWalker;

public class ToString {
	public static void main(String[] args) {
		// Walk which outputs the tree in a term-like representation. The result are accumulated in the
		// stringbuffer 's'.
		final StringBuffer s = new StringBuffer();
		Walk<SimpleWalker<String, String>> toTerm = new BaseWalk<SimpleWalker<String, String>>() {
			@Override
			public int step(SimpleWalker<String, String> w) {
				if(leaf(w)) { // we're at a leaf, print data value
					s.append(w.getData().toString());
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
		SimpleWalker<String, String> toTermWalker = new SimpleWalker<String, String>(ExampleTree.TREE.makeCursor(), toTerm);
		// run it
		toTermWalker.start();
		// print the contents of S
		System.out.println(s.toString());
	}
}
