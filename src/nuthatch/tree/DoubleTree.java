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
		this.name = name;
		this.type = type;
		this.data = null;
		if(children.length > 0) {
			this.children = Arrays.copyOf(children, children.length);
			for(ModifiableTree t : children)
				t.setParent(this);
		}
	}

	public DoubleTree(String name, String type, Tree... children) {
		this.name = name;
		this.type = type;
		this.data = null;
		if(children.length > 0) {
			this.children = new Tree[children.length];
			int i = 0;
			for(Tree t : children) {
				ModifiableTree copy = t.copy();
				copy.setParent(this);
				this.children[i++] = copy;
			}
		}
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

	private DoubleTree(String name, String type, IValue data) {
		this.name = name;
		this.type = type;
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
			return children[i-1];
		else throw new ChildNotFoundError();
	}

	@Override
	public int numChildren() {
		return children == null ? 0 : children.length;
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

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if(name != null) {
			s.append(name);
			s.append("(");
			if(children != null) {
				String comma = "";
				for(Tree t : children) {
					s.append(comma);
					s.append(t.toString());
					comma = ",";
				}
			}
			else {
				s.append("()");
			}
			s.append(")");
		}
		else if(data !=  null) {
			s.append(data.toString());
		}

		return s.toString();
	}

	@Override
	public ModifiableTree copy() {
		DoubleTree tree = new DoubleTree(name, type, data);
		if(children != null) {
		tree.children = new Tree[children.length];
		int i = 0;
		for(Tree child : children) {
			ModifiableTree copy = child.copy();
			copy.setParent(tree);
			tree.children[i++] = copy;
		}
		}
		tree.parent = parent;
		return tree;
	}
}
