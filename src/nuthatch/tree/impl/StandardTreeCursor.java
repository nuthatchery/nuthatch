package nuthatch.tree.impl;

import java.util.ArrayList;
import java.util.List;

import nuthatch.tree.Path;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;

public class StandardTreeCursor<Value, Type> implements TreeCursor<Value, Type> {
	private final List<Tree<Value, Type>> stack;
	private final Path path;
	private Tree<Value, Type> current;
	private int from = 0;


	public StandardTreeCursor(Tree<Value, Type> tree) {
		this.current = tree;
		this.stack = new ArrayList<Tree<Value, Type>>();
		this.path = new StandardPath();
	}


	private StandardTreeCursor(Tree<Value, Type> current, List<Tree<Value, Type>> stack, Path path) {
		this.current = current;
		this.stack = new ArrayList<Tree<Value, Type>>(stack);
		this.path = path.copy();
	}


	@Override
	public TreeCursor<Value, Type> copy() {
		return new StandardTreeCursor<Value, Type>(current, stack, path);
	}


	@Override
	public TreeCursor<Value, Type> getBranch(int i) {
		return copy().go(i);
	}


	@Override
	public Tree<Value, Type> getCurrentTree() {
		return current;
	}


	@Override
	public Value getData() {
		return current.getData();
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
	public String getName() {
		return current.getName();
	}


	@Override
	public String getNodeId() {
		return current.getNodeId();
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
	public Tree<Value, Type> getTreeAtBranch(int i) {
		int n = current.numChildren();
		if(i == -1) {
			i = n;
		}
		else if(i == n + 1) {
			i = 0;
		}

		if(i == 0) {
			if(stack.isEmpty()) {
				return null;
			}
			else {
				return stack.get(stack.size() - 1);
			}
		}
		else if(i > 0 && i <= n) {
			return current.getBranch(i);
		}
		else {
			throw new BranchNotFoundError("" + i);
		}
	}


	@Override
	public Type getType() {
		return current.getType();
	}


	@Override
	public StandardTreeCursor<Value, Type> go(int i) throws BranchNotFoundError {
		if(current == null) {
			throw new BranchNotFoundError("" + i);
		}

		int n = current.numChildren();
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
			current = current.getBranch(i);
			from = 0;
		}
		else {
			throw new BranchNotFoundError("" + i);
		}
		return this;

	}


	@Override
	public boolean hasBranch(int i) {
		if(i == 0 || i == numChildren() + 1) {
			return !isAtRoot();
		}
		else {
			return current.hasBranch(i);
		}
	}


	@Override
	public boolean isAtLeaf() {
		return current.isLeaf();
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
	public boolean matches(Tree<Value, Type> tree) {
		return current.equals(tree);
	}


	@Override
	public int numChildren() {
		return current.numChildren();
	}


	@Override
	public int getFromBranch() {
		return from;
	}

}
