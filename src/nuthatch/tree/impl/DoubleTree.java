package nuthatch.tree.impl;

import java.util.Arrays;

import nuthatch.tree.ModifiableTree;
import nuthatch.tree.Tree;
import nuthatch.tree.errors.BranchNotFoundError;

import org.eclipse.imp.pdb.facts.IValue;

public class DoubleTree implements ModifiableTree {
	private Tree[] branches;
	private final IValue data;
	private final String name;
	private final String type;


	public DoubleTree(IValue data) {
		this.name = null;
		this.type = null;
		this.data = data;
		this.branches = new Tree[1];
	}


	public DoubleTree(IValue data, ModifiableTree parent) {
		parent.addChild(this);
		this.branches = new Tree[] { parent };
		this.name = null;
		this.type = null;
		this.data = data;
	}


	public DoubleTree(String name, String type, ModifiableTree parent) {
		parent.addChild(this);
		this.branches = new Tree[] { parent };
		this.name = name;
		this.type = type;
		this.data = null;
	}


	public DoubleTree(String name, String type, ModifiableTree... children) {
		this.name = name;
		this.type = type;
		this.data = null;
		this.branches = new Tree[children.length + 1];
		branches[0] = null;
		for(int i = 0; i < children.length; i++) {
			children[i].setParent(this);
			branches[i + 1] = children[i];
		}
	}


	public DoubleTree(String name, String type, Tree... children) {
		this.name = name;
		this.type = type;
		this.data = null;
		this.branches = new Tree[children.length + 1];
		branches[0] = null;
		for(int i = 0; i < children.length; i++) {
			ModifiableTree copy = children[i].copy();
			copy.setParent(this);
			branches[i + 1] = copy;
		}
	}


	private DoubleTree(String name, String type, IValue data) {
		this.name = name;
		this.type = type;
		this.data = data;
		this.branches = new Tree[1];
	}


	@Override
	public void addChild(Tree tree) {
		if(branches == null) {
			branches = new Tree[] { tree };
		}
		else {
			branches = Arrays.copyOf(branches, branches.length + 1);
			branches[branches.length - 1] = tree;
		}
	}


	@Override
	public Iterable<Tree> children() {
		return Arrays.asList(branches);
	}


	@Override
	public ModifiableTree copy() {
		DoubleTree tree = new DoubleTree(name, type, data);
		if(branches != null) {
			tree.branches = new Tree[branches.length];
			for(int i = 1; i < branches.length; i++) {
				ModifiableTree copy = branches[i].copy();
				copy.setParent(tree);
				tree.branches[i] = copy;
			}
		}
		return tree;
	}


	@Override
	public Tree freeze() {
		return this;
	}


	@Override
	public Tree getBranch(int i) throws BranchNotFoundError {
		if(i < branches.length) {
			return branches[i];
		}
		else if(i == branches.length) {
			return branches[0];
		}
		else {
			throw new BranchNotFoundError(String.valueOf(i));
		}
	}


	@Override
	public int getBranch(Tree node) throws BranchNotFoundError {
		for(int i = 0; i < branches.length; i++) {
			if(branches[i] == node) {
				return i;
			}
		}
		throw new BranchNotFoundError(node.toString());
	}


	@Override
	public IValue getData() {
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
	public Tree getParent() {
		return branches[0];
	}


	@Override
	public String getType() {
		return type;
	}


	@Override
	public boolean hasParentLinks() {
		return true;
	}


	@Override
	public boolean isLeaf() {
		return branches.length == 1;
	}


	@Override
	public boolean isParent(int i) {
		return i == 0 || i == branches.length;
	}


	@Override
	public boolean isRoot() {
		return branches[0] == null;
	}


	@Override
	public int numChildren() {
		return branches.length - 1;
	}


	@Override
	public void setParent(Tree tree) {
		branches[0] = tree;
	}


	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if(name != null) {
			s.append(name);
			s.append("(");
			if(branches != null) {
				String comma = "";
				for(int i = 1; i < branches.length; i++) {
					if(i > 1) {
						s.append(comma);
					}
					s.append(branches[i].toString());
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
