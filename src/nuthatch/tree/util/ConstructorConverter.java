package nuthatch.tree.util;

import nuthatch.tree.ModifiableTree;
import nuthatch.tree.Tree;
import nuthatch.tree.impl.StandardTree;

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
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.visitors.IValueVisitor;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;

public class ConstructorConverter {
	public static Tree valueOf(IConstructor cons) {
		try {
			ModifiableTree tree = cons.accept(new IValueVisitor<ModifiableTree>() {

				@Override
				public ModifiableTree visitBoolean(IBool boolValue) throws VisitorException {
					return new StandardTree(boolValue);
				}


				@Override
				public ModifiableTree visitConstructor(IConstructor o) throws VisitorException {
					Type type = o.getType();
					ModifiableTree[] children = new ModifiableTree[o.arity()];
					int i = 0;
					for(IValue child : o) {
						children[i++] = child.accept(this);
					}
					return new StandardTree(o.getName(), type.toString(), children);
				}


				@Override
				public ModifiableTree visitDateTime(IDateTime o) throws VisitorException {
					return new StandardTree(o);
				}


				@Override
				public ModifiableTree visitExternal(IExternalValue externalValue) throws VisitorException {
					return new StandardTree(externalValue);
				}


				@Override
				public ModifiableTree visitInteger(IInteger o) {
					return new StandardTree(o);
				}


				@Override
				public ModifiableTree visitList(IList o) {
					return new StandardTree(o);
				}


				@Override
				public ModifiableTree visitListRelation(IListRelation o) throws VisitorException {
					return new StandardTree(o);
				}


				@Override
				public ModifiableTree visitMap(IMap o) throws VisitorException {
					return new StandardTree(o);
				}


				@Override
				public ModifiableTree visitNode(INode o) throws VisitorException {
					Type type = o.getType();
					ModifiableTree[] children = new ModifiableTree[o.arity()];
					int i = 0;
					for(IValue child : o) {
						children[i++] = child.accept(this);
					}
					return new StandardTree(o.getName(), type.toString(), children);
				}


				@Override
				public ModifiableTree visitRational(IRational o) {
					return new StandardTree(o);
				}


				@Override
				public ModifiableTree visitReal(IReal o) {
					return new StandardTree(o);
				}


				@Override
				public ModifiableTree visitRelation(IRelation o) {
					return new StandardTree(o);
				}


				@Override
				public ModifiableTree visitSet(ISet o) {
					return new StandardTree(o);
				}


				@Override
				public ModifiableTree visitSourceLocation(ISourceLocation o) throws VisitorException {
					return new StandardTree(o);
				}


				@Override
				public ModifiableTree visitString(IString o) {
					return new StandardTree(o);
				}


				@Override
				public ModifiableTree visitTuple(ITuple o) throws VisitorException {
					Type type = o.getType();
					ModifiableTree[] children = new ModifiableTree[o.arity()];
					int i = 0;
					for(IValue child : o) {
						children[i++] = child.accept(this);
					}
					return new StandardTree("", type.toString(), children);
				}
			});
			return tree.freeze();
		}
		catch(VisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
