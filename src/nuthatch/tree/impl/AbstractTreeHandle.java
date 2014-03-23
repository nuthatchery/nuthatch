package nuthatch.tree.impl;

import nuthatch.tree.TreeHandle;
import nuthatch.tree.util.BranchUtil;

public abstract class AbstractTreeHandle<Value, Type, T> implements TreeHandle<Value, Type> {
	T current;


	protected AbstractTreeHandle(T tree) {
		this.current = tree;
	}


	@Override
	public boolean hasBranch(int i) {
		return BranchUtil.normalBranch(i, getNumChildren()) >= 0;
	}


	@Override
	public boolean hasName(String name) {
		return name.equals(getName());
	}


	@Override
	public boolean hasType(Type type) {
		return type.equals(getType());
	}


	@Override
	public boolean isAtLeaf() {
		return getNumChildren() == 0;
	}


	@Override
	public String treeToString() {
		return current.toString();
	}


	protected T getCurrent() {
		return current;
	}

}
