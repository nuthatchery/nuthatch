package nuthatch.engine;

import nuthatch.tree.Tree;

public interface Engine {
	void engage();
	
	void transform(Transform t);

	Tree getCurrent();

	int from();
	
	boolean from(int i);
	
	boolean isLeaf();

	void split();

	boolean isRoot();
}
