package nuthatch.examples.xmpllang;

public abstract class Stat extends XmplNode {

	public Stat(String name, XmplNode[] children) {
		super(name, Type.STAT, children);
	}


	public Stat(String name, Type type, XmplNode[] children) {
		super(name, type, children);
	}


	@Override
	protected abstract Stat copy();

}
