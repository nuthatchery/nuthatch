package nuthatch.tree.impl;

import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.TreeHandle;

public class StandardTreeHandle<Value, Type> extends AbstractTreeHandle<Value, Type, Tree<Value, Type>> {

	public StandardTreeHandle(Tree<Value, Type> tree) {
		super(tree);
	}


	@Override
	public TreeHandle<Value, Type> getBranchHandle(int i) {
		return new StandardTreeHandle<Value, Type>(getCurrent().getBranch(i + 1));

	}


	@Override
	public TreeCursor<Value, Type> getCursor() {
		return new StandardTreeCursor<Value, Type>(getCurrent());
	}


	@Override
	public Value getData() {
		return getCurrent().getData();
	}


	@Override
	public String getName() {
		return getCurrent().getName();
	}


	@Override
	public int getNumChildren() {
		return getCurrent().numChildren();
	}


	@Override
	public Type getType() {
		return getCurrent().getType();
	}


	@Override
	public boolean hasData() {
		return getData() != null;
	}


	@Override
	public boolean hasName() {
		return getName() != null;
	}


	@Override
	public boolean subtreeEquals(TreeHandle<Value, Type> other) {
		if(this == other) {
			return true;
		}
		else if(other == null) {
			return false;
		}
		else if(other instanceof StandardTreeHandle<?, ?>) {
			return getCurrent().equals(((StandardTreeHandle<?, ?>) other).getCurrent());
		}
		else if(other instanceof StandardTreeCursor<?, ?>) {
			return getCurrent().equals(((StandardTreeCursor<?, ?>) other).getCurrent());
		}
		else {
			throw new UnsupportedOperationException("Equality only supported on StandardTreeCursor");
		}
	}

}
