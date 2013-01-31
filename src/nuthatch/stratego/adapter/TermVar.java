package nuthatch.stratego.adapter;

import org.spoofax.interpreter.terms.IStrategoTerm;

import nuthatch.pattern.Environment;
import nuthatch.pattern.impl.Var;

public class TermVar extends Var<IStrategoTerm, Integer> {

	public TermVar(Environment<?> env) {
		super(env);
	}

	@Override
	public TermCursor get() {
		return (TermCursor) super.get();
	}
}
