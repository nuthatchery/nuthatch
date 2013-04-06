package nuthatch.examples;

import nuthatch.library.Walk;
import nuthatch.walker.impl.SimpleWalker;

public class ToString {
	public static void main(String[] args) {
		// Walk which outputs the tree in a term-like representation. The walker has a built-int
		// variable (or register) 'S', which is basically a string builder suitable for accumulating a string
		Walk<SimpleWalker<String, String>> toTerm = new Walk<SimpleWalker<String, String>>() {
			@Override
			public void init(SimpleWalker<String, String> walker) {

			}


			@Override
			public int step(SimpleWalker<String, String> w) {
				if(w.isAtLeaf()) { // we're at a leaf, print data value
					w.appendToS(w.getData().toString());
				}
				else if(w.from(PARENT)) { // first time we see this node, print constructor name
					w.appendToS(w.getName() + "(");
				}
				else if(w.from(LAST)) { // just finished with children, close parenthesis
					w.appendToS(")");
				}
				else { // coming up from a child (not the last), insert a comma
					w.appendToS(", ");
				}

				return NEXT;
			}
		};

		// instantiate walker with an example tree and the above step function
		SimpleWalker<String, String> toTermWalker = new SimpleWalker<String, String>(ExampleTree.TREE.makeCursor(), toTerm);
		// run it
		toTermWalker.start();
		// print the contents of S
		System.out.println(toTermWalker.getS());
	}
}
