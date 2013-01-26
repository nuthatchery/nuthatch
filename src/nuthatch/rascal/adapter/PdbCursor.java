package nuthatch.rascal.adapter;

import nullness.Nullable;
import nuthatch.engine.errors.TypeMismatch;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.impl.AbstractTreeCursor;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class PdbCursor extends AbstractTreeCursor<IValue, Type, IValue> {

	public PdbCursor(IValue value) {
		super(value);
	}

	protected PdbCursor(PdbCursor src, boolean fullTree) {
		super(src, fullTree);
	}

	protected PdbCursor(PdbCursor src, IValue replacement) {
		super(src, replacement);
	}
	
	@Override
	public TreeCursor<IValue, Type> copy() {
		return new PdbCursor(this, true);
	}

	@Override
	public TreeCursor<IValue, Type> copySubtree() {
		return new PdbCursor(this, false);
	}

	@Override
	public TreeCursor<IValue, Type> copyAndReplaceSubtree(
			TreeCursor<IValue, Type> replacement) {
		if(replacement instanceof PdbCursor) {
			IValue repl = ((PdbCursor)replacement).getCurrent();
			if(!repl.getType().isSubtypeOf(getCurrent().getType())) {
				throw new TypeMismatch();
			}
			return new PdbCursor(this, repl);
		}
		else {
			throw new UnsupportedOperationException("Replacing with different cursor type");
		}
	}
	
	@Override
	public IValue getData() {
		return getCurrent();
	}

	@Override
	public String getName() {
		IValue value = getCurrent();
		if(value instanceof INode) {
			return ((INode) value).getName();
		}
		else if(value instanceof IList) {
			return "[]";
		}
		else if(value instanceof ITuple) {
			return "";
		}
		else if(value instanceof ISet) {
			return "{}";
		}
		else if(value instanceof IMap) {
			return "()";
		}
		else {
			return null;
		}
	}

	@Override
	public int getNumChildren() {
		IValue value = getCurrent();
		if(value instanceof INode) {
			return ((INode) value).arity();
		}
		else if(value instanceof IList) {
			return ((IList) value).length();
		}
		//else if(value instanceof ISet) {
		//	return ((ISet) value).size();
		//}
		else if(value instanceof ITuple) {
			return ((ITuple) value).arity();
		}
		else {
			return 0;
		}
	}

	@Override
	public Type getType() {
		return getCurrent().getType();
	}

	@Override
	public boolean hasData() {
		return true;
	}

	@Override
	public boolean hasName() {
		return getName() != null;
	}

	@Override
	public boolean subtreeEquals(@Nullable TreeCursor<IValue, Type> other) {
		if(this == other) {
			return true;
		}
		else if(other == null) {
			return false;
		}
		else if(other instanceof PdbCursor) {
			return getCurrent().isEqual(((PdbCursor) other).getCurrent());
		}
		else {
			throw new UnsupportedOperationException("Equality only supported on PdbCursor");
		}
	}

	@Override
	protected IValue getChild(int i) {
		IValue value = getCurrent();
		if(value instanceof INode) {
			return ((INode) value).get(i);
		}
		else if(value instanceof IList) {
			return ((IList) value).get(i);
		}
		else if(value instanceof ITuple) {
			return ((ITuple) value).get(i);
		}
		return null;
	}

	@Override
	protected IValue replaceChild(IValue node, IValue child, int i) {
		if(node instanceof INode) {
			return ((INode) node).set(i, child);
		}
		else if(node instanceof IList) {
			return ((IList) node).put(i, child);
		}
		else if(node instanceof ITuple) {
			return ((ITuple) node).set(i, child);
		}
		return null;
	}


}