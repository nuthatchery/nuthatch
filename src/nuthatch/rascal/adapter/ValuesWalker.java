package nuthatch.rascal.adapter;

import nuthatch.library.Walk;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.walker.impl.AbstractWalker;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class ValuesWalker extends AbstractWalker<IValue, Type, ValuesWalker> {
	public ValuesWalker(IValue term, Walk<ValuesWalker> step) {
		super(new PdbCursor(term), step);
	}


	public ValuesWalker(PdbCursor cursor, Walk<ValuesWalker> step) {
		super(cursor, step);
	}


	@Override
	public PdbCursor copy() {
		return (PdbCursor) super.copy();
	}


	@Override
	public PdbCursor copyAndReplaceSubtree(TreeCursor<IValue, Type> replacement) {
		return (PdbCursor) super.copyAndReplaceSubtree(replacement);
	}


	@Override
	public PdbCursor copySubtree() {
		return (PdbCursor) super.copySubtree();
	}


	@Override
	public PdbCursor getBranchCursor(int i) throws BranchNotFoundError {
		return (PdbCursor) super.getBranchCursor(i);
	}


	@Override
	protected ValuesWalker subWalk(TreeCursor<IValue, Type> cursor, Walk<ValuesWalker> step) {
		return new ValuesWalker((PdbCursor) cursor, step);
	}

}
