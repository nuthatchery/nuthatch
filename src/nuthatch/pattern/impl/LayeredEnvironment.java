package nuthatch.pattern.impl;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	private List<Map<String, Tree>> stack;
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
	public void begin() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		push();
	}


	@Override
	public void rollback() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		map = pop();
	}


	@Override
	public void commit() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		Map<String, Tree> pop = pop();
		map.putAll(pop);
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
				if(env.stack != null) {
					for(Map<String, Tree> m : env.stack) {
						if(m.containsKey(var)) {
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


	protected void push() {
		if(stack == null) {
			stack = new ArrayList<Map<String, Tree>>();
		}
		stack.add(map);
		map = new HashMap<String, Tree>();
	}


	protected Map<String, Tree> pop() {
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


	@Override
	public boolean isEmpty() {
		if(childCount < 0) {
			throw new RuntimeException("This environment has been abandoned!");
		}

		LayeredEnvironment env = this;
		while(env != null) {
			if(!env.map.isEmpty()) {
				return false;
			}
			else {
				if(env.stack != null) {
					for(Map<String, Tree> m : env.stack) {
						if(!m.isEmpty()) {
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
		if(!map.isEmpty()) {
			return false;
		}
		else {
			if(stack != null) {
				for(Map<String, Tree> m : stack) {
					if(!m.isEmpty()) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
