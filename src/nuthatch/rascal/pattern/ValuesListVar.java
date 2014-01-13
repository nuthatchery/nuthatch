package nuthatch.rascal.pattern;

import nuthatch.pattern.BuildContext;
import nuthatch.pattern.Environment;
import nuthatch.pattern.NotBuildableException;
import nuthatch.pattern.Pattern;
import nuthatch.tree.TreeCursor;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;

public class ValuesListVar implements Pattern<IValue, Type> {
	private TreeCursor<IValue, Type> data;
	private String name;


	public ValuesListVar(String name) {
		this.name = name.intern();
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> T build(BuildContext<IValue, Type> context, Environment<T> env) throws NotBuildableException {
		if(data == null) {
			throw new NotBuildableException("Variable not bound");
		}
		else {
			return (T) data.copySubtree();
		}
	}


	@Override
	public <T extends TreeCursor<IValue, Type>> boolean match(T tree, Environment<T> env) {
		if(data == null) {
			if(tree.getType().isList()) {
				data = tree.copySubtree();
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return data.subtreeEquals(tree);
		}
	}


	@Override
	public boolean subTreeOnly() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String toString() {
		return "*" + name;
	}
}
