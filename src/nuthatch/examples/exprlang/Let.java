package nuthatch.examples.exprlang;

public class Let extends Expr {

	public Let(Var x, Expr e1, Expr e2) {
		super("Let", Type.APPLY, new Expr[] { x, e1, e2 });

	}


	@Override
	protected Expr copy() {
		return new Let((Var) getChild(0), getChild(1), getChild(2));
	}

}
