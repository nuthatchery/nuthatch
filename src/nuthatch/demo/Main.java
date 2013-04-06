package nuthatch.demo;

import static nuthatch.stratego.actions.SActionFactory.down;
import static nuthatch.stratego.actions.SActionFactory.walk;

import java.io.FileNotFoundException;
import java.io.IOException;

import nuthatch.library.AbstractAction;
import nuthatch.library.Walk;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.Pattern;
import nuthatch.stratego.adapter.StrategoAdapter;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.stratego.adapter.TermWalk;
import nuthatch.stratego.pattern.TermPatternFactory;
import nuthatch.tree.TreeCursor;
import nuthatch.walker.Walker;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.jsglr.client.InvalidParseTableException;
import org.spoofax.jsglr.client.ParseException;
import org.spoofax.jsglr.shared.BadTokenException;
import org.spoofax.jsglr.shared.SGLRException;
import org.spoofax.jsglr.shared.TokenExpectedException;

public class Main {

	public static void main(String[] args) throws IOException, InvalidParseTableException, SGLRException {
		String inputFile = "";
		String input = "package foo; public class Main { void f() throws Foo { throw new Foo(); } void g() { throw new Bar(); } }";

		String parseTable = "/home/anya/git/nuthatch-javafront/src/nuthatch/javafront/syntax/Java-15.tbl";

		try {
			IStrategoTerm term = StrategoAdapter.parseString(input, null, parseTable);
			System.err.println(term);
			System.out.println(TermPrinter.termToString(term));
			TermCursor tree = StrategoAdapter.termToTree(term);
			TermPatternFactory pf = TermPatternFactory.getInstance();
			final Pattern<IStrategoTerm, Integer> idPat = pf.appl("Id", pf.string("foo"));

			Walk<TermWalk> walk = walk(down(new AbstractAction<TermWalk>() {
				@Override
				public int step(TermWalk e) {
					Environment<TreeCursor<IStrategoTerm, Integer>> env = EnvironmentFactory.env();
					if(idPat.match(e, env)) {
						System.out.println("match!");
					}
					return PROCEED;
				}
			}));
			Walker<IStrategoTerm, Integer> e = new TermWalk(tree, walk);
			e.start();

		}
		catch(TokenExpectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(BadTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(InvalidParseTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(SGLRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
