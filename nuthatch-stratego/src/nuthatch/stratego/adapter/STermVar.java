package nuthatch.stratego.adapter;

import nuthatch.pattern.impl.Var;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class STermVar extends Var<IStrategoTerm, Integer> {

	public STermVar(String name) {
		super(name);
	}


	@Override
	public STermCursor get() {
		return (STermCursor) super.get();
	}
}
