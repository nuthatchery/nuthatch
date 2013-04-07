package nuthatch.examples.exprlang;

public class Type {
	String typeName;
	public static final Type INT = new Type("int");
	public static final Type BOOL = new Type("bool");


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


	@Override
	public String toString() {
		return typeName;
	}

}
