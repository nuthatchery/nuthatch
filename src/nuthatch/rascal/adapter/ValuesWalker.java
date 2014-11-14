package nuthatch.rascal.adapter;

import nuthatch.library.Walk;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.walker.impl.AbstractWalker;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class ValuesWalker extends AbstractWalker<IValue, Type, ValuesWalker> {
	public ValuesWalker(IValue term, Walk<ValuesWalker> step) {
		super(new ValuesCursor(term), step);
	}


	public ValuesWalker(ValuesCursor cursor, Walk<ValuesWalker> step) {
		super(cursor, step);
	}


	@Override
	public ValuesCursor copy() {
		return (ValuesCursor) super.copy();
	}


	@Override
	public ValuesCursor copyAndReplaceSubtree(TreeCursor<IValue, Type> replacement) {
		return (ValuesCursor) super.copyAndReplaceSubtree(replacement);
	}


	@Override
	public ValuesCursor copySubtree() {
		return (ValuesCursor) super.copySubtree();
	}


	@Override
	public ValuesCursor getBranchCursor(int i) throws BranchNotFoundError {
		return (ValuesCursor) super.getBranchCursor(i);
	}


	public void replace(IValue value) {
		replace(new ValuesCursor(value));
	}


	@Override
	protected ValuesWalker subWalk(TreeCursor<IValue, Type> cursor, Walk<ValuesWalker> step) {
		return new ValuesWalker((ValuesCursor) cursor, step);
	}

}
