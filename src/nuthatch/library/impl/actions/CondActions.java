package nuthatch.library.impl.actions;

import java.util.Comparator;

import nuthatch.library.Action;
import nuthatch.library.JoinPoints;
import nuthatch.walker.Walker;


final class CondActions {
	static final class AfterChild<W extends Walker<?, ?>> extends AbstractIfAction<W> {

		public AfterChild(Action<W> action) {
			super(action);
		}


		@Override
		public boolean cond(W walker) {
			return JoinPoints.afterChild(walker);
		}

	}


	static final class BeforeChild<W extends Walker<?, ?>> extends AbstractIfAction<W> {

		public BeforeChild(Action<W> action) {
			super(action);
		}


		@Override
		public boolean cond(W walker) {
			return JoinPoints.beforeChild(walker);

		}

	}


	static final class Down<W extends Walker<?, ?>> extends AbstractIfAction<W> {
		public Down(Action<W> action) {
			super(action);
		}


		@Override
		public boolean cond(W walker) {
			return JoinPoints.down(walker);
		}
	}


	static final class From<W extends Walker<?, ?>> extends AbstractIfAction<W> {
		private final int branch;


		public From(int branch, Action<W> action) {
			super(action);
			this.branch = branch;
		}


		@Override
		public boolean cond(W walker) {
			return walker.from(branch);
		}
	}


	static final class HasArity<W extends Walker<?, ?>> extends AbstractIfAction<W> {
		private final int arity;


		public HasArity(int arity, Action<W> action) {
			super(action);
			this.arity = arity;
		}


		@Override
		public boolean cond(W walker) {
			return walker.getArity() == arity;
		}
	}


	static final class HasType<Type, W extends Walker<?, Type>> extends AbstractIfAction<W> {
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
	}


	static final class HasTypeComp<Type, W extends Walker<?, Type>> extends AbstractIfAction<W> {
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
	}


	static final class Leaf<W extends Walker<?, ?>> extends AbstractIfAction<W> {
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
	}


	static final class Root<W extends Walker<?, ?>> extends AbstractIfAction<W> {
		public Root(Action<W> action) {
			super(action);
		}


		@Override
		public boolean cond(W walker) {
			return walker.isAtRoot();
		}
	}


	static final class Up<W extends Walker<?, ?>> extends AbstractIfAction<W> {
		public Up(Action<W> action) {
			super(action);
		}


		@Override
		public boolean cond(W walker) {
			return JoinPoints.up(walker);
		}
	}

}
