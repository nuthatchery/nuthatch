package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;

import org.eclipse.imp.pdb.facts.IValue;

public class NodePattern extends AbstractPattern {

	private final String name;
	private final String type;
	private final IValue data;
	private final Pattern[] children;


	public NodePattern(String name, String type, IValue data, Pattern[] children) {
		this.name = name;
		this.type = type;
		this.data = data;
		if(children != null) {
			this.children = children.clone();
		}
		else {
			this.children = null;
		}
	}


	@Override
	public boolean doMatch(Tree tree, Environment env) {
		if(name != null && !name.equals(tree.getName()))
			return false;
		if(type != null && !type.equals(tree.getType()))
			return false;
		if(data != null && !data.isEqual(tree.getData()))
			return false;
		if(children != null) {
			if(tree.numChildren() != children.length)
				return false;
			for(int i = 0; i < children.length; i++) {
				if(!children[i].match(tree.getBranch(i + 1), env))
					return false;
			}
		}
		return true;
	}
}
