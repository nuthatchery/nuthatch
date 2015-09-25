package nuthatch.examples.xmpllang.full;

import nuthatch.examples.xmpllang.Type;
import nuthatch.examples.xmpllang.XmplNode;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.TreeHandle;
import nuthatch.tree.impl.AbstractTreeCursor;

public class XmplCursor extends AbstractTreeCursor<XmplNode, Type, XmplNode> {
	public XmplCursor(XmplNode tree) {
		super(tree);
	}


	protected XmplCursor(XmplCursor src, boolean fullTree) {
		super(src, fullTree);
	}


	protected XmplCursor(XmplCursor src, XmplNode replacement) {
		super(src, replacement);
	}


	@Override
	public XmplCursor copy() {
		return new XmplCursor(this, true);
	}


	@Override
	public XmplCursor copyAndReplaceSubtree(TreeCursor<XmplNode, Type> replacement) {
		return new XmplCursor(this, replacement.getData());
	}


	@Override
	public XmplCursor copySubtree() {
		return new XmplCursor(this, false);
	}


	@Override
	public int getArity() {
		return getCurrent().arity();
	}


	@Override
	public TreeHandle<XmplNode, Type> getBranchHandle(int i) {
		XmplCursor copy = copy();
		copy.go(i);
		return copy;
	}


	@Override
	public TreeCursor<XmplNode, Type> getCursor() {
		return copySubtree();
	}


	@Override
	public XmplNode getData() {
		return getCurrent();
	}


	@Override
	public TreeHandle<XmplNode, Type> getHandle() {
		return copySubtree();
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
	public boolean hasData() {
		return true;
	}


	@Override
	public boolean hasName() {
		return true;
	}


	@Override
	public boolean subtreeEquals(TreeHandle<XmplNode, Type> other) {
		return getCurrent().equals(other.getData());
	}


	@Override
	protected XmplNode getChild(int i) {
		return getCurrent().getChild(i);
	}


	@Override
	protected XmplNode replaceChild(XmplNode node, XmplNode child, int i) {
		return node.replace(i, child);
	}

}
