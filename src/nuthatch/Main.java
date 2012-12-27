package nuthatch;

import nuthatch.engine.Transform;
import nuthatch.engine.impl.Bottomup;
import nuthatch.engine.impl.Inorder;
import nuthatch.engine.impl.Topdown;
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
		Topdown topDown = new Topdown(tree, t);
		topDown.engage();
		System.out.println();

		Bottomup bottomup = new Bottomup(tree, t);
		bottomup.engage();
		System.out.println();

		Inorder inorder = new Inorder(tree, t, new Transform() {

			@Override
			public Tree apply(Tree tree) {
				System.out.print("(");
				return null;
			}}, new Transform() {

			@Override
			public Tree apply(Tree tree) {
				System.out.print(")");
				return null;
			}});
		inorder.engage();
		System.out.println();
}
}
