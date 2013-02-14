package nuthatch.library.walks;

import nuthatch.walk.Walk;

public class DefaultVisitor<W extends Walk<?, ?>> extends Visitor<W> {

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
