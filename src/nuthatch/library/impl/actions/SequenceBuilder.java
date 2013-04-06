package nuthatch.library.impl.actions;

import nuthatch.library.Action;
import nuthatch.walker.Walker;

final class SequenceBuilder<W extends Walker<?, ?>> extends AbstractComposeBuilder<W> {
	@Override
	public Action<W> done() {
		return new Combine<W>(actions);
	}
}
