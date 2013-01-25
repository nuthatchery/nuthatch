package nuthatch.tree.impl;

import java.util.ArrayList;
import java.util.List;

import nuthatch.tree.Path;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;

public abstract class AbstractTreeCursor<Value, Type, T> implements TreeCursor<Value, Type> {
	private final List<T> stack;
	private final Path path;
	private T current;
	private int from = 0;


	protected AbstractTreeCursor(T tree) {
		this.current = tree;
		this.stack = new ArrayList<T>();
		this.path = new StandardPath();
	}


	protected AbstractTreeCursor(AbstractTreeCursor<Value, Type, T> src) {
		this.current = src.current;
		this.stack = new ArrayList<T>(src.stack);
		this.path = src.path.copy();
	}


	@Override
	public TreeCursor<Value, Type> getBranch(int i) {
		return copy().go(i);
	}


	@Override
	public Tree<Value, Type> getCurrentTree() {
		throw new UnsupportedOperationException();
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
	public String getPathId() {
		return path.toString();
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
	public TreeCursor<Value, Type> go(int i) throws BranchNotFoundError {
		if(current == null) {
			throw new BranchNotFoundError("" + i);
		}

		int n = numChildren();
		if(i == -1) {
			i = n;
		}
		else if(i == n + 1) {
			i = 0;
		}

		if(i == 0) {
			if(!stack.isEmpty()) {
				current = stack.remove(stack.size() - 1);
				from = path.popElement();
			}
			else {
				current = null;
				from = 1;
			}
		}
		else if(i > 0 && i <= n) {
			stack.add(current);
			path.pushElement(i);
			current = getChild(i - 1);
			from = 0;
		}
		else {
			throw new BranchNotFoundError("" + i);
		}
		return this;

	}


	@Override
	public boolean hasBranch(int i) {
		int n = numChildren();
		if(i == 0 || i == n + 1) {
			return !isAtRoot();
		}
		else {
			return i > 0 && i <= n;
		}
	}


	/**
	 * Return the child at index i.
	 * 
	 * @param i
	 *            A zero-based child index
	 * @return The child at i
	 */
	protected abstract T getChild(int i);


	@Override
	public boolean isAtLeaf() {
		return numChildren() == 0;
	}


	@Override
	public boolean isAtRoot() {
		return stack.isEmpty();
	}


	@Override
	public boolean isAtTop() {
		return current == null;
	}


	@Override
	public int getFromBranch() {
		return from;
	}


	protected T getCurrent() {
		return current;
	}
}
