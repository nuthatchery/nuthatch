package nuthatch.demo;

import java.io.FileNotFoundException;
import java.io.IOException;

import nuthatch.library.walks.DefaultVisitor;
import nuthatch.library.walks.Visitor;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.Pattern;
import nuthatch.stratego.adapter.StrategoAdapter;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.stratego.adapter.TermWalk;
import nuthatch.stratego.pattern.TermPatternFactory;
import nuthatch.stratego.syntax.StrategoSignatures;
import nuthatch.tree.TreeCursor;
import nuthatch.walk.Walk;

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
		String parseTable = "/home/anya/magnolia/workspace/java-front/syntax/src/Java-15.tbl";

		try {
			IStrategoTerm term = StrategoAdapter.parseString(input, null, parseTable);
			System.err.println(term);
			System.out.println(TermPrinter.termToString(term));
			TermCursor tree = StrategoAdapter.termToTree(term);
			TermPatternFactory pf = TermPatternFactory.getInstance();
			final Pattern<IStrategoTerm, Integer> idPat = pf.appl("Id", pf.string("foo"));

			Visitor<TermWalk> visitor = new DefaultVisitor<TermWalk>() {
				@Override
				public void onEntry(TermWalk e) {
					Environment<TreeCursor<IStrategoTerm, Integer>> env = EnvironmentFactory.env();
					if(idPat.match(e, env)) {
						System.out.println("match!");
					}
				}
			};
			Walk<IStrategoTerm, Integer> e = new TermWalk(tree, visitor);
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

		StrategoSignatures.sig2java(StrategoSignatures.parseSignatureFile("/home/anya/magnolia/workspace/java-front/syntax/src/Java-15.sig"), "nuthatch.stratego.javafront", "Java15");
	}
}
