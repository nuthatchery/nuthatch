package nuthatch.engine.impl;

import java.util.Iterator;

import nuthatch.engine.Engine;
import nuthatch.engine.errors.ReachedTop;
import nuthatch.strategy.Strategy;
import nuthatch.tree.Path;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.tree.impl.StandardPath;
import nuthatch.tree.impl.StandardTreeCursor;

public abstract class StrategicEngine<Value, Type, E extends StrategicEngine<Value, Type, E>> implements Engine<Value, Type> {
	protected static final int parent = 0;
	protected static final int first = 1;
	protected static final int last = -1;

	private final TreeCursor<Value, Type> rootCursor;
	/**
	 * The current cursor. This will include at least a complete subtree at the
	 * current node, and
	 * up to replaceDepth (relative to original).
	 * 
	 * If replaceDepth == -1, then current == original
	 */
	private TreeCursor<Value, Type> current;
	private final Strategy<StrategicEngine<Value, Type, E>> strategy;

	private final Path path;


	public StrategicEngine(Tree<Value, Type> tree, Strategy<E> strat) {
		this.rootCursor = new StandardTreeCursor<>(tree);
		this.current = null;
		this.strategy = (Strategy<StrategicEngine<Value, Type, E>>) strat;
		this.path = new StandardPath();
	}


	public StrategicEngine(TreeCursor<Value, Type> cursor, Strategy<E> strat) {
		this.rootCursor = cursor;
		this.current = null;
		this.strategy = (Strategy<StrategicEngine<Value, Type, E>>) strat;
		this.path = new StandardPath();
	}


	@Override
	public TreeCursor<Value, Type> copy() {
		return current.copy();
	}


	@Override
	public TreeCursor<Value, Type> copySubtree() {
		return current.copySubtree();
	}


	@Override
	public TreeCursor<Value, Type> copyAndReplaceSubtree(TreeCursor<Value, Type> replacement) {
		return current.copyAndReplaceSubtree(replacement);
	}


	@Override
	public int depth() {
		return path.size();
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
	public TreeCursor<Value, Type> getBranch(int i) {
		return current.getBranch(i);
	}


	@Override
	public Value getData() {
		return current.getData();
	}


	@Override
	public int getFromBranch() {
		return current.getFromBranch();
	}


	@Override
	public int getLastPathElement() {
		return current.getLastPathElement();
	}


	@Override
	public String getName() {
		return current.getName();
	}


	@Override
	public int getNumChildren() {
		return current.getNumChildren();
	}


	@Override
	public Path getPath() {
		return path.copy();
	}


	@Override
	public int getPathElement(int i) {
		return path.getElement(i);
	}


	@Override
	public String getPathId() {
		return path.toString();
	}


	@Override
	public Type getType() {
		return current.getType();
	}


	@Override
	public TreeCursor<Value, Type> go(int i) throws BranchNotFoundError {
		current.go(i);
		return this;
	}


	@Override
	public boolean hasBranch(int i) {
		return current.hasBranch(i);
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
	public boolean hasName(String name) {
		return name.equals(current.getName());
	}


	@Override
	public boolean hasType(Type type) {
		return type.equals(current.getType());
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
	public boolean isLeaf() {
		return current.isAtLeaf();
	}


	@Override
	public boolean isRoot() {
		return current.isAtRoot();
	}


	@Override
	public void split() {
		// @SuppressWarnings("unchecked")
		// StrategicEngine<Value, Type> children[] = new StrategicEngine[current.getNumChildren()];

/*		int i = 0;
		for(Tree child : current.children()) {
			children[i++] = clone(child, top, strategy);
		}
 */	}


	@Override
	public boolean subtreeEquals(TreeCursor<Value, Type> other) {
		return current.subtreeEquals(other);
	}


	@Override
	public void replace(TreeCursor<Value, Type> tree) {
		current = current.copyAndReplaceSubtree(tree);
	}


	private boolean dataInvariant() {

		return true;
	}


	@Override
	public String treeToString() {
		if(current != null) {
			return current.treeToString();
		}
		else {
			return "<top>";
		}
	}


	@Override
	public Iterator<TreeCursor<Value, Type>> iterator() {
		return current.iterator();
	}

}
