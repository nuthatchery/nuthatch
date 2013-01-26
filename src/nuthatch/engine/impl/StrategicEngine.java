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
	private final Strategy<Value, Type> strategy;


	public StrategicEngine(TreeCursor<Value, Type> cursor, Strategy<Value, Type> strat) {
		this.rootCursor = cursor;
		this.current = null;
		this.strategy = strat;
	}


	public StrategicEngine(Tree<Value, Type> tree, Strategy<Value, Type> strat) {
		this.rootCursor = new StandardTreeCursor<Value, Type>(tree);
		this.current = null;
		this.strategy = strat;
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
			return current.getFromBranch() == current.getNumChildren();
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
		StrategicEngine<Value, Type> children[] = new StrategicEngine[current.getNumChildren()];

/*		int i = 0;
		for(Tree child : current.children()) {
			children[i++] = clone(child, top, strategy);
		}
*/	}


	@Override
	public void transform(Transform<Value, Type> t) {
		t.apply(this);
	}


	@Override
	public TreeCursor<Value, Type> copy() {
		return current.copy();
	}


	@Override
	public TreeCursor<Value, Type> getBranch(int i) {
		return current.getBranch(i);
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
	public String getPathId() {
		return current.getPathId();
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
	public int getNumChildren() {
		return current.getNumChildren();
	}


	@Override
	public boolean hasData() {
		return current.hasData();
	}


	@Override
	public boolean hasName() {
		return current.hasName();
	}


	@Override
	public boolean subtreeEquals(TreeCursor<Value, Type> other) {
		return current.subtreeEquals(other);
	}
}
