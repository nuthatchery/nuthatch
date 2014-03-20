package nuthatch.tree.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nuthatch.library.Action;
import nuthatch.tree.Path;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.TreeHandle;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.tree.util.BranchUtil;
import nuthatch.walker.errors.ReachedTop;

public abstract class AbstractTreeCursor<Value, Type, T> extends AbstractTreeHandle<Value, Type, T> implements TreeCursor<Value, Type> {
	private final List<T> stack;

	private final Path path;
	private int from = 0;


	protected AbstractTreeCursor(AbstractTreeCursor<Value, Type, T> src, boolean fullTree) {
		super(src.current);
		this.from = src.from;
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
		super(replacement);
		this.stack = new ArrayList<T>(src.stack);
		this.path = src.path.copy();
		this.from = src.from;
		if(from > getNumChildren()) {
			from = getNumChildren();
		}
	}


	protected AbstractTreeCursor(T tree) {
		super(tree);
		this.stack = new ArrayList<T>();
		this.stack.add(current);
		this.path = new StandardPath();
	}


	@Override
	public TreeCursor<Value, Type> getBranchCursor(int i) {
		return copy().go(i);
	}


	@Override
	public TreeHandle<Value, Type> getBranchHandle(int i) {
		TreeCursor<Value, Type> copy = copy();
		copy.go(i);
		return copy;
	}


	@Override
	public TreeCursor<Value, Type> getCursor() {
		return copySubtree();
	}


	@Override
	public int getFromBranch() {
		return from;
	}


	@Override
	public TreeHandle<Value, Type> getHandle() {
		return copySubtree();
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
	public int getPathLength() {
		return path.size();
	}


	@Override
	public TreeCursor<Value, Type> go(int i) throws BranchNotFoundError {
		assert current != null;

		if(i == Action.STAY) {
			return this;
		}

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
					// System.err.println("Needs patching: " + old);
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


	@Override
	public boolean isAtRoot() {
		return path.size() == 0;
	}


	@Override
	public boolean isAtTop() {
		return stack.isEmpty();
	}


	@Override
	public Iterator<TreeCursor<Value, Type>> iterator() {
		return new TreeCursorIterator<>(this);
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
	protected T getCurrent() {
		return current;
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


	static class TreeCursorIterator<Value, Type, T> implements Iterator<TreeCursor<Value, Type>> {
		private TreeCursor<Value, Type> subtreeCursor;
		private int i = 0; // the next child to visit
		private int numChildren;


		TreeCursorIterator(AbstractTreeCursor<Value, Type, T> cursor) {
			subtreeCursor = cursor.copySubtree();
			numChildren = cursor.getNumChildren();
			if(numChildren > 0) {
				this.subtreeCursor.go(1);
			}
		}


		@Override
		public boolean hasNext() {
			return numChildren > i;
		}


		@Override
		public TreeCursor<Value, Type> next() {
			while(!subtreeCursor.isAtRoot()) {
				subtreeCursor.go(0);
			}
			subtreeCursor.go(++i);
			return subtreeCursor;
		}


		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
