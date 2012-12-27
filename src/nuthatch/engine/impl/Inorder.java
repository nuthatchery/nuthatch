package nuthatch.engine.impl;

import nuthatch.engine.Transform;
import nuthatch.tree.Tree;

public class Inorder extends AbstractStrategicEngine {
	private final Transform pre;
	private final Transform post;

	public Inorder(Tree tree, Transform transform, Transform pre, Transform post) {
		super(tree, transform);
		this.pre = pre;
		this.post = post;
	}

	protected Inorder(Tree tree, Tree top, Transform transform, Transform pre, Transform post) {
		super(tree, top, transform);
		this.pre = pre;
		this.post = post;
	}

	@Override
	public int visit() {
		if(isLeaf() || from(first))
			transform();
		else if(from(parent))
			transform(pre);
		else if(from(last))
			transform(post);
		return from() + 1;
	}
	
	@Override
	protected Inorder clone(Tree tree, Tree top) {
		return new Inorder(tree, top, getTransform(), pre, post);
	}

}
