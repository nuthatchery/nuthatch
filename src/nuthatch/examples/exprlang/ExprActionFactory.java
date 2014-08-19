package nuthatch.examples.exprlang;

import nuthatch.library.MatchBuilder;
import nuthatch.library.impl.actions.StandardActionFactory;
import nuthatch.pattern.BuildContext;

public class ExprActionFactory extends StandardActionFactory<Expr, Type, ExprCursor, ExprWalker> {
	public static ExprActionFactory actionFactory = new ExprActionFactory();
	public static BuildContext<Expr, Type, ExprCursor> exprBuildContex = new BuildContext<Expr, Type, ExprCursor>() {

		@Override
		public ExprCursor create(String name, Type type, Expr value, ExprCursor[] children) {
			if(children.length == 2) {
				switch(name) {
				case "Add":
					return new ExprCursor(ExprPatterns.Add(children[0].getData(), children[1].getData()));
				case "Mul":
					return new ExprCursor(ExprPatterns.Mul(children[0].getData(), children[1].getData()));
				default:
					throw new RuntimeException("No binary constructor with name " + name);
				}
			}
			else if(children.length == 3) {
				switch(name) {
				case "Let":
					return new ExprCursor(ExprPatterns.Let((Var) children[0].getData(), children[1].getData(), children[2].getData()));
				default:
					throw new RuntimeException("No ternary constructor with name " + name);
				}
			}
			else {
				throw new RuntimeException("No " + children.length + "-ary constructor with name " + name);
			}
		}

	};


	public MatchBuilder<Expr, Type, ExprCursor, ExprWalker> matchBuilder() {
		return actionFactory.matchBuilder(exprBuildContex);
	}


	@SuppressWarnings("unchecked")
	public static ExprActionFactory getInstance() {
		return actionFactory;
	}

}
