package nuthatch.library.walks;

import nuthatch.walker.Walker;

public class DefaultVisitor<W extends Walker<?, ?>> extends Visitor<W> {

	@Override
	public void afterChild(W walker, int child) {
	}


	@Override
	public void beforeChild(W walker, int child) {
	}


	@Override
	public void onEntry(W walker) {
	}


	@Override
	public void onExit(W walker) {
	}

}
