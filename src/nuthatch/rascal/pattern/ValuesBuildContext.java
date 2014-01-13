package nuthatch.rascal.pattern;

import nuthatch.pattern.BuildContext;
import nuthatch.rascal.adapter.PdbCursor;
import nuthatch.tree.TreeCursor;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.type.TypeStore;

public class ValuesBuildContext implements BuildContext<IValue, Type> {
	private final IValueFactory vf;
	private final TypeFactory tf = TypeFactory.getInstance();
	private final TypeStore ts;


	public ValuesBuildContext(IValueFactory vf, TypeStore ts) {
		this.vf = vf;
		this.ts = ts;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T extends TreeCursor<IValue, Type>> T create(String name, Type type, IValue value, T[] children) {
		if(value != null) {
			return (T) new PdbCursor(value);
		}
		else if(children != null) {
			IValue[] childValues = new IValue[children.length];
			for(int i = 0; i < children.length; i++) {
				childValues[i] = children[i].getData();
			}
			if(name != null && type != null && type.isAbstractData()) {
				Type[] childTypes = new Type[children.length];
				for(int i = 0; i < children.length; i++) {
					childTypes[i] = children[i].getData().getType();
				}
				Type consType = ts.lookupConstructor(type, name, tf.tupleType(childTypes));
				if(consType == null) {
					System.out.println("Help!");
					consType = tf.constructor(ts, type, name, childTypes);
				}
				// Type consType = tf.constructor(ts, type, name, childTypes);
				return (T) new PdbCursor(vf.constructor(consType, childValues));
			}
			else if((type != null && type.isList()) || "[]".equals(name)) {
				return (T) new PdbCursor(vf.list(childValues));
			}
			else if((type != null && type.isTuple()) || "()".equals(name)) {
				return (T) new PdbCursor(vf.tuple(childValues));
			}
		}
		throw new IllegalArgumentException();
	}

}
