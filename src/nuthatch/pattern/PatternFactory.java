package nuthatch.pattern;

import nuthatch.pattern.impl.AndPattern;
import nuthatch.pattern.impl.NodePattern;
import nuthatch.pattern.impl.NotPattern;
import nuthatch.pattern.impl.OrPattern;
import nuthatch.pattern.impl.TreePattern;
import nuthatch.pattern.impl.VarPattern;
import nuthatch.tree.Tree;

import org.eclipse.imp.pdb.facts.IValue;

public class PatternFactory {
	private static PatternFactory instance;


	public static PatternFactory getInstance() {
		if(instance == null)
			instance = new PatternFactory();
		return instance;
	}


	public Pattern nodeName(String name) {
		return node(name, null, null);
	}


	public Pattern nodeType(String type) {
		return node(null, type, null);
	}


	public Pattern nodeData(IValue data) {
		return node(null, null, data);
	}


	public Pattern children(Pattern... children) {
		return nodeWithChildren(null, null, null, children);
	}


	public Pattern node(String name, String type, IValue data) {
		return new NodePattern(name, type, data, null);
	}


	public Pattern nodeWithChildren(String name, Pattern... children) {
		return new NodePattern(name, null, null, children);
	}


	public Pattern nodeWithChildren(String name, String type, IValue data, Pattern... children) {
		return new NodePattern(name, type, data, children);
	}


	public Pattern tree(Tree tree) {
		return new TreePattern(tree);
	}


	public Pattern and(Pattern a, Pattern b) {
		return new AndPattern(a, b);
	}


	public Pattern or(Pattern a, Pattern b) {
		return new OrPattern(a, b);
	}


	public Pattern not(Pattern a) {
		return new NotPattern(a);
	}


	public Pattern var(String name) {
		return new VarPattern(name, null);
	}


	public Pattern var(String name, Pattern p) {
		return new AndPattern(p, new VarPattern(name, null));
	}
}
