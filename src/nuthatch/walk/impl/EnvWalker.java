package nuthatch.walk.impl;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.VarName;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.errors.BranchNotFoundError;
import nuthatch.tree.util.BranchUtil;
import nuthatch.walk.Walk;

public class EnvWalker<Value, Type, E extends EnvWalker<Value, Type, E>> extends AbstractVarWalker<Value, Type, E> {
	private Environment<Object> env = null;
	private List<Map<String, Object>> subtreeScoping;
	private Map<String, Object> subtreeVars;
	private Map<String, Object> globalVars;


	public EnvWalker(TreeCursor<Value, Type> cursor, Walk<E> strat) {
		super(cursor, strat);
		clear();
	}


	/**
	 * Clear all variables.
	 * 
	 * Note that engine state is *not* automatically cleared when engage() is
	 * called or when it returns. This means that variables can be assigned
	 * values before the engine starts, and that the values can be inspected
	 * after it completes. If the engine is to be engaged again, calling clear()
	 * may be necessary.
	 * 
	 * @throws ConcurrentModificationException
	 *             if the engine is currently running
	 */
	public void clear() throws ConcurrentModificationException {
		if(isRunning()) {
			throw new ConcurrentModificationException();
		}
		env = EnvironmentFactory.env();
		globalVars = new IdentityHashMap<String, Object>();
		subtreeVars = new IdentityHashMap<String, Object>();
		subtreeScoping = new ArrayList<Map<String, Object>>();
	}


	public void enterScope() {
		env.enterScope();
	}


	public void exitScope() {
		env.exitScope();
	}


	public <T> T getGlobalVar(VarName<T> name) {
		return (T) globalVars.get(name.getName());
	}


	public <T> T getScopedVar(VarName<T> name) {
		return (T) env.get(name.getName());
	}


	public <T> T getSubtreeVar(VarName<T> name) {
		return (T) subtreeVars.get(name.getName());
	}


	@Override
	public TreeCursor<Value, Type> go(int i) throws BranchNotFoundError {
		int j = BranchUtil.normalBranch(i, getNumChildren());
		if(j == 0) {
			int k = subtreeScoping.size();
			if(k > 0) {
				Map<String, Object> map = subtreeScoping.remove(k - 1);
				if(map != null) {
					for(Entry<String, Object> entry : map.entrySet()) {
						if(entry.getValue() == null) {
							subtreeVars.remove(entry.getKey());
						}
						else {
							subtreeVars.put(entry.getKey(), entry.getValue());
						}
					}
				}
			}
		}
		else if(j > 0) {
			subtreeScoping.add(null);
		}
		else {
			throw new BranchNotFoundError(Integer.toString(i));
		}

		return super.go(j);
	}


	public <T> void setGlobalVar(VarName<T> name, T value) {
		globalVars.put(name.getName(), value);
	}


	public <T> void setScopedVar(VarName<T> name, T value) {
		env.put(name.getName(), value);
	}


	public <T> void setSubtreeVar(VarName<T> name, T value) {
		if(subtreeScoping.size() == 0) {
			subtreeScoping.add(null);
		}
		Map<String, Object> map = subtreeScoping.get(subtreeScoping.size() - 1);

		String n = name.getName();
		if(map == null) {
			map = new IdentityHashMap<>();
			subtreeScoping.set(subtreeScoping.size() - 1, map);
		}
		if(!map.containsKey(n)) {
			// store old value if this is a new variable at this subtree
			map.put(n, subtreeVars.get(n));
		}

		subtreeVars.put(name.getName(), value);
	}


	@SuppressWarnings("unchecked")
	@Override
	protected E subWalk(TreeCursor<Value, Type> cursor, Walk<E> step) {
		return (E) new EnvWalker<Value, Type, E>(cursor, step);
	}
}
