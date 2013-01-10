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
import nuthatch.strategy.SimpleTransform;
import nuthatch.strategy.Strategy;
import nuthatch.strategy.Transform;
import nuthatch.tree.Tree;
import nuthatch.tree.impl.DoubleTree;

public class Main {
	public static void main(String[] args) {
		Tree tree = new DoubleTree("+", "", new DoubleTree("5", ""), new DoubleTree("*", "", new DoubleTree("+", "", new DoubleTree("7", ""), new DoubleTree("3", "")), new DoubleTree("4", "")));

		System.out.println(tree);

		Transform t = new SimpleTransform() {
			@Override
			public Tree apply(Tree tree) {
				System.out.print(tree.getName() + " ");
				return null;
			}
		};
		TopdownStrategy topDown = new TopdownStrategy(t);
		System.out.print("TopDown: ");
		new StrategicEngine(tree, topDown).engage();
		System.out.println();

		BottomupStrategy bottomup = new BottomupStrategy(t);
		System.out.print("BottomUp: ");
		new StrategicEngine(tree, bottomup).engage();
		System.out.println();

		InorderStrategy inorder = new InorderStrategy(new SimpleTransform() {

			@Override
			public Tree apply(Tree tree) {
				System.out.print("(");
				return null;
			}
		}, t, new SimpleTransform() {

			@Override
			public Tree apply(Tree tree) {
				System.out.print(")");
				return null;
			}
		});
		System.out.print("InOrder: ");
		new StrategicEngine(tree, inorder).engage();
		System.out.println();

		Strategy toTikz = new VisitStrategy(new SimpleTransform() {
			@Override
			public Tree apply(Tree tree) {
				System.out.println("node[treenode] (" + tree.getNodeId() + ") {" + tree.getName() + "} [->]");
				return null;
			}
		}, new SimpleTransform() {
			@Override
			public Tree apply(Tree tree) {
				return null;
			}
		}, new SimpleTransform() {
			@Override
			public Tree apply(Tree tree) {
				System.out.println("child{");
				return null;
			}
		}, new SimpleTransform() {
			@Override
			public Tree apply(Tree tree) {
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
		new StrategicEngine(tree, toTikz).engage();
		System.out.println(";");

		final List<String> visits = new ArrayList<String>();
		SimpleTransform traceVisit = new SimpleTransform() {

			@Override
			public Tree apply(Tree tree) {
				visits.add(tree.getNodeId());
				return null;
			}
		};
		new StrategicEngine(tree, new Before(new InorderStrategy(traceVisit, traceVisit, traceVisit)) {
			@Override
			public int visitBefore(Engine e) {
				Tree prev = e.currentTree().getBranch(e.from());
				int branch = 1;
				if(prev != null)
					branch = prev.getBranch(e.currentTree());
				if(e.from(Tree.PARENT)) {
					if(!e.isRoot()) {
						System.out.println("\\down{" + e.currentTree().getBranch(e.from()).getNodeId() + "}{" + branch + "}{" + e.currentTree().getNodeId() + "}");
					}
				}
				else {
					System.out.println("\\up{" + e.currentTree().getBranch(e.from()).getNodeId() + "}{" + e.from() + "}{" + e.currentTree().getNodeId() + "}");
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
	}
}
