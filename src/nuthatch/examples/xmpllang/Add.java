package nuthatch.examples.xmpllang;

public class Add extends Expr {

	public Add(Expr e1, Expr e2) {
		super("Add", Type.EXPR, new Expr[] { e1, e2 });

	}


	@Override
	protected Expr copy() {
		return new Add(getChild(0), getChild(1));
	}

}
