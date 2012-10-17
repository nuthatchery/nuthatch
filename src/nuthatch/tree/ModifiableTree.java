package nuthatch.tree;

public interface ModifiableTree extends Tree {
	void addChild(Tree tree);

	void setParent(Tree tree);
	
	Tree freeze();
}
