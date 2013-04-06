package nuthatch.library;

import nuthatch.walker.Walker;

public interface ActionBuilder<W extends Walker<?, ?>> {
	void add(Action<W> action);


	Action<W> done();
}
