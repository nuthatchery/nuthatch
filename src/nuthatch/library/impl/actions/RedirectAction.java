package nuthatch.library.impl.actions;

import nuthatch.library.Action;
import nuthatch.walker.Walker;

/**
 * Will redirect the next branch taken.
 *
 * @param <W>
 */
final public class RedirectAction<W extends Walker<?, ?>> implements Action<W> {
	private Action<W> action;
	private int[] redirects;


	/**
	 * 
	 * Format of redirections: if next branch is n, then this action will redirect to redirects[n].
	 * 
	 * @param action An action to be taken. Its indicated next branch may be overriden by this action.
	 * @param redirects Array of redirections
	 */
	public RedirectAction(Action<W> action, int[] redirects) {
		this.action = action;
		this.redirects = redirects;

	}

	/**
	 * 
	 * Format of redirections: if next branch is n, then this action will redirect to redirects[n].
	 * 
	 * @param redirects Array of redirections
	 */
	public RedirectAction(int[] redirects) {
		this.action = null;
		this.redirects = redirects;

	}

	@Override
	public void init(W walker) {
		if(action != null) {
			action.init(walker);
		}
	}


	@Override
	public int step(W walker) {
		int r = PROCEED;
		if(action != null) {
			r = action.step(walker);
		}

		int next = walker.next(PROCEED);
		int redirect = next;
		if(next < redirects.length) {
			redirect = redirects[next];
		}
		if(redirect != next) {
			return redirect;
		}
		else {
			return r;
		}
	}
}
