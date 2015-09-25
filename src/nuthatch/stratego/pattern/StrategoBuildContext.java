package nuthatch.stratego.pattern;

import nuthatch.pattern.BuildContext;
import nuthatch.stratego.adapter.STermCursor;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class StrategoBuildContext implements BuildContext<IStrategoTerm, Integer, STermCursor> {

	@Override
	public STermCursor create(String name, Integer type, IStrategoTerm value, STermCursor[] children) {
		throw new UnsupportedOperationException("not yet implemented");
	}

}
