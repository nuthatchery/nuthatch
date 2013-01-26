package nuthatch.rascal.adapter;

import java.util.ArrayList;
import java.util.List;

import nullness.Nullable;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.impl.AbstractTreeCursor;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.values.uptr.Factory;
import org.rascalmpl.values.uptr.ProductionAdapter;
import org.rascalmpl.values.uptr.TreeAdapter;

public class UptrCursor extends AbstractTreeCursor<String, Type, IConstructor> {

	private String leafValue = null;
	private IConstructor prod = null;
	private List<IConstructor> children = null;
	private String name;
	
	public UptrCursor(IConstructor value) {
		super(value);
		if(!value.getType().equals(Factory.Tree)) {
			throw new IllegalArgumentException("Must be a parse tree!");
		}
	}
	
	protected UptrCursor(UptrCursor src) {
		super(src);
	}

	@Override
	public TreeCursor<String, Type> copy() {
		return new UptrCursor(this);
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
	public int getNumChildren() {
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
	protected IConstructor getChild(int i) {
		if(!isDecoded()) {
			decodeNode();
		}

		return children.get(i);
	}

	@Override
	public TreeCursor<String, Type> go(int i) {
		super.go(i);
		prod = null;
		
		IConstructor tree = getCurrent();
	
		if(tree != null && tree.getConstructorType().equals(Factory.Tree_Cycle)) {
			throw new UnsupportedOperationException("Tree cyclces not supported");
		}
		
		return this;
	}

	@Override
	protected IConstructor getCurrent() {
		IConstructor tree = super.getCurrent();

		if(tree != null) {
		while(TreeAdapter.isAmb(tree)) {
			tree = (IConstructor) TreeAdapter.getAlternatives(tree).iterator().next();
		}
		}
		return tree;
	}
	
	private void decodeNode() {
		IConstructor tree = getCurrent();
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
	
	private List<IConstructor> getChildren(IConstructor t) {
		IList args = TreeAdapter.getArgs(t);
		List<IConstructor> list = new ArrayList<IConstructor>();
		for(IValue val : args) {
			IConstructor child = (IConstructor) val;
			if(TreeAdapter.isAmb(child)) {
				child = (IConstructor) TreeAdapter.getAlternatives(child).iterator().next();
			}
			if(!(TreeAdapter.isLayout(child) || TreeAdapter.isLiteral(child))) {
				list.add(child);
			}
		}
		return list;
	}
	
	private boolean isDecoded() {
		return prod != null;
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

	@Override
	public boolean subtreeEquals(@Nullable TreeCursor<String, Type> other) {
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
