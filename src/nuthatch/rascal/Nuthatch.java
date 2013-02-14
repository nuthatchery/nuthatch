package nuthatch.rascal;

import nuthatch.library.walks.Topdown;
import nuthatch.rascal.adapter.PdbCursor;
import nuthatch.rascal.adapter.UptrCursor;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Action;
import nuthatch.walk.Walk;
import nuthatch.walk.impl.SimpleWalk;

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
		Action<String, Type> t = new Action<String, Type>() {
			@Override
			public TreeCursor<String, Type> apply(Walk<String, Type> e) {
				String name = e.getName();
				if (name != null) {
					ctx.getStdOut().print(name + " ");
				} else {
					ctx.getStdOut().print(e.getData());
				}
				return null;
			}
		};
		Topdown<String, Type, SimpleWalk<String, Type>> topDown = new Topdown<String, Type, SimpleWalk<String, Type>>(
				t);
		Walk<String, Type> e = new SimpleWalk<String, Type>(new UptrCursor(
				tree), topDown);
		e.start();

		ctx.getStdOut().println();
		ctx.getStdOut().flush();

		return tree;
	}

	public INode engage(INode n, final IEvaluatorContext ctx) {
		Action<IValue, Type> t = new Action<IValue, Type>() {
			@Override
			public TreeCursor<IValue, Type> apply(Walk<IValue, Type> e) {
				String name = e.getName();
				if (name != null) {
					ctx.getStdOut().print(e.getName() + " ");
				} else {
					ctx.getStdOut().print(e.getData().toString());
				}
				return null;
			}
		};
		Topdown<IValue, Type, SimpleWalk<IValue, Type>> topDown = new Topdown<IValue, Type, SimpleWalk<IValue, Type>>(
				t);
		Walk<IValue, Type> e = new SimpleWalk<IValue, Type>(
				new PdbCursor(n), topDown);
		e.start();

		ctx.getStdOut().println();
		ctx.getStdOut().flush();

		return vf.node("foo");
	}

}
