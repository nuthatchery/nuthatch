package nuthatch;

import nuthatch.engine.Transform;
import nuthatch.engine.impl.BottomupStrategy;
import nuthatch.engine.impl.InorderStrategy;
import nuthatch.engine.impl.StrategicEngine;
import nuthatch.engine.impl.TopdownStrategy;
import nuthatch.tree.DoubleTree;
import nuthatch.tree.Tree;

public class Main {
	public static void main(String[] args) {
		Tree tree = new DoubleTree("+", "", new DoubleTree("5", ""), new DoubleTree("*", "", new DoubleTree("+", "", new DoubleTree("7", ""), new DoubleTree("3", "")), new DoubleTree("4", "")));
		
		System.out.println(tree);
		
		Transform t = new Transform() {
			@Override
			public Tree apply(Tree tree) {
				System.out.print(tree.getName() + " ");
				return null;
			}};
		TopdownStrategy topDown = new TopdownStrategy(t);
		System.out.print("TopDown: ");
		new StrategicEngine(tree, topDown).engage();
		System.out.println();

		BottomupStrategy bottomup = new BottomupStrategy(t);
		System.out.print("BottomUp: ");
		new StrategicEngine(tree, bottomup).engage();
		System.out.println();

		InorderStrategy inorder = new InorderStrategy(new Transform() {

			@Override
			public Tree apply(Tree tree) {
				System.out.print("(");
				return null;
			}},
			t,
			new Transform() {

			@Override
			public Tree apply(Tree tree) {
				System.out.print(")");
				return null;
			}});
		System.out.print("InOrder: ");
		new StrategicEngine(tree, inorder).engage();
		System.out.println();
}
}
