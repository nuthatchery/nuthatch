package nuthatch.rascal.adapter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.values.uptr.ITree;
import org.rascalmpl.values.uptr.ProductionAdapter;
import org.rascalmpl.values.uptr.RascalValueFactory;
import org.rascalmpl.values.uptr.TreeAdapter;

import nullness.Nullable;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.TreeHandle;
import nuthatch.tree.impl.AbstractTreeCursor;
import nuthatch.walker.errors.TypeMismatch;

public class UptrCursor extends AbstractTreeCursor<String, Type, ITree> {

	private String leafValue = null;
	private IConstructor prod = null;
	private List<ITree> children = null;
	private String name;


	public UptrCursor(ITree value) {
		super(value);
		if(!value.getType().equals(RascalValueFactory.Tree)) {
			throw new IllegalArgumentException("Must be a parse tree!");
		}
	}


	protected UptrCursor(UptrCursor src, boolean fullTree) {
		super(src, fullTree);
	}


	protected UptrCursor(UptrCursor src, ITree replacement) {
		super(src, replacement);
	}


	@Override
	public TreeCursor<String, Type> copy() {
		return new UptrCursor(this, true);
	}


	@Override
	public TreeCursor<String, Type> copyAndReplaceSubtree(TreeCursor<String, Type> replacement) {
		if(replacement instanceof UptrCursor) {
			ITree repl = ((UptrCursor) replacement).getCurrent();
			if(!repl.getType().isSubtypeOf(getCurrent().getType())) {
				throw new TypeMismatch();
			}
			return new UptrCursor(this, repl);
		}
		else {
			throw new UnsupportedOperationException("Replacing with different cursor type");
		}
	}


	@Override
	public TreeCursor<String, Type> copySubtree() {
		return new UptrCursor(this, false);
	}


	private void decodeNode() {
		ITree tree = getCurrent();
		assert TreeAdapter.isAppl(tree);
		prod = TreeAdapter.getProduction(tree);
		leafValue = null;
		children = null;
		name = null;
		if(ProductionAdapter.isLexical(prod) || ProductionAdapter.isKeyword(prod)) {
			leafValue = TreeAdapter.yield(tree);
		}
		else if(ProductionAdapter.isContextFree(prod)) {
			children = getChildren(tree);
			name = ProductionAdapter.getConstructorName(prod);
			if(name == null) {
				name = ProductionAdapter.getSortName(prod);
			}
		}
		else if(ProductionAdapter.isList(prod)) {
			children = getChildren(tree);
			name = "[]";
		}

	}


	@Override
	public int getArity() {
		if(!isDecoded()) {
			decodeNode();
		}
		if(children != null) {
			return children.size();
		}
		else {
			return 0;
		}
	}


	@Override
	protected ITree getChild(int i) {
		if(!isDecoded()) {
			decodeNode();
		}

		return children.get(i);
	}


	private List<ITree> getChildren(ITree t) {
		IList args = TreeAdapter.getArgs(t);
		List<ITree> list = new ArrayList<ITree>();
		for(IValue val : args) {
			ITree child = (ITree) val;
			if(TreeAdapter.isAmb(child)) {
				child = (ITree) TreeAdapter.getAlternatives(child).iterator().next();
			}
			if(!(TreeAdapter.isLayout(child) || TreeAdapter.isLiteral(child))) {
				list.add(child);
			}
		}
		return list;
	}


	@Override
	protected ITree getCurrent() {
		ITree tree = super.getCurrent();

		if(tree != null) {
			while(TreeAdapter.isAmb(tree)) {
				tree = (ITree) TreeAdapter.getAlternatives(tree).iterator().next();
			}
		}
		return tree;
	}


	@Override
	public String getData() {
		if(!isDecoded()) {
			decodeNode();
		}
		return leafValue;
	}


	@Override
	public String getName() {
		if(!isDecoded()) {
			decodeNode();
		}

		return name;
	}


	@Override
	public Type getType() {
		return getCurrent().getType();
	}


	@Override
	public TreeCursor<String, Type> go(int i) {
		super.go(i);
		prod = null;

		ITree tree = getCurrent();

		if(tree != null && tree.getConstructorType().equals(RascalValueFactory.Tree_Cycle)) {
			throw new UnsupportedOperationException("Tree cyclces not supported");
		}

		return this;
	}


	@Override
	public boolean hasData() {
		if(!isDecoded()) {
			decodeNode();
		}
		return leafValue != null;
	}


	@Override
	public boolean hasName() {
		if(!isDecoded()) {
			decodeNode();
		}
		return name != null;
	}


	private boolean isDecoded() {
		return prod != null;
	}


	@Override
	protected ITree replaceChild(ITree node, ITree child, int i) {
		return (ITree) node.set(i, child);
	}


	@Override
	public boolean subtreeEquals(@Nullable TreeHandle<String, Type> other) {
		if(this == other) {
			return true;
		}
		else if(other == null) {
			return false;
		}
		else if(other instanceof UptrCursor) {
			return getCurrent().isEqual(((UptrCursor) other).getCurrent());
		}
		else {
			throw new UnsupportedOperationException("Equality only supported on UptrCursor");
		}
	}

}
