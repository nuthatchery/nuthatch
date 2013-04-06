package nuthatch.stratego.actions;

import nuthatch.library.Action;
import nuthatch.stratego.adapter.TermWalk;

public abstract class SAction implements Action<TermWalk> {

	@Override
	public void init(TermWalk walker) {
	}
}
