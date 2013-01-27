package nuthatch.demo;

import java.io.FileNotFoundException;
import java.io.IOException;

import nuthatch.engine.Engine;
import nuthatch.engine.impl.StrategicEngine;
import nuthatch.library.strategies.TopdownStrategy;
import nuthatch.stratego.adapter.StrategoAdapter;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.strategy.Transform;
import nuthatch.tree.TreeCursor;


import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.jsglr.client.InvalidParseTableException;
import org.spoofax.jsglr.client.ParseException;
import org.spoofax.jsglr.shared.BadTokenException;
import org.spoofax.jsglr.shared.SGLRException;
import org.spoofax.jsglr.shared.TokenExpectedException;

public class Main {

	public static void main(String[] args) {
		String inputFile = "";
		String input = "package foo; public class Main { }";
		String parseTable = "/home/anya/magnolia/workspace/java-front/syntax/src/Java-15.tbl";
		
		try {
			IStrategoTerm term = StrategoAdapter.parseString(input, null, parseTable);
			TermCursor tree = StrategoAdapter.termToTree(term);
			
			Transform<IStrategoTerm, Integer> t = new Transform<IStrategoTerm, Integer>() {
				@Override
				public TreeCursor<IStrategoTerm, Integer> apply(Engine<IStrategoTerm, Integer> e) {
					String name = e.getName();
					if(name != null) {
						System.out.print(e.getName() + " ");
					} else {
						System.out.print(e.getData().toString());
					}
					return null;
				}
			};
			TopdownStrategy<IStrategoTerm, Integer> topDown = new TopdownStrategy<IStrategoTerm, Integer>(t);
			Engine<IStrategoTerm, Integer> e = new StrategicEngine<IStrategoTerm, Integer>(tree, topDown);
			e.engage();

		} catch (TokenExpectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidParseTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SGLRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
