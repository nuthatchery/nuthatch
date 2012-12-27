package nuthatch.tree;

import org.eclipse.imp.pdb.facts.IValue;

import nullness.Nullable;

public interface Tree {
	@Nullable
	Tree getParent();
	
	Tree getBranch(int i) throws BranchNotFoundError;

	int getBranch(Tree node) throws BranchNotFoundError;
	
	int numChildren();
	
	Iterable<Tree> children();
	
	boolean isLeaf();
	
	boolean isRoot();
	
	IValue getData();
	
	String getName();
	
	String getType();

	ModifiableTree copy();

	boolean isParent(int i);

}
