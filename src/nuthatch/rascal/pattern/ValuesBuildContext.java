package nuthatch.rascal.pattern;

import nuthatch.pattern.BuildContext;
import nuthatch.rascal.adapter.PdbCursor;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.type.TypeStore;

public class ValuesBuildContext implements BuildContext<IValue, Type, PdbCursor> {
	private final IValueFactory vf;
	private final TypeFactory tf = TypeFactory.getInstance();
	private final TypeStore ts;


	public ValuesBuildContext(IValueFactory vf, TypeStore ts) {
		this.vf = vf;
		this.ts = ts;
	}


	@SuppressWarnings("unchecked")
	@Override
	public PdbCursor create(String name, Type type, IValue value, PdbCursor[] children) {
		if(value != null) {
			return new PdbCursor(value);
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
				return new PdbCursor(vf.constructor(consType, childValues));
			}
			else if(type != null && type.isConstructor()) {
				return new PdbCursor(vf.constructor(type, childValues));
			}
			else if((type != null && type.isList()) || "[]".equals(name)) {
				return new PdbCursor(vf.list(childValues));
			}
			else if((type != null && type.isTuple()) || "()".equals(name)) {
				return new PdbCursor(vf.tuple(childValues));
			}
		}
		throw new IllegalArgumentException();
	}


	public IValueFactory getValueFactory() {
		return vf;
	}
}
