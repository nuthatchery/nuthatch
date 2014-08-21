package nuthatch.examples.xmpllang;

import java.util.Arrays;

public abstract class XmplNode {

	protected String name;
	protected XmplNode[] children;
	protected Type type;


	public XmplNode(String name, Type type, XmplNode[] children) {
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
		XmplNode other = (XmplNode) obj;
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


	public XmplNode getChild(int i) {
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


	public XmplNode replace(int i, XmplNode child) {
		XmplNode e = copy();
		e.children[i] = child;
		return e;
	}


	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(name);
		b.append("(");
		boolean first = true;
		for(XmplNode e : children) {
			if(!first) {
				b.append(", ");
			}
			b.append(e.toString());
			first = false;
		}
		b.append(")");
		return b.toString();
	}


	protected abstract XmplNode copy();

}
