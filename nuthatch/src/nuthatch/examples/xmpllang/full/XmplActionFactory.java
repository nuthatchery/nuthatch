package nuthatch.examples.xmpllang.full;

import nuthatch.examples.xmpllang.Expr;
import nuthatch.examples.xmpllang.Type;
import nuthatch.examples.xmpllang.Var;
import nuthatch.examples.xmpllang.XmplNode;
import nuthatch.library.MatchBuilder;
import nuthatch.library.impl.actions.StandardActionFactory;
import nuthatch.pattern.BuildContext;


public class XmplActionFactory extends StandardActionFactory<XmplNode, Type, XmplCursor, XmplWalker> {
	public static XmplActionFactory actionFactory = new XmplActionFactory();
	public static BuildContext<XmplNode, Type, XmplCursor> exprBuildContext = new BuildContext<XmplNode, Type, XmplCursor>() {

		@Override
		public XmplCursor create(String name, Type type, XmplNode value, XmplCursor[] children) {
			if(children.length == 2) {
				switch(name) {
				case "Add":
					return new XmplCursor(XmplPatterns.Add((Expr) children[0].getData(), (Expr) children[1].getData()));
				case "Seq":
					return new XmplCursor(XmplPatterns.Mul((Expr) children[0].getData(), (Expr) children[1].getData()));
				default:
					throw new RuntimeException("No binary constructor with name " + name);
				}
			}
			else if(children.length == 3) {
				switch(name) {
				case "Let":
					return new XmplCursor(XmplPatterns.Let((Var) children[0].getData(), (Expr) children[1].getData(), (Expr) children[2].getData()));
				default:
					throw new RuntimeException("No ternary constructor with name " + name);
				}
			}
			else {
				throw new RuntimeException("No " + children.length + "-ary constructor with name " + name);
			}
		}

	};


	public MatchBuilder<XmplNode, Type, XmplCursor, XmplWalker> matchBuilder() {
		return actionFactory.matchBuilder(exprBuildContext);
	}


	@SuppressWarnings("unchecked")
	public static XmplActionFactory getInstance() {
		return actionFactory;
	}

}
