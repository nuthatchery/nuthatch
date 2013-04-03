package nuthatch.library.walks;

import nuthatch.walk.Step;
import nuthatch.walk.Walk;

public class DownAction<W extends Walk<?, ?>> implements Action<W> {

	private Action<W> action;


	public DownAction(Action<W> action) {
		this.action = action;
	}


	@Override
	public int action(W walk) {
		if(walk.from(Step.PARENT)) {
			return action.action(walk);
		}
		else {
			return PROCEED;
		}
	}
}
