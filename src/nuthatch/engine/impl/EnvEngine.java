package nuthatch.engine.impl;

import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.VarName;
import nuthatch.strategy.Strategy;
import nuthatch.tree.TreeCursor;

public class EnvEngine<Value, Type, E extends EnvEngine<Value, Type, E>> extends RegisteredEngine<Value, Type, E> {
	public Environment<TreeCursor<Value, Type>> env = null;


	public EnvEngine(TreeCursor<Value, Type> cursor, Strategy<E> strat) {
		super(cursor, strat);
	}


	@Override
	public void engage() {
		env = EnvironmentFactory.env();
		super.engage();
	}


	public TreeCursor<Value, Type> getGlobalVar(String name) {
		return null;
	}


	public <T> T getGlobalVar(VarName<T> name) {
		return null;
	}


	public TreeCursor<Value, Type> getScopedVar(String name) {
		return null;
	}


	public <T> T getScopedVar(VarName<T> name) {
		return null;
	}


	public TreeCursor<Value, Type> getSubtreeVar(String name) {
		return null;
	}


	public <T> T getSubtreeVar(VarName<T> name) {
		return null;
	}


	public void setGlobalVar(String name, TreeCursor<Value, Type> tree) {

	}


	public <T> void setGlobalVar(VarName<T> name, T value) {

	}


	public void setScopedVar(String name, TreeCursor<Value, Type> tree) {

	}


	public <T> void setScopedVar(VarName<T> name, T value) {

	}


	public void setSubtreeVar(String name, TreeCursor<Value, Type> tree) {

	}


	public <T> void setSubtreeVar(VarName<T> name, T value) {

	}

}
