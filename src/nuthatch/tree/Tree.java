package nuthatch.tree;

import org.eclipse.imp.pdb.facts.IValue;

import nullness.Nullable;

public interface Tree {
	public static final int FIRST = 1;
	public static final int LAST = -1;
	public static final int PARENT = 0;
	
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
