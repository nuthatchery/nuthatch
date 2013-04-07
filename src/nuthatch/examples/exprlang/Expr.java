package nuthatch.examples.exprlang;

import java.util.Arrays;

public abstract class Expr {
	private String name;
	private Expr[] children;
	private Type type;


	protected Expr(String name, Type type, Expr[] children) {
		this.name = name;
		this.type = type;
		this.children = children.clone();
	}


	public int arity() {
		return children.length;
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		Expr other = (Expr) obj;
		if(!Arrays.equals(children, other.children)) {
			return false;
		}
		if(name == null) {
			if(other.name != null) {
				return false;
			}
		}
		else if(!name.equals(other.name)) {
			return false;
		}
		if(type == null) {
			if(other.type != null) {
				return false;
			}
		}
		else if(!type.equals(other.type)) {
			return false;
		}
		return true;
	}


	public Expr getChild(int i) {
		return children[i];
	}


	public String getName() {
		return name;
	}


	public Type getType() {
		return type;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(children);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	public Expr replace(int i, Expr child) {
		Expr e = copy();
		e.children[i] = child;
		return e;
	}


	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(name);
		b.append("(");
		boolean first = true;
		for(Expr e : children) {
			if(!first) {
				b.append(", ");
			}
			b.append(e.toString());
			first = false;
		}
		b.append(")");
		return b.toString();
	}


	protected abstract Expr copy();
}
