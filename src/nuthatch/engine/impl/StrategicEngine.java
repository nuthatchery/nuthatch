package nuthatch.engine.impl;

import nuthatch.engine.Engine;
import nuthatch.engine.Strategy;
import nuthatch.engine.Transform;
import nuthatch.tree.Tree;

public class StrategicEngine implements Engine {
	protected static final int parent = 0;
	protected static final int first = 1;
	protected static final int last = -1;
	
	private final Tree top;
	private final Tree root;
	private Tree current;
	private final Strategy strategy;
	private int from = 0;
	
	public StrategicEngine(Tree tree, Strategy strat) {
		this.root = tree;
		this.current = null;
		this.strategy = strat;
		this.top = null;
	}
	
	protected StrategicEngine(Tree tree, Tree top, Strategy strat) {
		this.root = tree;
		this.current = null;
		this.strategy = strat;
		this.top = top;
	}

	@Override
	public void engage() {
		current = root;
		try {
		while(current != top) {
			int go = strategy.visit(this);
			go(go);
		}
		}
		catch(ReachedTop e) {
		}
	}
	
	@Override
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
	
	@Override
	public int from() {
		return from;
	}
	
	@Override
	public boolean from(int i) {
		if(i == last)
			return from == current.numChildren();
		else
			return from == i;
	}
	
	@Override
	public boolean isLeaf() {
		return current.isLeaf();
	}

	@Override
	public void split() {
		StrategicEngine children[] = new StrategicEngine[current.numChildren()];

		int i = 0;
		for(Tree child : current.children()) {
			children[i++] = clone(child, top, strategy);
		}
	}
	
	protected StrategicEngine clone(Tree newRoot, Tree newTop, Strategy strat) {
		return new StrategicEngine(newRoot, newTop, strat);
	}
}
