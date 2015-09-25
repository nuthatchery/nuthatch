package nuthatch.examples.xmpllang;

public class Nop extends Stat {

	public Nop() {
		super("Nop", new XmplNode[] {});

	}


	@Override
	protected Stat copy() {
		return new Nop();
	}
}
