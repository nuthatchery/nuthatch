package nuthatch.examples.exprlang;

import nuthatch.tree.TreeCursor;
import nuthatch.tree.impl.AbstractTreeCursor;

public class ExprCursor extends AbstractTreeCursor<Expr, Type, Expr> {
	public ExprCursor(Expr tree) {
		super(tree);
	}


	protected ExprCursor(ExprCursor src, boolean fullTree) {
		super(src, fullTree);
	}


	protected ExprCursor(ExprCursor src, Expr replacement) {
		super(src, replacement);
	}


	@Override
	public ExprCursor copy() {
		return new ExprCursor(this, true);
	}


	@Override
	public ExprCursor copyAndReplaceSubtree(TreeCursor<Expr, Type> replacement) {
		return new ExprCursor(this, replacement.getData());
	}


	@Override
	public ExprCursor copySubtree() {
		return new ExprCursor(this, false);
	}


	@Override
	public Expr getData() {
		return getCurrent();
	}


	@Override
	public String getName() {
		return getCurrent().getName();
	}


	@Override
	public int getNumChildren() {
		return getCurrent().arity();
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
	public boolean subtreeEquals(TreeCursor<Expr, Type> other) {
		return getCurrent().equals(other.getData());
	}


	@Override
	protected Expr getChild(int i) {
		return getCurrent().getChild(i);
	}


	@Override
	protected Expr replaceChild(Expr node, Expr child, int i) {
		return node.replace(i, child);
	}

}
