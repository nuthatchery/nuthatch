package nuthatch.examples.xmpllang;

public abstract class Expr extends XmplNode {
	protected Expr(String name, Type type, Expr[] children) {
		super(name, type, children);
	}


	@Override
	public Expr getChild(int i) {
		return (Expr) children[i];
	}


	@Override
	public Expr replace(int i, XmplNode child) {
		assert child instanceof Expr;
		Expr e = copy();
		e.children[i] = child;
		return e;
	}


	@Override
	protected abstract Expr copy();

}
