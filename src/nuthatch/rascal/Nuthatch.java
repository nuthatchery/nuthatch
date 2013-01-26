package nuthatch.rascal;

import nuthatch.engine.Engine;
import nuthatch.engine.impl.StrategicEngine;
import nuthatch.library.strategies.TopdownStrategy;
import nuthatch.rascal.adapter.PdbCursor;
import nuthatch.rascal.adapter.UptrCursor;
import nuthatch.strategy.Transform;
import nuthatch.tree.TreeCursor;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.interpreter.IEvaluatorContext;


public class Nuthatch {
	private final IValueFactory vf;

	public Nuthatch(IValueFactory vf) {
		this.vf = vf;

	}

	public IConstructor engage(IConstructor tree, final IEvaluatorContext ctx) {
		Transform t = new Transform() {
			@Override
			public TreeCursor apply(Engine e) {
				String name = e.getName();
				if(name != null) {
					ctx.getStdOut().print(name + " ");
				} else {
					ctx.getStdOut().print(e.getData().toString());
				}
				return null;
			}
		};
		TopdownStrategy topDown = new TopdownStrategy(t);
		Engine<String, Type> e = new StrategicEngine<String, Type>(new UptrCursor(tree), topDown);
		e.engage();

		ctx.getStdOut().println();
		ctx.getStdOut().flush();

		return tree;
	}

	public INode engage(INode n, final IEvaluatorContext ctx) {
		Transform t = new Transform() {
			@Override
			public TreeCursor apply(Engine e) {
				String name = e.getName();
				if(name != null) {
					ctx.getStdOut().print(e.getName() + " ");
				} else {
					ctx.getStdOut().print(e.getData().toString());
				}
				return null;
			}
		};
		TopdownStrategy topDown = new TopdownStrategy(t);
		Engine<IValue, Type> e = new StrategicEngine<IValue, Type>(new PdbCursor(n), topDown);
		e.engage();

		ctx.getStdOut().println();
		ctx.getStdOut().flush();

		return vf.node("foo");
	}

}
