package nuthatch;

import nuthatch.library.walks.Bottomup;
import nuthatch.library.walks.Inorder;
import nuthatch.library.walks.Topdown;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.impl.StringTree;
import nuthatch.walk.Action;
import nuthatch.walk.Step;
import nuthatch.walker.Walker;
import nuthatch.walker.impl.SimpleWalker;

public class Main {
	public static void main(String[] args) {
		StringTree tree = new StringTree("+", new StringTree("5"), new StringTree("*", new StringTree("+", new StringTree("7"), new StringTree("3")), new StringTree("4")));

		Action t = new Action() {
			@Override
			public TreeCursor apply(Walker e) {
				System.out.print(e.getName() + " ");
				return null;
			}
		};
		Topdown topDown = new Topdown(t);
		System.out.print("TopDown: ");
		new SimpleWalker<String, String>(tree, topDown).start();
		System.out.println();

		Bottomup bottomup = new Bottomup(t);
		System.out.print("BottomUp: ");
		new SimpleWalker<String, String>(tree, bottomup).start();
		System.out.println();

		Inorder inorder = new Inorder(new Action() {

			@Override
			public TreeCursor apply(Walker e) {
				System.out.print("(");
				return null;
			}
		}, t, new Action() {

			@Override
			public TreeCursor apply(Walker e) {
				System.out.print(")");
				return null;
			}
		});
		System.out.print("InOrder: ");
		new SimpleWalker<String, String>(tree, inorder).start();
		System.out.println();

		System.out.println(tree);

		Action<String, String> u = new Action<String, String>() {
			@Override
			public TreeCursor<String, String> apply(Walker<String, String> e) {
				if(e.hasName()) {
					String name = e.getName();
					if(!name.matches("^[0-9]+$")) {
						int a = Integer.valueOf(e.getBranch(1).getName());
						int b = Integer.valueOf(e.getBranch(2).getName());

						switch(name) {
						case "+":
							return new StringTree(String.valueOf(a + b)).makeCursor();
						case "*":
							return new StringTree(String.valueOf(a + b)).makeCursor();
						}
					}
					return null;
				}
				else {
					return null;
				}
			}
		};
		Step<SimpleWalker<String, String>> replace = new Bottomup<>(u);
		System.out.print("TopDownReplace: ");
		SimpleWalker<String, String> engine = new SimpleWalker<String, String>(tree, replace);
		engine.start();
		System.out.println(engine.treeToString());

	}
}
