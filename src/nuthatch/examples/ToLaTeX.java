package nuthatch.examples;

import java.util.ArrayList;
import java.util.List;

import nuthatch.library.impl.actions.AbstractComposeWalk;
import nuthatch.library.impl.actions.ActionFactory;
import nuthatch.tree.Tree;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Action;
import nuthatch.walk.Walk;
import nuthatch.walk.impl.SimpleWalker;

public class ToLaTeX {
	private static final int MODE_WALK = 1 << 0;

	private static final int MODE_VISIT = 1 << 1;
	private static final int MODE_PREAMBLE = 1 << 2;
	private static final ActionFactory<String, String, TreeCursor<String, String>, SimpleWalker<String, String>> AF = ActionFactory.getInstance();


	public static void main(String[] args) {
		int mode = 0;
		TraceVisit traceVisit = new TraceVisit();
		Walk<SimpleWalker<String, String>> bottomup = AF.walk(AF.up(traceVisit));
		Walk<SimpleWalker<String, String>> topdown = AF.walk(AF.down(traceVisit));
		Walk<SimpleWalker<String, String>> strat = topdown;
		int i = 0;
		while(i < args.length) {
			switch(args[i++]) {
			case "-p":
				mode |= MODE_PREAMBLE;
				break;
			case "-td":
				strat = topdown;
				break;
			case "-bu":
				strat = bottomup;
				break;
			//case "-in":
			//	strat = inorder;
			//	break;
			case "-w":
				mode |= MODE_WALK;
				break;
			case "-v":
				mode |= MODE_VISIT;
				break;
			case "-h":
				System.err.println("usage: ToLaTeX [-p] [-w|-v] [-td|-bu|-in]");
				System.err.println("    -p     Include LaTeX preamble/postamble");
				System.err.println("    -w     Trace the walk");
				System.err.println("    -v     Trace the order of visits");
				System.err.println("    -td    Use topdown strategy (default)");
				System.err.println("    -bu    Use bottomup strategy");
				//System.err.println("    -in    Use inorder strategy");
				System.err.println("You should specify at least one of -v and -w, or you'll get just the tree");
				System.exit(1);
				break;
			default:
				System.err.println("Unknown option " + args[i]);
				System.exit(1);
			}
		}

		if((mode & MODE_PREAMBLE) != 0) {
			System.out.println("\\documentclass{minimal}");
			System.out.println("\\usepackage[a4paper,margin=1cm,landscape]{geometry}");
			System.out.println("\\usepackage{tikz}");
			System.out.println("\\usetikzlibrary{positioning,shadows,arrows}");
			System.out.println("");
			System.out.println("\\begin{document}");
			System.out.println("\\begin{center}");
		}

		System.out.println("\\begin{tikzpicture}[");
		System.out.println("    level distance=0.5cm, growth parent anchor=south");
		System.out.println("]");
		// draw the tree itself
		System.out.print("\\");
		SimpleWalker<String, String> treePrinter = new SimpleWalker<String, String>(ExampleTree.TREE.makeCursor(), new ToTikzWalk());
		treePrinter.start();
		System.out.println(treePrinter.getS());
		System.out.println(";");

		// we should output commands for drawing the walk
		if((mode & MODE_WALK) != 0) {
			System.out.println(traceWalk(ExampleTree.TREE.makeCursor(), topdown));
		}

		// we should output commands for drawing the order of visiting nodes
		if((mode & MODE_VISIT) != 0) {
			SimpleWalker<String, String> walker = new SimpleWalker<String, String>(ExampleTree.TREE.makeCursor(), strat);
			walker.start();
			System.out.println(visitsToString(traceVisit.getResult()));

		}
		System.out.println("\\end{tikzpicture}");

		if((mode & MODE_PREAMBLE) != 0) {
			System.out.println("\\end{center}");
			System.out.println("\\end{document}");
		}

	}


	/**
	 * Trace the walker's path in a tree.
	 * 
	 * The output must be places inside the same tikzpicture as the tree. LaTeX
	 * definitions for \\down and \\up must be provided.
	 * 
	 * @param tree
	 *            The tree we're tracing
	 * @param step
	 *            A step function
	 * @return A series of up and down commands tracing the walk produced by a
	 *         walker running the step function
	 */
	public static String traceWalk(TreeCursor<String, String> tree, final Walk<SimpleWalker<String, String>> step) {
		SimpleWalker<String, String> walkTracingWalker = new SimpleWalker<String, String>(tree, new AbstractComposeWalk<SimpleWalker<String, String>>(step) {
			@Override
			public int step(SimpleWalker<String, String> w) {
				TreeCursor<String, String> prev = w.getBranch(w.from());
				int branch = w.getFromBranch();
				if(w.from(Tree.PARENT)) {
					if(!w.isRoot()) {
						w.appendToS("\\down{" + prev.getPathId() + "}{" + branch + "}{" + w.getPathId() + "}\n");
					}
				}
				else {
					w.appendToS("\\up{" + prev.getPathId() + "}{" + w.from() + "}{" + w.getPathId() + "}\n");
				}
				return super.step(w);
			}
		});
		walkTracingWalker.start();
		return walkTracingWalker.getS();
	}


	/**
	 * Turn a list of visits into LaTeX commands
	 * 
	 * @param visits
	 *            The list of nodes that have been visited
	 * @return LaTeX commands
	 */
	private static String visitsToString(List<String> visits) {
		StringBuilder b = new StringBuilder();
		if(!visits.isEmpty()) {
			int i = 0;
			String last = visits.get(i++);
			while(i < visits.size()) {
				String current = visits.get(i++);
				b.append("\\visit{" + last + "}{" + current + "}\n");
				last = current;
			}
		}
		return b.toString();
	}


	protected static void spaces(SimpleWalker<String, String> walker) {
		for(int i = 0; i < walker.depth(); i++) {
			walker.appendToS("  ");
		}
	}


	/**
	 * An action that adds the current node to a list of visited nodes
	 * 
	 * @return An action, suitable for use in a step function
	 */
	private static final class TraceVisit implements Action<SimpleWalker<String, String>> {
		private ArrayList<String> visits;


		public List<String> getResult() {
			return visits;
		}


		@Override
		public void init(SimpleWalker<String, String> walker) {
			visits = new ArrayList<String>();
		}


		@Override
		public int step(SimpleWalker<String, String> walker) {
			visits.add(walker.getPathId());
			return PROCEED;
		}
	}
}
