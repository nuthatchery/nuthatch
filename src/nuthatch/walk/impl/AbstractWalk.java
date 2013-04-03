package nuthatch.walk.impl;

import java.util.Iterator;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Path;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.tree.impl.StandardTreeCursor;
import nuthatch.walk.Step;
import nuthatch.walk.Walk;
import nuthatch.walk.errors.ReachedTop;

public abstract class AbstractWalk<Value, Type, E extends AbstractWalk<Value, Type, E>> implements Walk<Value, Type> {
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
	private final Step<AbstractWalk<Value, Type, E>> step;


	public AbstractWalk(Tree<Value, Type> tree, Step<E> step) {
		this.rootCursor = new StandardTreeCursor<>(tree);
		this.current = null;
		this.step = (Step<AbstractWalk<Value, Type, E>>) step;
	}


	public AbstractWalk(TreeCursor<Value, Type> cursor, Step<E> step) {
		this.rootCursor = cursor;
		this.current = null;
		this.step = (Step<AbstractWalk<Value, Type, E>>) step;
	}


	@Override
	public TreeCursor<Value, Type> copy() {
		return current.copy();
	}


	@Override
	public TreeCursor<Value, Type> copyAndReplaceSubtree(TreeCursor<Value, Type> replacement) {
		return current.copyAndReplaceSubtree(replacement);
	}


	@Override
	public TreeCursor<Value, Type> copySubtree() {
		return current.copySubtree();
	}


	private boolean dataInvariant() {

		return true;
	}


	@Override
	public int depth() {
		return current.getPathLength();
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
		return current.getPath();
	}


	@Override
	public int getPathElement(int i) {
		return current.getPathElement(i);
	}


	@Override
	public String getPathId() {
		return current.getPathId();
	}


	@Override
	public int getPathLength() {
		return current.getPathLength();
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
	public boolean isRunning() {
		return current != null && current.isAtTop();
	}


	@Override
	public Iterator<TreeCursor<Value, Type>> iterator() {
		return current.iterator();
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean match(Pattern<Value, Type> pat, Environment<? extends TreeCursor<Value, Type>> env) {
		return pat.match(current, (Environment<TreeCursor<Value, Type>>) env);
	}


	@Override
	public void nest() {
		// @SuppressWarnings("unchecked")
		// StrategicEngine<Value, Type> children[] = new StrategicEngine[current.getNumChildren()];

/*		int i = 0;
		for(Tree child : current.children()) {
			children[i++] = clone(child, top, strategy);
		}
 */	}


	@Override
	public void replace(TreeCursor<Value, Type> tree) {
		current = current.copyAndReplaceSubtree(tree);
	}


	@Override
	public void start() {
		current = rootCursor.copy();
		try {
			while(!current.isAtTop()) {
				int go = step.step(this);
				if(go == Step.NEXT) {
					go = from() + 1;
				}
				go(go);
			}
		}
		catch(ReachedTop e) {
		}
	}


	@Override
	public boolean subtreeEquals(TreeCursor<Value, Type> other) {
		return current.subtreeEquals(other);
	}


	protected abstract E subWalk(TreeCursor<Value, Type> cursor, Step<E> step);


	@Override
	public String treeToString() {
		if(current != null) {
			return current.treeToString();
		}
		else {
			return "<top>";
		}
	}


	public void walkSubtree(Step<E> step) {
		subWalk(copySubtree(), step).start();
	}


	public void walkSubtreeAndReplace(Step<E> step) {
		E walk = subWalk(copySubtree(), step);
		walk.start();
		replace(walk);
	}
}
