package nuthatch.examples.xmpllang;

import java.util.Comparator;

public class Type {
	String typeName;
	public static final Type INT = new Type("int");
	public static final Type BOOL = new Type("bool");
	public static final Type VAR = new Type("var");
	public static final Type EXPR = new Type("expr");
	public static final Type STAT = new Type("stat");


	public static final Comparator<Type> isSubtype = new Comparator<Type>() {

		@Override
		public int compare(Type o1, Type o2) {
			return o1.isSubtypeOf(o2) ? 0 : 1;
		}
	};


	public Type(String name) {
		this.typeName = name;
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
		Type other = (Type) obj;
		if(typeName == null) {
			if(other.typeName != null) {
				return false;
			}
		}
		else if(!typeName.equals(other.typeName)) {
			return false;
		}
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}


	public boolean isSubtypeOf(Type t) {
		return t == this || (t == EXPR && (this == INT || this == BOOL || this == VAR));
	}


	@Override
	public String toString() {
		return typeName;
	}

}
