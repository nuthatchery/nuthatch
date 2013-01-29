package nuthatch.engine.impl;

import nuthatch.strategy.Strategy;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;

public abstract class RegisteredEngine<Value, Type, E extends RegisteredEngine<Value, Type, E>> extends StrategicEngine<Value, Type, E> {
	protected StringBuilder sReg;
	protected int xReg, yReg;
	protected Object zReg;


	public RegisteredEngine(Tree<Value, Type> tree, Strategy<E> strat) {
		super(tree, strat);
	}


	public RegisteredEngine(TreeCursor<Value, Type> cursor, Strategy<E> strat) {
		super(cursor, strat);
	}


	public void appendToS(String s) {
		if(sReg == null) {
			sReg = new StringBuilder();
		}
		sReg.append(s);
	}


	public void addToX(int value) {
		xReg += value;
	}


	public void addToY(int value) {
		yReg += value;
	}


	public String getS() {
		return sReg.toString();
	}


	public int getX() {
		return xReg;
	}


	public int getY() {
		return yReg;
	}


	public Object getZ() {
		return zReg;
	}


	public void setS(String s) {
		sReg = new StringBuilder(s);
	}


	public void setX(int value) {
		xReg = value;
	}


	public void setY(int value) {
		yReg = value;
	}


	public void setZ(Object value) {
		zReg = value;
	}

}
