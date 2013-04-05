package nuthatch.library.impl.actions;

import nuthatch.walk.Action;
import nuthatch.walk.Walker;

final class SequenceBuilder<W extends Walker<?, ?>> extends AbstractComposeBuilder<W> {
	@Override
	public Action<W> done() {
		return new Combine<W>(actions);
	}
}
