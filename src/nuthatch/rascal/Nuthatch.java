package nuthatch.rascal;

import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.interpreter.IEvaluatorContext;


public class Nuthatch {
	private final IValueFactory vf;

	public Nuthatch(IValueFactory vf) {
		this.vf = vf;
		
	}
	
	public INode engage(INode n, IEvaluatorContext ctx) {
		System.err.println(n);
		return n;
	}
}
