package nuthatch.walk.impl;

import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Walk;

/**
 * This engine variant adds a few state registers.
 * 
 * This class is intended to be subclassed, with the subclass given as the E
 * parameter.
 * 
 * @author anya
 * 
 * @param <Value>
 *            The value type
 * @param <Type>
 *            The type type
 * @param <E>
 *            The type of the implemented engine
 */
public abstract class AbstractVarWalker<Value, Type, E extends AbstractVarWalker<Value, Type, E>> extends AbstractWalker<Value, Type, E> {
	protected StringBuilder sReg;
	protected int xReg, yReg;
	protected Object zReg;


	public AbstractVarWalker(Tree<Value, Type> tree, Walk<E> strat) {
		super(tree, strat);
	}


	public AbstractVarWalker(TreeCursor<Value, Type> cursor, Walk<E> strat) {
		super(cursor, strat);
	}


	/**
	 * Add to X register.
	 * 
	 * @param value
	 *            The integer value to be added
	 */
	public void addToX(int value) {
		xReg += value;
	}


	/**
	 * Add to Y register.
	 * 
	 * @param value
	 *            The integer value to be added
	 */
	public void addToY(int value) {
		yReg += value;
	}


	/**
	 * Append to S register.
	 * 
	 * If the register is previously unset, it is initalised to an empty string.
	 * 
	 * You may assume that the append operation is reasonably fast, e.g. using a
	 * StringBuilder internally.
	 * 
	 * @param s
	 *            A string to be appended
	 */
	public void appendToS(String s) {
		if(sReg == null) {
			sReg = new StringBuilder();
		}
		sReg.append(s);
	}


	/**
	 * Get contents of S register
	 * 
	 * @return The string contained in the S register
	 */
	public String getS() {
		return sReg == null ? null : sReg.toString();
	}


	/**
	 * Get contents of X register
	 * 
	 * @return The integer value
	 */
	public int getX() {
		return xReg;
	}


	/**
	 * Get contents of Y register
	 * 
	 * @return The integer value
	 */
	public int getY() {
		return yReg;
	}


	/**
	 * Get contents of Z register
	 * 
	 * @return The data value
	 */
	public Object getZ() {
		return zReg;
	}


	/**
	 * Set the string in the S register
	 * 
	 * The string may later be added to using {@link #appendToS(String)}.
	 * 
	 * @param s
	 *            A string
	 */
	public void setS(String s) {
		sReg = new StringBuilder(s);
	}


	/**
	 * Set contents of X register
	 * 
	 * @param value
	 *            The integer value
	 */
	public void setX(int value) {
		xReg = value;
	}


	/**
	 * Set contents of Y register
	 * 
	 * @param value
	 *            The integer value
	 */
	public void setY(int value) {
		yReg = value;
	}


	/**
	 * Set contents of Z register
	 * 
	 * Note that only the reference is stored; if the object is changed
	 * elsewhere, the change will be reflected by {@link #getZ()}.
	 * 
	 * @param value
	 *            The data value
	 */
	public void setZ(Object value) {
		zReg = value;
	}

}
