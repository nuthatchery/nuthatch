package nuthatch.tree.impl;

import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;

public class StandardTreeCursor<Value, Type> extends AbstractTreeCursor<Value, Type, Tree<Value, Type>> {

	public StandardTreeCursor(Tree<Value, Type> tree) {
		super(tree);
	}


	private StandardTreeCursor(StandardTreeCursor<Value, Type> src) {
		super(src);
	}


	@Override
	public TreeCursor<Value, Type> copy() {
		return new StandardTreeCursor<Value, Type>(this);
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
	public boolean subtreeEquals(TreeCursor<Value, Type> other) {
		if(this == other) {
			return true;
		}
		else if(other == null) {
			return false;
		}
		else if(other instanceof StandardTreeCursor<?, ?>) {
			return getCurrent().equals(((StandardTreeCursor<?, ?>) other).getCurrent());
		}
		else {
			throw new UnsupportedOperationException("Equality only supported on StandardTreeCursor");
		}
	}


	@Override
	protected Tree<Value, Type> getChild(int i) {
		return getCurrent().getBranch(i + 1);
	}

}
