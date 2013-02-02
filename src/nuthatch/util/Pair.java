package nuthatch.util;

/**
 * A simple Pair class
 * 
 * @param <T>
 *            Type of first element
 * @param <U>
 *            Type of second element
 */
public class Pair<T, U> {

	private final T first;
	private final U second;


	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		Pair other = (Pair) obj;
		if(first == null) {
			if(other.first != null) {
				return false;
			}
		}
		else if(!first.equals(other.first)) {
			return false;
		}
		if(second == null) {
			if(other.second != null) {
				return false;
			}
		}
		else if(!second.equals(other.second)) {
			return false;
		}
		return true;
	}


	/**
	 * @return First element of pair
	 */
	public T getFirst() {
		return first;
	}


	/**
	 * @return Second element of pair
	 */
	public U getSecond() {
		return second;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (first == null ? 0 : first.hashCode());
		result = prime * result + (second == null ? 0 : second.hashCode());
		return result;
	}

}
