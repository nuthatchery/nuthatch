package nuthatch;

import java.util.ArrayList;
import java.util.List;

import nuthatch.engine.Engine;
import nuthatch.engine.impl.StrategicEngine;
import nuthatch.library.strategies.BottomupStrategy;
import nuthatch.library.strategies.InorderStrategy;
import nuthatch.library.strategies.TopdownStrategy;
import nuthatch.library.strategies.VisitStrategy;
import nuthatch.strategy.Before;
import nuthatch.strategy.Strategy;
import nuthatch.strategy.Transform;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.impl.StandardTree;

public class Main {
	public static void main(String[] args) {
		Tree<String, String> tree = new StandardTree("+", "", new StandardTree("5", ""), new StandardTree("*", "", new StandardTree("+", "", new StandardTree("7", ""), new StandardTree("3", "")), new StandardTree("4", "")));

		Transform t = new Transform() {
			@Override
			public TreeCursor apply(Engine e) {
				System.out.print(e.getName() + " ");
				return null;
			}
		};
		TopdownStrategy topDown = new TopdownStrategy(t);
		System.out.print("TopDown: ");
		new StrategicEngine<String, String>(tree, topDown).engage();
		System.out.println();

		BottomupStrategy bottomup = new BottomupStrategy(t);
		System.out.print("BottomUp: ");
		new StrategicEngine<String, String>(tree, bottomup).engage();
		System.out.println();

		InorderStrategy inorder = new InorderStrategy(new Transform() {

			@Override
			public TreeCursor apply(Engine e) {
				System.out.print("(");
				return null;
			}
		}, t, new Transform() {

			@Override
			public TreeCursor apply(Engine e) {
				System.out.print(")");
				return null;
			}
		});
		System.out.print("InOrder: ");
		new StrategicEngine<String, String>(tree, inorder).engage();
		System.out.println();

		Strategy toTikz = new VisitStrategy(new Transform() {
			@Override
			public TreeCursor apply(Engine tree) {
				System.out.println("node[treenode] (" + tree.getPathId() + ") {" + tree.getName() + "} [->]");
				return null;
			}
		}, new Transform() {
			@Override
			public TreeCursor apply(Engine tree) {
				return null;
			}
		}, new Transform() {
			@Override
			public TreeCursor apply(Engine tree) {
				System.out.println("child{");
				return null;
			}
		}, new Transform() {
			@Override
			public TreeCursor apply(Engine tree) {
				System.out.println("}");
				return null;
			}
		});
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
		new StrategicEngine<String, String>(tree, toTikz).engage();
		System.out.println(";");

		final List<String> visits = new ArrayList<String>();
		Transform traceVisit = new Transform() {

			@Override
			public TreeCursor apply(Engine e) {
				visits.add(e.getPathId());
				return null;
			}
		};
		new StrategicEngine<String, String>(tree, new Before(new InorderStrategy(traceVisit, traceVisit, traceVisit)) {
			@Override
			public int visitBefore(Engine e) {
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
		}).engage();
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

		Transform<String, String> u = new Transform<String, String>() {
			@Override
			public TreeCursor<String, String> apply(Engine<String, String> e) {
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
				else
					return null;
			}
		};
		Strategy<String, String> replace = new BottomupStrategy<>(u);
		System.out.print("TopDownReplace: ");
		StrategicEngine<String, String> engine = new StrategicEngine<String, String>(tree, replace);
		engine.engage();
		System.out.println(engine.treeToString());

	}
}
