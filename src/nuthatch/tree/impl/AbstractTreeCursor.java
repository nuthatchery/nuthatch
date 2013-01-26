package nuthatch.tree.impl;

import java.util.ArrayList;
import java.util.List;

import nuthatch.engine.errors.ReachedTop;
import nuthatch.tree.Path;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.tree.util.BranchUtil;

public abstract class AbstractTreeCursor<Value, Type, T> implements TreeCursor<Value, Type> {
	private final List<T> stack;
	private final Path path;
	private T current;
	private int from = 0;


	protected AbstractTreeCursor(AbstractTreeCursor<Value, Type, T> src, boolean fullTree) {
		this.current = src.current;
		if(fullTree) {
			this.stack = new ArrayList<T>(src.stack);
			this.path = src.path.copy();
		}
		else {
			this.stack = new ArrayList<T>();
			this.stack.add(current);
			this.path = new StandardPath();
		}
	}


	protected AbstractTreeCursor(AbstractTreeCursor<Value, Type, T> src, T replacement) {
		this.current = replacement;
		this.stack = new ArrayList<T>(src.stack);
		this.path = src.path.copy();
	}


	protected AbstractTreeCursor(T tree) {
		this.current = tree;
		this.stack = new ArrayList<T>();
		this.stack.add(current);
		this.path = new StandardPath();
	}


	@Override
	public TreeCursor<Value, Type> getBranch(int i) {
		return copy().go(i);
	}


	@Override
	public int getFromBranch() {
		return from;
	}


	@Override
	public int getLastPathElement() {
		if(path.size() > 0) {
			return path.getElement(path.size() - 1);
		}
		else {
			return 1;
		}
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
	public TreeCursor<Value, Type> go(int i) throws BranchNotFoundError {
		assert current != null;

		if(i == 1 && stack.isEmpty()) { // we can go down from top
			stack.add(current);
			from = 0;
			return this;

		}

		i = BranchUtil.normalBranch(i, getNumChildren());

		if(i == 0) {
			if(path.size() != 0) {
				T old = current;
				current = stack.remove(stack.size() - 1);
				from = path.popElement();
				if(getChild(from - 1) != old) {
					current = replaceChild(current, old, from - 1);
					System.err.println("Needs patching: " + old);
				}
			}
			else if(!stack.isEmpty()) {
				stack.remove(stack.size() - 1);
				from = 1;
			}
			else {
				throw new ReachedTop();
			}
		}
		else if(i > 0) {
			stack.add(current);
			path.pushElement(i);
			current = getChild(i - 1);
			from = 0;
		}
		else {
			throw new BranchNotFoundError(String.valueOf(i));
		}
		return this;

	}


	/**
	 * Replace child 'i' of 'node' with 'child'.
	 * 
	 * @param node
	 *            Current node
	 * @param child
	 *            New child
	 * @param i
	 *            Child index, staring at 0
	 * @return New current node
	 */
	protected abstract T replaceChild(T node, T child, int i);


	@Override
	public boolean hasBranch(int i) {
		return BranchUtil.normalBranch(i, getNumChildren()) >= 0;
	}


	@Override
	public boolean isAtLeaf() {
		return getNumChildren() == 0;
	}


	@Override
	public boolean isAtRoot() {
		return path.size() == 0;
	}


	@Override
	public boolean isAtTop() {
		return stack.isEmpty();
	}


	/**
	 * Return the child at index i.
	 * 
	 * @param i
	 *            A zero-based child index
	 * @return The child at i
	 */
	protected abstract T getChild(int i);


	protected T getCurrent() {
		return current;
	}


	@Override
	public String treeToString() {
		return current.toString();
	}
}
