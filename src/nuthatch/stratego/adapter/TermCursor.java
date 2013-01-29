package nuthatch.stratego.adapter;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.tree.impl.AbstractTreeCursor;

import org.spoofax.interpreter.terms.*;

public class TermCursor extends AbstractTreeCursor<IStrategoTerm, Integer, IStrategoTerm> {
	private final IStrategoTermBuilder builder;
	
	public TermCursor(IStrategoTerm tree, IStrategoTermBuilder builder) {
		super(tree);
		this.builder = builder;
	}

	protected TermCursor(TermCursor src, boolean fullTree) {
		super(src, fullTree);
		this.builder = src.builder;
	}
	protected TermCursor(TermCursor src, IStrategoTerm replacement) {
		super(src, replacement);
		this.builder = src.builder;
	}

	@Override
	public TreeCursor<IStrategoTerm, Integer> copy() {
		return new TermCursor(this, true);
	}

	@Override
	public TreeCursor<IStrategoTerm, Integer> copySubtree() {
		return new TermCursor(this, false);
	}

	@Override
	public TreeCursor<IStrategoTerm, Integer> copyAndReplaceSubtree(
			TreeCursor<IStrategoTerm, Integer> replacement) {
		if(replacement instanceof TermCursor) {
			return new TermCursor(this, ((TermCursor) replacement).getCurrent());
		}
		else {
			throw new UnsupportedOperationException("Replacing with different cursor type");
		}
	}

	@Override
	public IStrategoTerm getData() {
		return getCurrent();
	}

	@Override
	public String getName() {
		IStrategoTerm term = getCurrent();
		if(term instanceof IStrategoNamed) {
			return ((IStrategoNamed) term).getName();
		}
		else {
			return null;
		}
	}

	@Override
	public int getNumChildren() {
		return getCurrent().getSubtermCount();
	}

	@Override
	public Integer getType() {
		return getCurrent().getTermType();
	}

	@Override
	public boolean hasData() {
		return true;
	}

	@Override
	public boolean hasName() {
		return getCurrent() instanceof IStrategoNamed;
	}


	@Override
	public boolean subtreeEquals(TreeCursor<IStrategoTerm, Integer> other) {
		if(other instanceof TermCursor) {
			return getCurrent().equals(((TermCursor) other).getCurrent());
		}
		else {
			throw new UnsupportedOperationException("Equality only supported on TermCursor");
		}
	}

	@Override
	protected IStrategoTerm replaceChild(IStrategoTerm node,
			IStrategoTerm child, int i) {
		switch(getCurrent().getTermType()) {
	    case IStrategoTerm.APPL: {
	    	IStrategoAppl term = (IStrategoAppl) getCurrent();
	    	IStrategoTerm[] kids = term.getAllSubterms().clone();
	    	kids[i] = child;
	    	return builder.makeAppl(term.getConstructor(), kids, term.getAnnotations());
	    }
	    case IStrategoTerm.LIST: {
	    	IStrategoList term = (IStrategoList) getCurrent();
	    	IStrategoTerm[] kids = term.getAllSubterms().clone();
	    	kids[i] = child;
	    	return builder.makeList(kids, term.getAnnotations());
	    }
	    case IStrategoTerm.TUPLE: {
	    	IStrategoTuple term = (IStrategoTuple) getCurrent();
	    	IStrategoTerm[] kids = term.getAllSubterms().clone();
	    	kids[i] = child;
	    	return builder.makeTuple(kids, term.getAnnotations());
	    }
	    case IStrategoTerm.INT:
	    case IStrategoTerm.REAL:
	    case IStrategoTerm.STRING:
	    	throw new BranchNotFoundError(String.valueOf(i + 1));
	    case IStrategoTerm.CTOR:
	    case IStrategoTerm.REF:
	    case IStrategoTerm.BLOB:
	    case IStrategoTerm.PLACEHOLDER:
	    	System.err.println("Don't know how to deal with term type " + getCurrent().getTermType());
	    	break;
		}

		return null;
	}

	@Override
	protected IStrategoTerm getChild(int i) {
		return getCurrent().getSubterm(i);
	}

	public IStrategoTerm getTerm() {
		return getCurrent();
	}
}
