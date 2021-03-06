package nuthatch.pattern.impl;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nuthatch.pattern.Environment;

/**
 * This class is not thread safe.
 * 
 */
public class LayeredEnvironment<V> implements Environment<V> {
	private final LayeredEnvironment<V> chain;
	private Map<Object, V> map;
	private List<Map<Object, V>> stack;
	private int childCount;


	public LayeredEnvironment() {
		chain = null;
		childCount = 0;
		map = null;
	}


	protected LayeredEnvironment(LayeredEnvironment<V> chain) {
		this.chain = chain;
		chain.childCount++;
		childCount = 0;
		map = null;
	}


	@Override
	public void begin() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		push();
	}


	@Override
	public void commit() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		Map<Object, V> pop = pop();
		if(pop != null) {
			if(map == null) {
				map = new HashMap<Object, V>();
			}
			map.putAll(pop);
		}
	}


	@Override
	public Environment<V> enterScope() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		return new LayeredEnvironment<V>(this);
	}


	@Override
	public Environment<V> exitScope() {
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
	public <K> V get(K var) {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		LayeredEnvironment<V> env = this;
		while(env != null) {
			if(env.map != null && env.map.containsKey(var)) {
				return env.map.get(var);
			}
			else {
				if(env.stack != null) {
					for(Map<Object, V> m : env.stack) {
						if(m != null && m.containsKey(var)) {
							return m.get(var);
						}
					}
				}
				env = env.chain;
			}
		}
		return null;
	}


	@Override
	public boolean isEmpty() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		LayeredEnvironment<V> env = this;
		while(env != null) {
			if(env.map != null && !env.map.isEmpty()) {
				return false;
			}
			else {
				if(env.stack != null) {
					for(Map<Object, V> m : env.stack) {
						if(m != null && !m.isEmpty()) {
							return false;
						}
					}
				}
				env = env.chain;
			}
		}
		return true;
	}


	@Override
	public boolean isEmptyScope() {
		if(map != null && !map.isEmpty()) {
			return false;
		}
		else {
			if(stack != null) {
				for(Map<Object, V> m : stack) {
					if(m != null && !m.isEmpty()) {
						return false;
					}
				}
			}
		}
		return true;
	}


	@Override
	public Iterator<Object> iterator() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		throw new UnsupportedOperationException();
	}


	@Override
	public <K> void put(K var, V val) {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}
		else if(childCount > 0) {
			throw new ConcurrentModificationException("Tried to change an environment that has children");
		}

		if(map == null) {
			map = new HashMap<Object, V>();
		}

		map.put(var, val);
	}


	@Override
	public void rollback() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		map = pop();
	}


	protected Map<Object, V> pop() {
		if(stack == null) {
			throw new RuntimeException("Trying to end a transaction before any was started");
		}
		else if(stack.isEmpty()) {
			throw new RuntimeException("Trying to end a transaction while none were active");
		}
		else {
			return stack.remove(stack.size() - 1);
		}

	}


	protected void push() {
		if(stack == null) {
			stack = new ArrayList<Map<Object, V>>();
		}
		stack.add(map);
		map = null;
	}

}
