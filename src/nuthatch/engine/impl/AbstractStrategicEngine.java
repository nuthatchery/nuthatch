package nuthatch.engine.impl;

import nuthatch.engine.Transform;
import nuthatch.tree.Tree;

public abstract class AbstractStrategicEngine {
	protected static final int parent = 0;
	protected static final int first = 1;
	protected static final int last = -1;
	
	private final Tree top;
	private Tree root;
	private Tree current;
	private Transform transform;
	private int from = 0;
	
	public AbstractStrategicEngine(Tree tree, Transform transform) {
		this.root = tree;
		this.current = null;
		this.transform = transform;
		this.top = null;
	}
	
	protected AbstractStrategicEngine(Tree tree, Tree top, Transform transform) {
		this.root = tree;
		this.current = null;
		this.transform = transform;
		this.top = top;
	}

	protected abstract AbstractStrategicEngine clone(Tree tree, Tree top);
	
	public int visit() {
		current = null;
		return 0;
	}
	
	public void engage() {
		current = root;
		try {
		while(current != top) {
			int go = visit();
			go(go);
		}
		}
		catch(ReachedTop e) {
		}
	}
	
	public void transform() {
		transform.apply(current);
	}

	public void transform(Transform t) {
		t.apply(current);
	}

	private void go(int i) {
		Tree old = current;
		if(i == last)
			i = current.numChildren();
		current = current.getBranch(i);
		if(current == null) {
			from = 0;
			throw new ReachedTop();
		}
		else if(old.isParent(i)) {
			from = current.getBranch(old); 
		}
		else {
			from = 0;
		}
	}
	
	public int from() {
		return from;
	}
	
	public boolean from(int i) {
		if(i == last)
			return from == current.numChildren();
		else
			return from == i;
	}
	
	protected boolean isLeaf() {
		return current.isLeaf();
	}

	public void split() {
		AbstractStrategicEngine children[] = new AbstractStrategicEngine[current.numChildren()];

		int i = 0;
		for(Tree child : current.children()) {
			children[i++] = clone(child, current);
		}
	}
	
	protected Transform getTransform() {
		return transform;
	}
}
