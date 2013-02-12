package nuthatch.examples;

import nuthatch.walk.Step;
import nuthatch.walker.impl.SimpleWalker;

public class ToString {
	public static void main(String[] args) {
		Step<SimpleWalker<String, String>> toTerm = new Step<SimpleWalker<String, String>>() {
			@Override
			public int step(SimpleWalker<String, String> w) {
				if(w.isAtLeaf()) {
					w.appendToS(w.getData().toString());
				}
				else if(w.from(PARENT)) {
					w.appendToS(w.getName() + "(");
				}
				else if(w.from(LAST)) {
					w.appendToS(")");
				}
				else {
					w.appendToS(", ");
				}

				return NEXT;
			}
		};

		SimpleWalker<String, String> toTermWalker = new SimpleWalker<String, String>(ExampleTree.TREE, toTerm);
		toTermWalker.start();
		System.out.println(toTermWalker.getS());
	}
}
