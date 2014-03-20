package nuthatch.tree.impl;

import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.TreeHandle;

public class StandardTreeCursor<Value, Type> extends AbstractTreeCursor<Value, Type, Tree<Value, Type>> {

	public StandardTreeCursor(Tree<Value, Type> tree) {
		super(tree);
	}


	private StandardTreeCursor(StandardTreeCursor<Value, Type> src, boolean fullTree) {
		super(src, fullTree);
	}


	private StandardTreeCursor(StandardTreeCursor<Value, Type> src, Tree<Value, Type> replacement) {
		super(src, replacement);
	}


	@Override
	public TreeCursor<Value, Type> copy() {
		return new StandardTreeCursor<Value, Type>(this, true);
	}


	@Override
	public TreeCursor<Value, Type> copyAndReplaceSubtree(TreeCursor<Value, Type> replacement) {
		if(replacement instanceof StandardTreeCursor) {
			return new StandardTreeCursor<>(this, ((StandardTreeCursor<Value, Type>) replacement).getCurrent());
		}
		else {
			throw new UnsupportedOperationException("Replacing with different cursor type");
		}
	}


	@Override
	public TreeCursor<Value, Type> copySubtree() {
		return new StandardTreeCursor<Value, Type>(this, false);
	}


	@Override
	public TreeHandle<Value, Type> getBranchHandle(int i) {
		return new StandardTreeHandle<Value, Type>(getCurrent().getBranch(i + 1));
	}


	@Override
	public TreeCursor<Value, Type> getCursor() {
		return copySubtree();
	}


	@Override
	public Value getData() {
		return getCurrent().getData();
	}


	@Override
	public TreeHandle<Value, Type> getHandle() {
		return new StandardTreeHandle<Value, Type>(getCurrent());
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


	@Override
	protected Tree<Value, Type> getChild(int i) {
		return getCurrent().getBranch(i + 1);
	}


	@Override
	protected Tree<Value, Type> replaceChild(Tree<Value, Type> node, Tree<Value, Type> child, int i) {
		int n = node.numChildren();
		@SuppressWarnings("unchecked")
		Tree<Value, Type> children[] = new Tree[n];
		int j = 0;
		for(Tree<Value, Type> c : node.children()) {
			if(i == j) {
				children[j++] = child;
			}
			else {
				children[j++] = c;
			}
		}
		return new StandardTree<Value, Type>(node.getName(), node.getType(), children);
	}

}
