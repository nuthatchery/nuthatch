package nuthatch;

import nuthatch.engine.Engine;
import nuthatch.engine.SimpleTransform;
import nuthatch.engine.Strategy;
import nuthatch.engine.Transform;
import nuthatch.engine.impl.BottomupStrategy;
import nuthatch.engine.impl.InorderStrategy;
import nuthatch.engine.impl.StrategicEngine;
import nuthatch.engine.impl.TopdownStrategy;
import nuthatch.engine.impl.VisitStrategy;
import nuthatch.tree.DoubleTree;
import nuthatch.tree.Tree;

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
				System.out.println("node (" + tree.getNodeId() + ") {" + tree.getName() + "} [->]");
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
		System.out.println("    every node/.style={circle, draw=gray, fill=white, circular drop shadow,text centered, anchor=north, text=black},");
		System.out.println("    level distance=0.5cm, growth parent anchor=south");
		System.out.println("]");
		System.out.print("\\");
		new StrategicEngine(tree, toTikz).engage();
		System.out.println(";");

		new StrategicEngine(tree, new TopdownStrategy(new Transform() {
			@Override
			public Tree apply(Engine e) {
				if(e.from(Tree.PARENT)) {
					if(!e.isRoot()) {
						System.out.println("\\down{" + e.currentTree().getBranch(e.from()).getNodeId() + "}{" + e.currentTree().getNodeId() + "}");
					}
				}
				else {
					System.out.println("\\up{" + e.currentTree().getBranch(e.from()).getNodeId() + "}{" + e.currentTree().getNodeId() + "}");
				}
				return null;
			}
		})).engage();

		System.out.println("\\end{tikzpicture}");
		System.out.println("\\end{center}");
		System.out.println("\\end{document}");
	}
}
