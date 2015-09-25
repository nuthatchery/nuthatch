package nuthatch.examples.xmpllang;

public class If extends Stat {

	public If(Expr c, Stat s1, Stat s2) {
		super("If", new XmplNode[] { c, s1, s2 });

	}


	@Override
	protected Stat copy() {
		return new If((Expr) getChild(0), (Stat) getChild(1), (Stat) getChild(2));
	}

}
