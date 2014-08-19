package nuthatch.examples.exprlang;

public class Var extends Expr {

	private String name;


	public Var(String s) {
		super("Var", Type.VAR, new Expr[] {});
		name = s;
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!super.equals(obj)) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		Var other = (Var) obj;
		if(name == null) {
			if(other.name != null) {
				return false;
			}
		}
		else if(!name.equals(other.name)) {
			return false;
		}
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public String toString() {
		return String.valueOf(name);
	}


	@Override
	protected Expr copy() {
		return new Var(name);
	}

}
