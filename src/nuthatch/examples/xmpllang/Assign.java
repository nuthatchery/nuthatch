package nuthatch.examples.xmpllang;

public class Assign extends Stat {

	public Assign(Var v, Expr e) {
		super("Assign", new XmplNode[] { v, e });

	}


	@Override
	protected Stat copy() {
		return new Assign((Var) getChild(0), (Expr) getChild(1));
	}
}
