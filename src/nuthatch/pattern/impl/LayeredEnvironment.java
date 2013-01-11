package nuthatch.pattern.impl;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nuthatch.pattern.Environment;
import nuthatch.tree.Tree;

/**
 * This class is not thread safe.
 * 
 */
public class LayeredEnvironment implements Environment {
	private final LayeredEnvironment chain;
	private Map<String, Tree> map;
	private int childCount;


	public LayeredEnvironment() {
		chain = null;
		childCount = 0;
		map = new HashMap<String, Tree>();
	}


	protected LayeredEnvironment(LayeredEnvironment chain) {
		this.chain = chain;
		chain.childCount++;
		childCount = 0;
		map = new HashMap<String, Tree>();
	}


	@Override
	public Environment enterScope() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		return new LayeredEnvironment(this);
	}


	@Override
	public Environment exitScope() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		if(chain != null) {
			chain.childCount--;
			childCount = -1;
			return chain;
		}
		else {
			return null;
		}
	}


	@Override
	public Environment begin() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		return enterScope();
	}


	@Override
	public Environment rollback() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		return exitScope();
	}


	@Override
	public Environment commit() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		childCount = -1;

		chain.childCount--;
		if(chain.childCount != 0) {
			throw new ConcurrentModificationException("Tried to change an environment that has children");
		}
		chain.map.putAll(map);
		return chain;
	}


	@Override
	public Tree get(String var) {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		LayeredEnvironment env = this;
		while(env != null) {
			if(env.map.containsKey(var)) {
				return env.map.get(var);
			}
			else {
				env = env.chain;
			}
		}

		return null;
	}


	@Override
	public void put(String var, Tree val) {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}
		else if(childCount > 0) {
			throw new ConcurrentModificationException("Tried to change an environment that has children");
		}

		map.put(var, val);
	}


	@Override
	public Iterator<String> iterator() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		throw new UnsupportedOperationException();
	}

}
