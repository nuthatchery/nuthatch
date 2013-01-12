package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;

public class AndPattern implements Pattern {

	private final Pattern a;
	private final Pattern b;


	public AndPattern(Pattern a, Pattern b) {
		if(a == null || b == null) {
			throw new IllegalArgumentException("Patterns must not be null");
		}
		this.a = a;
		this.b = b;
	}


	@Override
	public boolean match(Tree tree, Environment env) {
		return a.match(tree, env) && b.match(tree, env);
	}

}
