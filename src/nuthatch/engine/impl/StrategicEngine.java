package nuthatch.engine.impl;

import nuthatch.engine.Engine;
import nuthatch.engine.errors.ReachedTop;
import nuthatch.strategy.Strategy;
import nuthatch.strategy.Transform;
import nuthatch.tree.Path;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.tree.impl.StandardTreeCursor;

public class StrategicEngine<Value, Type> implements Engine<Value, Type> {
	protected static final int parent = 0;
	protected static final int first = 1;
	protected static final int last = -1;

	private final TreeCursor<Value, Type> rootCursor;
	private TreeCursor<Value, Type> current;
	private final Strategy strategy;


	public StrategicEngine(TreeCursor<Value, Type> cursor, Strategy strat) {
		this.rootCursor = cursor;
		this.current = null;
		this.strategy = strat;
	}


	public StrategicEngine(Tree<Value, Type> tree, Strategy strat) {
		this.rootCursor = new StandardTreeCursor<Value, Type>(tree);
		this.current = null;
		this.strategy = strat;
	}


	@Override
	public Tree<Value, Type> currentTree() {
		return current.getCurrentTree();
	}


	@Override
	public int depth() {
		return current.getPath().size();
	}


	@Override
	public void engage() {
		current = rootCursor.copy();
		try {
			while(!current.isAtTop()) {
				int go = strategy.visit(this);
				go(go);
			}
		}
		catch(ReachedTop e) {
		}
	}


	@Override
	public int from() {
		return current.getFromBranch();
	}


	@Override
	public boolean from(int i) {
		if(i == last) {
			return current.getFromBranch() == current.numChildren();
		}
		else {
			return current.getFromBranch() == i;
		}
	}


	@Override
	public boolean isLeaf() {
		return current.isAtLeaf();
	}


	@Override
	public boolean isRoot() {
		return current.isAtRoot();
	}


	@Override
	public void split() {
		@SuppressWarnings("unchecked")
		StrategicEngine<Value, Type> children[] = new StrategicEngine[current.numChildren()];

/*		int i = 0;
		for(Tree child : current.children()) {
			children[i++] = clone(child, top, strategy);
		}
*/	}


	@Override
	public void transform(Transform t) {
		t.apply(this);
	}


	@Override
	public TreeCursor<Value, Type> copy() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public TreeCursor<Value, Type> getBranch(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Value getData() {
		return current.getData();
	}


	@Override
	public String getName() {
		return current.getName();
	}


	@Override
	public String getNodeId() {
		return current.getNodeId();
	}


	@Override
	public Tree<Value, Type> getCurrentTree() {
		return current.getCurrentTree();
	}


	@Override
	public Path getPath() {
		return current.getPath();
	}


	@Override
	public int getPathElement(int i) {
		return current.getPathElement(i);
	}


	@Override
	public int getLastPathElement() {
		return current.getLastPathElement();
	}


	@Override
	public int getFromBranch() {
		return current.getFromBranch();
	}


	@Override
	public Tree<Value, Type> getTreeAtBranch(int i) {
		return current.getTreeAtBranch(i);
	}


	@Override
	public Type getType() {
		return current.getType();
	}


	@Override
	public TreeCursor<Value, Type> go(int i) throws BranchNotFoundError {
		return current.go(i);
	}


	@Override
	public boolean hasBranch(int i) {
		return current.hasBranch(i);
	}


	@Override
	public boolean isAtLeaf() {
		return current.isAtLeaf();
	}


	@Override
	public boolean isAtRoot() {
		return current.isAtRoot();
	}


	@Override
	public boolean isAtTop() {
		return current.isAtTop();
	}


	@Override
	public boolean matches(Tree<Value, Type> tree) {
		return current.matches(tree);
	}


	@Override
	public int numChildren() {
		return current.numChildren();
	}
}
