package nuthatch.examples.xmpllang;

public class Seq extends Stat {

	public Seq(Stat... ss) {
		super("Seq", ss);

	}


	@Override
	protected Stat copy() {
		return new Seq((Stat[]) children);
	}

}
