package nuthatch.rascal.adapter;

import nuthatch.tree.Tree;
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
	
	protected PdbCursor(PdbCursor src) {
		super(src);
	}

	@Override
	public TreeCursor<IValue, Type> copy() {
		return new PdbCursor(this);
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
	public Type getType() {
		return getCurrent().getType();
	}

	@Override
	public boolean matches(Tree<IValue, Type> tree) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int numChildren() {
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
	
}