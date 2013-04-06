package nuthatch.rascal;

import nuthatch.library.AbstractAction;
import nuthatch.library.Action;
import nuthatch.library.impl.actions.ActionFactory;
import nuthatch.rascal.adapter.PdbCursor;
import nuthatch.rascal.adapter.UptrCursor;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;
import nuthatch.walker.impl.SimpleWalker;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.interpreter.IEvaluatorContext;

public class Nuthatch {
	private final IValueFactory vf;
	private final ActionFactory<IValue, Type, TreeCursor<IValue, Type>, SimpleWalker<IValue, Type>> pdbAf = ActionFactory.getInstance();
	private final ActionFactory<String, Type, TreeCursor<String, Type>, SimpleWalker<String, Type>> uptrAf = ActionFactory.getInstance();


	public Nuthatch(IValueFactory vf) {
		this.vf = vf;

	}


	public IConstructor engage(IConstructor tree, final IEvaluatorContext ctx) {
		Action<SimpleWalker<String, Type>> t = new AbstractAction<SimpleWalker<String, Type>>() {
			@Override
			public int step(SimpleWalker<String, Type> walker) {
				String name = walker.getName();
				if(name != null) {
					ctx.getStdOut().print(name + " ");
				}
				else {
					ctx.getStdOut().print(walker.getData());
				}
				return PROCEED;
			}
		};
		Action<SimpleWalker<String, Type>> topDown = uptrAf.down(t);
		Walker<String, Type> e = new SimpleWalker<String, Type>(new UptrCursor(tree), uptrAf.walk(topDown));
		e.start();

		ctx.getStdOut().println();
		ctx.getStdOut().flush();

		return tree;
	}


	public INode engage(INode n, final IEvaluatorContext ctx) {
		Action<SimpleWalker<IValue, Type>> t = new AbstractAction<SimpleWalker<IValue, Type>>() {
			@Override
			public int step(SimpleWalker<IValue, Type> e) {
				String name = e.getName();
				if(name != null) {
					ctx.getStdOut().print(e.getName() + " ");
				}
				else {
					ctx.getStdOut().print(e.getData().toString());
				}
				return PROCEED;
			}
		};
		Action<SimpleWalker<IValue, Type>> topDown = pdbAf.down(t);
		Walker<IValue, Type> e = new SimpleWalker<IValue, Type>(new PdbCursor(n), pdbAf.walk(topDown));
		e.start();

		ctx.getStdOut().println();
		ctx.getStdOut().flush();

		return vf.node("foo");
	}

}
