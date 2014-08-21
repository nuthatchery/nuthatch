package nuthatch.examples.xmpllang;

public class While extends Stat {

	public While(Expr c, Stat ss) {
		super("While", new XmplNode[] { c, ss });

	}


	@Override
	protected Stat copy() {
		return new While((Expr) getChild(0), (Stat) getChild(1));
	}

}
