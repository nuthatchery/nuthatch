package nuthatch.javafront;

import static nuthatch.javafront.JavaPatterns.AmbName;
import static nuthatch.javafront.JavaPatterns.Id;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.stratego.adapter.TermVar;
import nuthatch.tree.TreeCursor;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class JavaAdapter {

	public static String nameToStr(TreeCursor<IStrategoTerm, Integer> tree) {
		Environment<TreeCursor<IStrategoTerm, Integer>> env = EnvironmentFactory.env();
		TermVar s = new TermVar(env);
		TermVar t = new TermVar(env);
		if(Id(s).match(tree, env)) {
			return s.get().getName();
		}
		else if(AmbName(s, t).match(tree, env)) {
			return nameToStr(s.get()) + "." + nameToStr(t.get());
		}
		else {
			throw new RuntimeException("Unknown name: " + tree.treeToString());
		}
	}

}
