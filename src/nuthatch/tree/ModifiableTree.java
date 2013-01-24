package nuthatch.tree;

public interface ModifiableTree<Value, Type> extends Tree<Value, Type> {
	void addChild(Tree<Value, Type> tree);


	Tree<Value, Type> freeze();
}
