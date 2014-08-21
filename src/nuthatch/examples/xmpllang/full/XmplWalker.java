package nuthatch.examples.xmpllang.full;

import nuthatch.examples.xmpllang.Type;
import nuthatch.examples.xmpllang.XmplNode;
import nuthatch.library.ActionFactory;
import nuthatch.library.FactoryFactory;
import nuthatch.library.Walk;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.walker.impl.AbstractWalker;

public class XmplWalker extends AbstractWalker<XmplNode, Type, XmplWalker> {
	public XmplWalker(TreeCursor<XmplNode, Type> cursor, Walk<XmplWalker> step) {
		super(cursor, step);
	}


	public XmplWalker(XmplNode expr, Walk<XmplWalker> step) {
		super(new XmplCursor(expr), step);
	}


	@Override
	public TreeCursor<XmplNode, Type> go(int i) throws BranchNotFoundError {
		return super.go(i);
	}


	public void replace(XmplNode e) {
		super.replace(new XmplCursor(e));
	}


	@Override
	protected XmplWalker subWalk(TreeCursor<XmplNode, Type> cursor, Walk<XmplWalker> step) {
		return new XmplWalker(cursor, step);
	}


	public static ActionFactory<XmplNode, Type, XmplCursor, XmplWalker> getActionFactory() {
		return FactoryFactory.getActionFactory();
	}
}
