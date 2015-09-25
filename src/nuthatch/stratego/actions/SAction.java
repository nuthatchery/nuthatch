package nuthatch.stratego.actions;

import nuthatch.library.Action;
import nuthatch.stratego.adapter.SWalker;

public abstract class SAction implements Action<SWalker> {

	@Override
	public void init(SWalker walker) {
	}
}
