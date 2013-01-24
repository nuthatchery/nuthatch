package nuthatch.tree.impl;

import java.util.ArrayList;

import nuthatch.tree.Path;

@SuppressWarnings("serial")
public class StandardPath extends ArrayList<Integer> implements Path {

	public StandardPath() {
		super();
	}


	public StandardPath(StandardPath path) {
		super(path);
	}


	@Override
	public int popElement() {
		return remove(size() - 1);
	}


	@Override
	public void pushElement(int i) {
		add(i);
	}


	@Override
	public int getElement(int i) {
		return get(i);
	}


	@Override
	public Path copy() {
		return new StandardPath(this);
	}

}
