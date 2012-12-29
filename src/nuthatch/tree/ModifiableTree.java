package nuthatch.tree;

public interface ModifiableTree extends Tree {
	void addChild(Tree tree);


	Tree freeze();


	void setParent(Tree tree);
}
