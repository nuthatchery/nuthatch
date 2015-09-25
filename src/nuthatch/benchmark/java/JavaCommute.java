package nuthatch.benchmark.java;

import nuthatch.benchmark.Benchmark;
import nuthatch.stratego.adapter.StrategoAdapter;

import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class JavaCommute extends Benchmark {

	private IStrategoTerm inputTree;
	private IStrategoTerm result;
	private IStrategoTerm ref;

	public JavaCommute(int n, IStrategoTerm tree, IStrategoTerm ref) {
		super("Commute", n, "(Java)");
		this.inputTree = tree;
		this.ref = ref;
	}

	public JavaCommute(IStrategoTerm tree, IStrategoTerm ref) {
		super("Commute", "(Java)");
		this.inputTree = tree;
		this.ref = ref;
	}

	public IStrategoTerm getResult() {
		return result;
	}

	private IStrategoTerm traverse(IStrategoTerm tree) {
		if (tree instanceof IStrategoAppl) {
			IStrategoAppl appl = (IStrategoAppl) tree;
			if (appl.getName().equals("Invoke") && appl.getSubtermCount() == 2) {
				if (appl.getSubterm(1) instanceof IStrategoList) {
					IStrategoList args = (IStrategoList) appl.getSubterm(1);
					if (args.getSubtermCount() == 2) {
						args = StrategoAdapter.getTermFactory().makeList(
								args.getSubterm(1), args.getSubterm(0));
						appl = StrategoAdapter.getTermFactory()
								.makeAppl(appl.getConstructor(),
										appl.getSubterm(0), args);
						tree = appl;
					}
				}
			}
		}

		int n = tree.getSubtermCount();

		if (n > 0) {
			IStrategoTerm[] terms = tree.getAllSubterms().clone();
			boolean changed = false;
			for (int i = 0; i < n; i++) {
				IStrategoTerm t = traverse(terms[i]);
				changed |= t != terms[i];
				terms[i] = t;
			}
			if (changed) {
				switch (tree.getTermType()) {
				case IStrategoTerm.APPL:
					tree = StrategoAdapter.getTermFactory().makeAppl(
							((IStrategoAppl) tree).getConstructor(), terms);
					break;
				case IStrategoTerm.LIST:
					tree = StrategoAdapter.getTermFactory().makeList(terms);
					break;
				case IStrategoTerm.TUPLE:
					tree = StrategoAdapter.getTermFactory().makeTuple(terms);
					break;
				default:
					throw new RuntimeException(
							"Don't know how to build terms of type "
									+ tree.getTermType());
				}
			}
		}

		return tree;
	}

	@Override
	protected boolean check() {
		if (ref != null) {
			return ref.equals(result);
		} else {
			return true;
		}
	}

	@Override
	protected void doIt() {
		result = traverse(inputTree);
	}
}
