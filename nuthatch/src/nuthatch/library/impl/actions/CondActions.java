package nuthatch.library.impl.actions;

import java.util.Comparator;

import nuthatch.library.Action;
import nuthatch.library.JoinPoints;
import nuthatch.walker.Walker;


final class CondActions {
	static final class AfterChild<W extends Walker<?, ?, W>> extends AbstractIfAction<W> {

		public AfterChild(Action<W> action) {
			super(action);
		}


		@Override
		public boolean cond(W walker) {
			return JoinPoints.afterChild(walker);
		}


		@Override
		public String toString() {
			return "afterChild(" + action + ")";
		}

	}


	static final class BeforeChild<W extends Walker<?, ?, W>> extends AbstractIfAction<W> {

		public BeforeChild(Action<W> action) {
			super(action);
		}


		@Override
		public boolean cond(W walker) {
			return JoinPoints.beforeChild(walker);

		}

		@Override
		public String toString() {
			return "beforeChild(" + action + ")";
		}
	}


	static final class Down<W extends Walker<?, ?, W>> extends AbstractIfAction<W> {
		public Down(Action<W> action) {
			super(action);
		}


		@Override
		public boolean cond(W walker) {
			return JoinPoints.down(walker);
		}
		@Override
		public String toString() {
			return "down(" + action + ")";
		}

	}


	static final class From<W extends Walker<?, ?, W>> extends AbstractIfAction<W> {
		private final int branch;


		public From(int branch, Action<W> action) {
			super(action);
			this.branch = branch;
		}


		@Override
		public boolean cond(W walker) {
			return walker.from(branch);
		}

		@Override
		public String toString() {
			return "from(" + branch + "," + action + ")";
		}

	}


	static final class HasArity<W extends Walker<?, ?, W>> extends AbstractIfAction<W> {
		private final int arity;


		public HasArity(int arity, Action<W> action) {
			super(action);
			this.arity = arity;
		}


		@Override
		public boolean cond(W walker) {
			return walker.getArity() == arity;
		}
		@Override
		public String toString() {
			return "arity(" + arity + "," + action + ")";
		}
	}


	static final class HasType<Type, W extends Walker<?, Type, W>> extends AbstractIfAction<W> {
		private final Type type;


		public HasType(Type type, Action<W> action) {
			super(action);
			this.type = type;
		}


		@Override
		public boolean cond(W walker) {
			Type t = walker.getType();
			return t == type || (t != null && t.equals(type));
		}
		@Override
		public String toString() {
			return "type(" + type + "," + action + ")";
		}
	}


	static final class HasTypeComp<Type, W extends Walker<?, Type, W>> extends AbstractIfAction<W> {
		private final Type type;
		private final Comparator<Type> comp;


		public HasTypeComp(Type type, Comparator<Type> comp, Action<W> action) {
			super(action);
			this.type = type;
			this.comp = comp;
		}


		@Override
		public boolean cond(W walker) {
			return comp.compare(walker.getType(), type) == 0;
		}
		@Override
		public String toString() {
			return "type(" + type + "," + action + ")";
		}
	}


	static final class Leaf<W extends Walker<?, ?, W>> extends AbstractIfAction<W> {
		private final boolean not;


		public Leaf(Action<W> action) {
			super(action);
			not = false;
		}


		public Leaf(Action<W> action, boolean not) {
			super(action);
			this.not = not;
		}


		@Override
		public boolean cond(W walker) {
			return walker.isAtLeaf() ^ not;
		}
		@Override
		public String toString() {
			return (not ? "notLeaf" : "leaf") + "(" + action + ")";
		}
	}


	static final class Root<W extends Walker<?, ?, W>> extends AbstractIfAction<W> {
		public Root(Action<W> action) {
			super(action);
		}


		@Override
		public boolean cond(W walker) {
			return walker.isAtRoot();
		}
		@Override
		public String toString() {
			return "root(" + action + ")";
		}
	}


	static final class Up<W extends Walker<?, ?, W>> extends AbstractIfAction<W> {
		public Up(Action<W> action) {
			super(action);
		}


		@Override
		public boolean cond(W walker) {
			return JoinPoints.up(walker);
		}
		@Override
		public String toString() {
			return "up(" + action + ")";
		}
	}

}
