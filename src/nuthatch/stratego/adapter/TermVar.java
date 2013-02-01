package nuthatch.stratego.adapter;

import nuthatch.pattern.Environment;
import nuthatch.pattern.impl.Var;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class TermVar extends Var<IStrategoTerm, Integer> {

	public TermVar(Environment<?> env) {
		super(env);
	}

	@Override
	public TermCursor get() {
		return (TermCursor) super.get();
	}
}
