package nuthatch.examples;

import nuthatch.tree.impl.StringTree;

public class ExampleTree {
	public static final StringTree TREE = new StringTree("+", new StringTree("5"), new StringTree("*", new StringTree("+", new StringTree("7"), new StringTree("3")), new StringTree("4")));

}
