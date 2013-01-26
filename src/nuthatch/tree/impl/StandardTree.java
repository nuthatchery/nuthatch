package nuthatch.tree.impl;

import java.util.Arrays;

import nuthatch.tree.ModifiableTree;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;

public class StandardTree<Value, Type> implements ModifiableTree<Value, Type> {
	private Tree<Value, Type>[] children;
	private final Value data;
	private final String name;
	private final Type type;


	@SuppressWarnings("unchecked")
	@SafeVarargs
	public StandardTree(String name, Type type, Tree<Value, Type>... children) {
		this.name = name;
		this.type = type;
		this.data = null;
		if(children.length > 0) {
			this.children = new Tree[children.length];
		}
		else {
			this.children = null;
		}
		for(int i = 0; i < children.length; i++) {
			ModifiableTree<Value, Type> copy = children[i].copy();
			this.children[i] = copy;
		}
	}


	public StandardTree(Value data) {
		this.name = null;
		this.type = null;
		this.data = data;
		this.children = null;
	}


	private StandardTree(String name, Type type, Value data) {
		this.name = name;
		this.type = type;
		this.data = data;
		this.children = null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void addChild(Tree<Value, Type> tree) {
		if(children == null) {
			children = new Tree[] { tree };
		}
		else {
			children = Arrays.copyOf(children, children.length + 1);
			children[children.length - 1] = tree;
		}
	}


	@Override
	public Iterable<Tree<Value, Type>> children() {
		return Arrays.asList(children);
	}


	@SuppressWarnings("unchecked")
	@Override
	public ModifiableTree<Value, Type> copy() {
		StandardTree<Value, Type> tree = new StandardTree<Value, Type>(name, type, data);
		if(children != null) {
			tree.children = new Tree[children.length];
			for(int i = 0; i < children.length; i++) {
				ModifiableTree<Value, Type> copy = children[i].copy();
				tree.children[i] = copy;
			}
		}
		return tree;
	}


	@Override
	public Tree<Value, Type> freeze() {
		return this;
	}


	@Override
	public Tree<Value, Type> getBranch(int i) throws BranchNotFoundError {
		if(i == -1) {
			i = children.length;
		}
		else if(i == children.length + 1) {
			i = 0;
		}

		if(i == 0) {
			throw new UnsupportedOperationException("Getting parent");
		}
		else if(i > 0 || i <= children.length) {
			return children[i - 1];
		}
		else {
			throw new BranchNotFoundError(String.valueOf(i));
		}
	}


	@Override
	public int getBranch(Tree<Value, Type> node) throws BranchNotFoundError {
		for(int i = 0; i < children.length; i++) {
			if(children[i] == node) {
				return i + 1;
			}
		}
		throw new BranchNotFoundError(node.toString());
	}


	@Override
	public Value getData() {
		return data;
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public String getNodeId() {
		return Integer.toHexString(super.hashCode());
	}


	@Override
	public Tree<Value, Type> getParent() {
		throw new UnsupportedOperationException("Getting parent");
	}


	@Override
	public Type getType() {
		return type;
	}


	@Override
	public boolean hasBranch(int i) {
		if(children != null) {
			return i >= -1 && i <= children.length + 1;
		}
		else {
			return i == 0;
		}
	}


	@Override
	public boolean hasParentLinks() {
		return false;
	}


	@Override
	public boolean isLeaf() {
		return children == null || children.length == 0;
	}


	@Override
	public boolean isParent(int i) {
		return i == 0 || i == children.length + 1;
	}


	@Override
	public boolean isRoot() {
		throw new UnsupportedOperationException("Getting parent");
	}


	public TreeCursor<Value, Type> makeCursor() {
		return new StandardTreeCursor<Value, Type>(this);
	}


	@Override
	public int numChildren() {
		if(children == null) {
			return 0;
		}
		else {
			return children.length;
		}
	}


	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if(name != null) {
			s.append(name);
			s.append("(");
			if(children != null) {
				String comma = "";
				for(int i = 0; i < children.length; i++) {
					if(i > 0) {
						s.append(comma);
					}
					s.append(children[i].toString());
					comma = ",";
				}
			}
			else {
				s.append("()");
			}
			s.append(")");
		}
		else if(data != null) {
			s.append(data.toString());
		}

		return s.toString();
	}

}
