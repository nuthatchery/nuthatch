package nuthatch.pattern;

import nuthatch.pattern.impl.LayeredEnvironment;

public class EnvironmentFactory {
	/**
	 * @param valueType
	 *            The type of values in the environment
	 * @return A new environment
	 */
	public static <T> Environment<T> env() {
		return new LayeredEnvironment<T>();
	}
}
