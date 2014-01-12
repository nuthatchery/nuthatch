package nuthatch.pattern;

import nuthatch.pattern.impl.LayeredEnvironment;

public class EnvironmentFactory {
	/**
	 * @param valueType
	 *            The type of values in the environment
	 * @return A new environment
	 */
	public static <K, V> Environment<K, V> env() {
		return new LayeredEnvironment<K, V>();
	}
}
