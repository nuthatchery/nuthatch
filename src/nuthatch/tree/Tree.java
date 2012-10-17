package nuthatch.tree;

import org.eclipse.imp.pdb.facts.IValue;

import nullness.Nullable;

public interface Tree {
	@Nullable
	Tree getParent();
	
	Tree getChild(int i) throws ChildNotFoundError;
	
	int numChildren();
	
	Iterable<Tree> children();
	
	boolean isLeaf();
	
	boolean isRoot();
	
	IValue getData();
	
	String getName();
	
	String getType();

}
