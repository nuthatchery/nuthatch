package nuthatch.engine;

import nuthatch.tree.Tree;

public interface Transform {
	Tree apply(Tree tree);
}
