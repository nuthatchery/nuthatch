package nuthatch.examples.xmpllang;

public class Declare extends Stat {

	public Declare(Var x, Expr e, Stat s) {
		super("Let", new XmplNode[] { x, e, s });

	}


	@Override
	protected Stat copy() {
		return new Declare((Var) getChild(0), (Expr) getChild(1), (Stat) getChild(2));
	}

}
