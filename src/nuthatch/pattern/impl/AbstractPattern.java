package nuthatch.pattern.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.Pattern;
import nuthatch.tree.Tree;

public abstract class AbstractPattern implements Pattern {

	protected abstract boolean doMatch(Tree tree, Environment env);


	@Override
	public final boolean match(Tree tree, Environment env) {
		env.begin();
		if(doMatch(tree, env)) {
			env.commit();
			return true;
		}
		else {
			env.rollback();
			return false;
		}
	}

}
