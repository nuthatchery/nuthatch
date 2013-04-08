package nuthatch.benchmark.java;

import nuthatch.benchmark.Benchmark;
import nuthatch.stratego.adapter.StrategoAdapter;

import org.spoofax.interpreter.terms.IStrategoTerm;

/**
 * There is actually no sensible way to code this in Java – not really any point in traversing a tree just to build the value 42.
 *
 */
public class JavaBottomupBuild extends Benchmark {

	private IStrategoTerm inputTree;
	private IStrategoTerm result;

	public JavaBottomupBuild(int n, IStrategoTerm tree) {
		super("BottomupBuild", n, "(Java)");
		this.inputTree = tree;
	}

	public JavaBottomupBuild(IStrategoTerm tree) {
		super("BottomupBuild", "(Java)");
		this.inputTree = tree;
	}

	public IStrategoTerm getResult() {
		return result;
	}

	private IStrategoTerm traverse(IStrategoTerm tree) {
		int n = tree.getSubtermCount();

		for (int i = 0; i < n; i++) {
			traverse(tree.getSubterm(i));
		}

		tree = StrategoAdapter.getTermFactory().makeInt(42);

		return tree;
	}

	@Override
	protected boolean check() {
		if (result != null) {
			return result.equals(StrategoAdapter.getTermFactory().makeInt(42));
		} else {
			return true;
		}
	}

	@Override
	protected void doIt() {
		result = traverse(inputTree);
	}
}
