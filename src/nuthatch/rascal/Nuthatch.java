package nuthatch.rascal;

import nuthatch.library.walks.Topdown;
import nuthatch.rascal.adapter.PdbCursor;
import nuthatch.rascal.adapter.UptrCursor;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Action;
import nuthatch.walker.Walker;
import nuthatch.walker.impl.BasicWalker;

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
			public TreeCursor<String, Type> apply(Walker<String, Type> e) {
				String name = e.getName();
				if (name != null) {
					ctx.getStdOut().print(name + " ");
				} else {
					ctx.getStdOut().print(e.getData());
				}
				return null;
			}
		};
		Topdown<String, Type, BasicWalker<String, Type>> topDown = new Topdown<String, Type, BasicWalker<String, Type>>(
				t);
		Walker<String, Type> e = new BasicWalker<String, Type>(new UptrCursor(
				tree), topDown);
		e.start();

		ctx.getStdOut().println();
		ctx.getStdOut().flush();

		return tree;
	}

	public INode engage(INode n, final IEvaluatorContext ctx) {
		Action<IValue, Type> t = new Action<IValue, Type>() {
			@Override
			public TreeCursor<IValue, Type> apply(Walker<IValue, Type> e) {
				String name = e.getName();
				if (name != null) {
					ctx.getStdOut().print(e.getName() + " ");
				} else {
					ctx.getStdOut().print(e.getData().toString());
				}
				return null;
			}
		};
		Topdown<IValue, Type, BasicWalker<IValue, Type>> topDown = new Topdown<IValue, Type, BasicWalker<IValue, Type>>(
				t);
		Walker<IValue, Type> e = new BasicWalker<IValue, Type>(
				new PdbCursor(n), topDown);
		e.start();

		ctx.getStdOut().println();
		ctx.getStdOut().flush();

		return vf.node("foo");
	}

}
