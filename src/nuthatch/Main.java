package nuthatch;

import java.util.ArrayList;
import java.util.List;

import nuthatch.library.walks.Bottomup;
import nuthatch.library.walks.Inorder;
import nuthatch.library.walks.Topdown;
import nuthatch.library.walks.Visitor;
import nuthatch.library.walks.VisitorAspect;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.impl.StandardTree;
import nuthatch.walk.Action;
import nuthatch.walk.Step;
import nuthatch.walker.Walker;
import nuthatch.walker.impl.BasicWalker;

public class Main {
	public static void main(String[] args) {
		StandardTree<String, String> tree = new StandardTree("+", "", new StandardTree("5", ""), new StandardTree("*", "", new StandardTree("+", "", new StandardTree("7", ""), new StandardTree("3", "")), new StandardTree("4", "")));

		Action t = new Action() {
			@Override
			public TreeCursor apply(Walker e) {
				System.out.print(e.getName() + " ");
				return null;
			}
		};
		Topdown topDown = new Topdown(t);
		System.out.print("TopDown: ");
		new BasicWalker<String, String>(tree, topDown).start();
		System.out.println();

		Bottomup bottomup = new Bottomup(t);
		System.out.print("BottomUp: ");
		new BasicWalker<String, String>(tree, bottomup).start();
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
		new BasicWalker<String, String>(tree, inorder).start();
		System.out.println();

		Step<BasicWalker<String, String>> toTikz = new Visitor<BasicWalker<String, String>>() {
			@Override
			public void afterChild(BasicWalker<String, String> walker, int child) {
				walker.appendToS("}");
			}


			@Override
			public void beforeChild(BasicWalker<String, String> walker, int child) {
				walker.appendToS("child{");
			}


			@Override
			public void onEntry(BasicWalker<String, String> walker) {
				walker.appendToS("node[treenode] (" + walker.getPathId() + ") {" + walker.getName() + "} [->]");
			}


			@Override
			public void onExit(BasicWalker<String, String> walker) {
			}
		};

		System.out.println("\\documentclass{minimal}");
		System.out.println("\\usepackage[a4paper,margin=1cm,landscape]{geometry}");
		System.out.println("\\usepackage{tikz}");
		System.out.println("\\usetikzlibrary{positioning,shadows,arrows}");
		System.out.println("");
		System.out.println("\\begin{document}");
		System.out.println("\\begin{center}");
		System.out.println("\\begin{tikzpicture}[");
		System.out.println("    level distance=0.5cm, growth parent anchor=south");
		System.out.println("]");
		System.out.print("\\");
		new BasicWalker<String, String>(tree.makeCursor(), toTikz).start();
		System.out.println(";");

		final List<String> visits = new ArrayList<String>();
		Action traceVisit = new Action() {

			@Override
			public TreeCursor apply(Walker e) {
				visits.add(e.getPathId());
				return null;
			}
		};
		new BasicWalker<String, String>(tree, new VisitorAspect<BasicWalker<String, String>>(new Inorder<String, String, BasicWalker<String, String>>(traceVisit, traceVisit, traceVisit)) {
			@Override
			public int before(BasicWalker<String, String> e) {
				TreeCursor<String, String> prev = e.getBranch(e.from());
				int branch = e.getFromBranch();
				if(e.from(Tree.PARENT)) {
					if(!e.isRoot()) {
						System.out.println("\\down{" + prev.getPathId() + "}{" + branch + "}{" + e.getPathId() + "}");
					}
				}
				else {
					System.out.println("\\up{" + prev.getPathId() + "}{" + e.from() + "}{" + e.getPathId() + "}");
				}
				return PROCEED;
			}
		}).start();
		if(!visits.isEmpty()) {
			int i = 0;
			String last = visits.get(i++);
			while(i < visits.size()) {
				String current = visits.get(i++);
				System.out.println("\\visit{" + last + "}{" + current + "}");
				last = current;
			}

		}
		System.out.println("\\end{tikzpicture}");
		System.out.println("\\end{center}");
		System.out.println("\\end{document}");

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
							return new StandardTree<String, String>(String.valueOf(a + b), "").makeCursor();
						case "*":
							return new StandardTree<String, String>(String.valueOf(a + b), "").makeCursor();
						}
					}
					return null;
				}
				else {
					return null;
				}
			}
		};
		Step<BasicWalker<String, String>> replace = new Bottomup<>(u);
		System.out.print("TopDownReplace: ");
		BasicWalker<String, String> engine = new BasicWalker<String, String>(tree, replace);
		engine.start();
		System.out.println(engine.treeToString());

	}
}
