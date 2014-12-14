package nuthatch.walker.impl;

import java.util.Iterator;

import nuthatch.library.Action;
import nuthatch.library.Walk;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Path;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.TreeHandle;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.tree.impl.StandardTreeCursor;
import nuthatch.tree.util.BranchUtil;
import nuthatch.walker.Walker;
import nuthatch.walker.errors.ReachedTop;

public abstract class AbstractWalker<Value, Type, W extends AbstractWalker<Value, Type, W>> implements Walker<Value, Type, W> {
	protected static final int parent = 0;
	protected static final int first = 1;
	protected static final int last = -1;
	protected  int direction = 1;

	private final TreeCursor<Value, Type> rootCursor;
	/**
	 * The current cursor. This will include at least a complete subtree at the
	 * current node, and
	 * up to replaceDepth (relative to original).
	 * 
	 * If replaceDepth == -1, then current == original
	 */
	private TreeCursor<Value, Type> current;
	private final Walk<W> step;
	private Environment<? extends TreeCursor<Value, Type>> localEnv = null;


	public AbstractWalker(Tree<Value, Type> tree, Walk<W> step) {
		this.rootCursor = new StandardTreeCursor<>(tree);
		this.current = null;
		this.step = step;
	}


	public AbstractWalker(TreeCursor<Value, Type> cursor, Walk<W> step) {
		this.rootCursor = cursor;
		this.current = null;
		this.step = step;
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
		if(i == LAST) {
			return current.getFromBranch() == current.getArity();
		}
		else {
			return current.getFromBranch() == i;
		}
	}


	@Override
	public int getArity() {
		return current.getArity();
	}


	@Override
	public TreeCursor<Value, Type> getBranchCursor(int i) {
		return current.getBranchCursor(i);
	}


	@Override
	public TreeHandle<Value, Type> getBranchHandle(int i) {
		return current.getBranchHandle(i);
	}


	@Override
	public TreeCursor<Value, Type> getCursor() {
		return current.getCursor();
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
	public TreeHandle<Value, Type> getHandle() {
		return current.getHandle();
	}


	@Override
	public int getLastPathElement() {
		return current.getLastPathElement();
	}


	@Override
	public Environment<? extends TreeCursor<Value, Type>> getLocalEnv() {
		if(localEnv == null) {
			localEnv = EnvironmentFactory.env();
		}
		return localEnv;
	}


	@Override
	public String getName() {
		return current.getName();
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
	public TreeCursor<Value, Type> go(int... path) throws BranchNotFoundError {
		current.go(path);
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
	public boolean isReversed() {
		return direction == -1;
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
	public boolean match(Pattern<Value, Type> pat) {
		return pat.match(current, (Environment<TreeCursor<Value, Type>>) getLocalEnv());
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
	public int next(int i) {
		int r = i;
		if(i == Action.NEXT || i == Action.PROCEED) {
			r = from() + direction;
		}
		else if(i == Action.LAST) {
			r = getArity();
		}

		r = BranchUtil.normalBranch(r, getArity());
		return r;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void replace(Pattern<Value, Type> pattern) throws NotBuildableException {
		TreeCursor<Value, Type> build = pattern.build(null, (Environment<TreeCursor<Value, Type>>) getLocalEnv());
		current = current.copyAndReplaceSubtree(build);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void replace(Pattern<Value, Type> pattern, Environment<? extends TreeCursor<Value, Type>> env) throws NotBuildableException {
		TreeCursor<Value, Type> build = pattern.build(null, (Environment<TreeCursor<Value, Type>>) env);
		current = current.copyAndReplaceSubtree(build);
	}


	@Override
	public void replace(TreeCursor<Value, Type> tree) {
		current = current.copyAndReplaceSubtree(tree);
	}


	@Override
	public void reverse() {
		direction = -direction;
	}

	@Override
	public void start() {
		step.init((W) this);
		current = rootCursor.copy();
		try {
			while(!current.isAtTop()) {
				localEnv = null;
				int go = step.step((W) this);
				if(go == Action.NEXT) {
					go = from() + direction;
				}
				go(go);
			}
		}
		catch(ReachedTop e) {
		}
	}

	@Override
	public boolean subtreeEquals(TreeHandle<Value, Type> other) {
		return current.subtreeEquals(other);
	}


	@Override
	public W subWalker() {
		return subWalk(current, step);
	}


	@Override
	public W subWalker(TreeCursor<Value, Type> tree) {
		return subWalk(tree, step);
	}

	@Override
	public W subWalker(Walk<W> step) {
		return subWalk(current, step);
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
	public void walkSubtree(Walk<W> step) {
		subWalk(copySubtree(), step).start();
	}

	@Override
	public void walkSubtreeAndReplace(Walk<W> step) {
		W walk = subWalk(copySubtree(), step);
		walk.start();
		replace(walk);
	}


	private boolean dataInvariant() {

		return true;
	}

	protected abstract W subWalk(TreeCursor<Value, Type> cursor, Walk<W> step);
}
