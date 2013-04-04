package nuthatch.benchmark.java;

import org.spoofax.interpreter.terms.IStrategoTerm;

import nuthatch.benchmark.Benchmark;

public class JavaTraverse extends Benchmark {

	private IStrategoTerm tree;

	public JavaTraverse(IStrategoTerm tree) {
		super("Traverse", "(Java)");
		this.tree = tree;
	}

	public JavaTraverse(int n, IStrategoTerm tree) {
		super("Traverse", n, "(Java)");
		this.tree = tree;
	}

	@Override
	protected void doIt() {
		traverse(tree);
	}

	private void traverse(IStrategoTerm tree) {
		int n = tree.getSubtermCount();

		for (int i = 0; i < n; i++) {
			traverse(tree.getSubterm(i));
		}
	}

	@Override
	protected boolean check() {
		return true;
	}

}
