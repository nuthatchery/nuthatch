package nuthatch.examples.exprlang;

public class Mul extends Expr {

	public Mul(Expr e1, Expr e2) {
		super("Mul", Type.INT, new Expr[] { e1, e2 });

	}


	@Override
	protected Expr copy() {
		return new Mul(getChild(0), getChild(1));
	}

}
