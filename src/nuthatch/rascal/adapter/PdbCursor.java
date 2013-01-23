package nuthatch.rascal.adapter;

import java.util.ArrayList;
import java.util.List;

import nuthatch.tree.ModifiableTree;
import nuthatch.tree.Tree;
import nuthatch.tree.errors.BranchNotFoundError;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IDateTime;
import org.eclipse.imp.pdb.facts.IExternalValue;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListRelation;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IRational;
import org.eclipse.imp.pdb.facts.IReal;
import org.eclipse.imp.pdb.facts.IRelation;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.visitors.IValueVisitor;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;

abstract class PdbCursor<T extends IValue> implements Tree {
	protected T currentNode;

	protected List<IValue> stack;

	protected static PdbCursor<? extends IValue> makeCursor(IValue value, final IValue parent, final List<IValue> stack) {
		try {
			return value.accept(new IValueVisitor<PdbCursor<? extends IValue>>() {

				@Override
				public PdbCursor<IValue> visitBoolean(IBool boolValue) {
					return new AtomCursor(boolValue, parent, stack);
				}

				@Override
				public PdbCursor<INode> visitConstructor(IConstructor o) {
					return new NodeCursor(o, parent, stack);
				}

				@Override
				public PdbCursor<IValue> visitDateTime(IDateTime o) {
					return new AtomCursor(o, parent, stack);
				}

				@Override
				public PdbCursor<IValue> visitExternal(IExternalValue externalValue) {
					return new AtomCursor(externalValue, parent, stack);
				}

				@Override
				public PdbCursor<IValue> visitInteger(IInteger o) {
					return new AtomCursor(o, parent, stack);
				}

				@Override
				public PdbCursor<IList> visitList(IList o) {
					return new ListCursor(o, parent, stack);
				}

				@Override
				public PdbCursor<IList> visitListRelation(IListRelation o ) {
					return new ListCursor(o, parent, stack);
				}

				@Override
				public PdbCursor<IValue> visitMap(IMap o) {
					throw new UnsupportedOperationException();
				}

				@Override
				public PdbCursor<INode> visitNode(INode o) {
					return new NodeCursor(o, parent, stack);
				}

				@Override
				public PdbCursor<IValue> visitRational(IRational o) {
					return new AtomCursor(o, parent, stack);
				}

				@Override
				public PdbCursor<IValue> visitReal(IReal o) {
					return new AtomCursor(o, parent, stack);
				}

				@Override
				public PdbCursor<IValue> visitRelation(IRelation o) {
					throw new UnsupportedOperationException();
				}

				@Override
				public PdbCursor<IValue> visitSet(ISet o) {
					throw new UnsupportedOperationException();
				}

				@Override
				public PdbCursor<IValue> visitSourceLocation(ISourceLocation o) {
					return new AtomCursor(o, parent, stack);
				}

				@Override
				public PdbCursor<IValue> visitString(IString o) {
					return new AtomCursor(o, parent, stack);
				}

				@Override
				public PdbCursor<ITuple> visitTuple(ITuple o) {
					return new TupleCursor(o, parent, stack);
				}
				
			});
		} catch (VisitorException e) {
			throw new RuntimeException("This can't happen!", e);
		}
	}
	
	protected PdbCursor(T node, IValue parent, List<IValue> stack) {
		this.currentNode = node;
		this.stack = new ArrayList<IValue>(stack);
		if(parent != null) {
			this.stack.add(parent);
		}
		else {
			this.stack.remove(this.stack.size()-1);
		}
	}

	@Override
	public Iterable<Tree> children() {
		return null;
	}
	
	@Override
	public ModifiableTree copy() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Tree getBranch(int i) throws BranchNotFoundError {
		i = decodeBranchIndex(i);
		if(i == 0) {
			return makeCursor(stack.get(stack.size()-1), null, stack);
		}
		else {
			return makeCursor(getChild(i), currentNode, stack);
		}
	}

	@Override
	public int getBranch(Tree node) throws BranchNotFoundError {
		if(getParent() == node) {
			return 0;
		}
		else {
			int i = 1;
			for(IValue child : nodeChildren()) {
				if(child == node) {
					return i;
				}
				else {
					i++;
				}
			}
			throw new BranchNotFoundError(node.getNodeId());
		}
	}
	
	@Override
	public String getNodeId() {
		return Integer.toHexString(System.identityHashCode(currentNode));
	}

	@Override
	public Tree getParent() {
		if(stack.isEmpty()) {
			throw new BranchNotFoundError("0");
		}
		else {
			return makeCursor(stack.get(stack.size()-1), null, stack);
		}
	}


	@Override
	public String getType() {
		return currentNode.getType().toString();
	}

	@Override
	public boolean hasBranch(int i) {
		int branches = numChildren() + 1;
		if(i == -1) {
			i = branches - 1;
		}
		else if(i == branches) {
			i = 0;
		}
		return i >= 0 && i < branches;	
	}

	@Override
	public boolean hasParentLinks() {
		return true;
	}

	@Override
	public boolean isParent(int i) {
		return i == 0 || i == numChildren() + 1;
	}

	@Override
	public boolean isRoot() {
		return stack.isEmpty();
	}


	protected int decodeBranchIndex(int i) {
		int branches = numChildren() + 1;
		if(i == -1) {
			i = branches - 1;
		}
		else if(i == branches) {
			i = 0;
		}
		
		if(i < 0 || i > branches) {
			throw new BranchNotFoundError("Illegal branch: " + i);
		}
		
		return i;
	}

	protected abstract IValue getChild(int i);

	
	protected abstract Iterable<IValue> nodeChildren();
	
	static class AtomCursor extends PdbCursor<IValue> {

		protected AtomCursor(IValue atom, IValue parent, List<IValue> stack) {
			super(atom, parent, stack);
		}

		@Override
		public IValue getChild(int i) throws BranchNotFoundError {
			throw new BranchNotFoundError(String.valueOf(i));
		}

		@Override
		public IValue getData() {
			return currentNode;
		}

		@Override
		public String getName() {
			return "";
		}

		@Override
		public boolean isLeaf() {
			return true;
		}

		@Override
		public int numChildren() {
			return 0;
		}

		@Override
		protected Iterable<IValue> nodeChildren() {
			return null;
		}
	}
	
	static class NodeCursor extends PdbCursor<INode> {
		protected NodeCursor(INode node, IValue parent, List<IValue> stack) {
			super(node, parent, stack);
		}

		@Override
		public IValue getChild(int i) throws BranchNotFoundError {
			return currentNode.get(i);
		}

		@Override
		public IValue getData() {
			return currentNode;
		}

		@Override
		public String getName() {
			String name = currentNode.getName();
			return name != null ? name : "";
		}

		@Override
		public boolean isLeaf() {
			return false;
		}

		@Override
		public int numChildren() {
			return currentNode.arity();
		}

		@Override
		protected Iterable<IValue> nodeChildren()  {
			return currentNode.getChildren();
		}
	}

	static class TupleCursor extends PdbCursor<ITuple> {
		protected TupleCursor(ITuple tuple, IValue parent, List<IValue> stack) {
			super(tuple, parent, stack);
		}

		@Override
		public IValue getChild(int i) throws BranchNotFoundError {
			return currentNode.get(i);
		}

		@Override
		public IValue getData() {
			return currentNode;
		}

		@Override
		public String getName() {
			return "";
		}

		@Override
		public boolean isLeaf() {
			return false;
		}

		@Override
		public int numChildren() {
			return currentNode.arity();
		}

		@Override
		protected Iterable<IValue> nodeChildren()  {
			return currentNode;
		}
	}

	static class ListCursor extends PdbCursor<IList> {
		protected ListCursor(IList list, IValue parent, List<IValue> stack) {
			super(list, parent, stack);
		}

		@Override
		public IValue getChild(int i) throws BranchNotFoundError {
			return currentNode.get(i);
		}

		@Override
		public IValue getData() {
			return currentNode;
		}

		@Override
		public String getName() {
			return "[]";
		}

		@Override
		public boolean isLeaf() {
			return false;
		}

		@Override
		public int numChildren() {
			return currentNode.length();
		}

		@Override
		protected Iterable<IValue> nodeChildren()  {
			return currentNode;
		}
	}
}