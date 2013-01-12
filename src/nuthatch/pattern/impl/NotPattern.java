package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;

public class NotPattern implements Pattern {

	private final Pattern a;


	public NotPattern(Pattern a) {
		if(a == null) {
			throw new IllegalArgumentException("Pattern must not be null");
		}

		this.a = a;
	}


	@Override
	public boolean match(Tree tree, Environment env) {
		env.begin();
		boolean result = !a.match(tree, env);
		env.rollback();
		return result;
	}

}
