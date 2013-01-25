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
	public Type getType() {
		return getCurrent().getType();
	}


	@Override
	public boolean matches(Tree<Value, Type> tree) {
		return getCurrent().equals(tree);
	}


	@Override
	public int numChildren() {
		return getCurrent().numChildren();
	}


	@Override
	protected Tree<Value, Type> getChild(int i) {
		return getCurrent().getBranch(i + 1);
	}

}
