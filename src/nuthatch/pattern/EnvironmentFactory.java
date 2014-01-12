package nuthatch.pattern;

import nuthatch.pattern.impl.LayeredEnvironment;

public class EnvironmentFactory {
	/**
	 * @param valueType
	 *            The type of values in the environment
	 * @return A new environment
	 */
	public static <K, V> Environment< V> env() {
		return new LayeredEnvironment< V>();
	}
}
