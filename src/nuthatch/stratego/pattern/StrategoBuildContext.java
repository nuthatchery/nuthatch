package nuthatch.stratego.pattern;

import nuthatch.pattern.BuildContext;
import nuthatch.tree.TreeCursor;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class StrategoBuildContext implements BuildContext<IStrategoTerm, Integer> {

	@Override
	public <T extends TreeCursor<IStrategoTerm, Integer>> T create(String name, Integer type, IStrategoTerm value, T[] children) {
		throw new UnsupportedOperationException("not yet implemented");
	}

}
