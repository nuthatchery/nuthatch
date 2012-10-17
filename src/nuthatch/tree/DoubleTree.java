package nuthatch.tree;

import java.util.Arrays;

import org.eclipse.imp.pdb.facts.IValue;

public class DoubleTree implements ModifiableTree {
	private Tree parent = null;
	private Tree[] children = null;
	private final IValue data;
	private final String name;
	private final String type;

	public DoubleTree(String name, String type, ModifiableTree parent) {
		parent.addChild(this);
		this.parent = parent;
		this.name = name;
		this.type = type;
		this.data = null;
	}
	
	public DoubleTree(String name, String type, ModifiableTree... children) {
		this.children = Arrays.copyOf(children, children.length);
		this.name = name;
		this.type = type;
		this.data = null;
		for(ModifiableTree t : children)
			t.setParent(this);
	}

	public DoubleTree(IValue data, ModifiableTree parent) {
		parent.addChild(this);
		this.parent = parent;
		this.name = null;
		this.type = null;
		this.data = data;
	}
	
	public DoubleTree(IValue data) {
		this.name = null;
		this.type = null;
		this.data = data;
	}

	@Override
	public Tree getParent() {
		return parent;
	}

	@Override
	public Tree getChild(int i) throws ChildNotFoundError {
		if(i == 0)
			return parent;
		else if(i <= children.length)
			return children[i+1];
		else throw new ChildNotFoundError();
	}

	@Override
	public int numChildren() {
		return children.length;
	}

	@Override
	public Iterable<Tree> children() {
		return Arrays.asList(children);
	}

	@Override
	public boolean isLeaf() {
		return children == null;
	}

	@Override
	public boolean isRoot() {
		return parent == null;
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
	public String getType() {
		return type;
	}
	@Override
	public void addChild(Tree tree) {
		if(children == null)
			children = new Tree[]{tree};
		else {
			children = Arrays.copyOf(children, children.length + 1);
			children[children.length-1] = tree;
		}
	}

	@Override
	public void setParent(Tree tree) {
		parent = tree;
	}

	@Override
	public Tree freeze() {
		return this;
	}
}
