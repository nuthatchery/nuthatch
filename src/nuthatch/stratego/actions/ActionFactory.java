package nuthatch.stratego.actions;

import java.util.HashMap;
import java.util.Map;

import nuthatch.library.walks.Action;
import nuthatch.library.walks.DownAction;
import nuthatch.library.walks.MatchedAction;
import nuthatch.library.walks.MatchingAction;
import nuthatch.pattern.Pattern;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.stratego.adapter.TermWalk;
import nuthatch.walk.Step;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class ActionFactory {

	public static Step<TermWalk> defaultStep(final Action<TermWalk> action) {
		return new Step<TermWalk>() {
			@Override
			public int step(TermWalk walker) {
				int r = action.action(walker);
				if(r != Action.PROCEED) {
					return r;
				}
				else {
					return NEXT;
				}
			}
		};
	}


	public static Action<TermWalk> ifDown(Action<TermWalk> action) {
		return new DownAction<>(action);
	}


	public static Action<TermWalk> ifMatches(Pattern<IStrategoTerm, Integer> pat, MatchedAction<IStrategoTerm, Integer, TermCursor, TermWalk> action) {
		return new MatchingAction<>(pat, action);
	}


	public static MatchSwitchBuilder matchSwitchBuilder() {
		return new MatchSwitchBuilder();
	}


	public static class MatchSwitchBuilder {
		Map<Pattern<IStrategoTerm, Integer>, MatchedAction<IStrategoTerm, Integer, TermCursor, TermWalk>> map = new HashMap<>();


		public void add(Pattern<IStrategoTerm, Integer> pat, MatchedAction<IStrategoTerm, Integer, TermCursor, TermWalk> action) {
			map.put(pat, action);
		}


		public Action<TermWalk> done() {
			return new MatchingAction<>(map);
		}
	}
}
