package nuthatch.pattern;

import nuthatch.pattern.impl.LayeredEnvironment;

public class EnvironmentFactory {
	public static Environment env() {
		return new LayeredEnvironment();
	}
}
