package nuthatch.tree.impl;

import java.util.ArrayList;

import nuthatch.tree.Path;

public class StandardPath extends ArrayList<Integer> implements Path {
	private static final long serialVersionUID = 3231044019230326233L;


	public StandardPath() {
		super();
	}


	public StandardPath(StandardPath path) {
		super(path);
	}


	@Override
	public Path copy() {
		return new StandardPath(this);
	}


	@Override
	public int getElement(int i) {
		return get(i);
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
	public String toString() {
		StringBuilder b = new StringBuilder(size() * 2);
		b.append("T");
		for(Integer i : this) {
			b.append(".");
			b.append(i);
		}
		return b.toString();
	}

}
