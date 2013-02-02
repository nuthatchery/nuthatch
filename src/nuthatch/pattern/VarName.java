package nuthatch.pattern;

public class VarName<T> {

	private final String name;


	public VarName() {
		this.name = ("$" + Integer.toHexString(System.identityHashCode(this))).intern();
	}


	public VarName(String name) {
		this.name = name.intern();
	}


	/**
	 * @return The name; guaranteed to be interned.
	 */
	public String getName() {
		return name;
	}
}
