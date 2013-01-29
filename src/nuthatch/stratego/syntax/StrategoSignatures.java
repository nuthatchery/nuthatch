package nuthatch.stratego.syntax;

import java.io.IOException;
import java.io.InputStream;

import nuthatch.engine.Engine;
import nuthatch.engine.impl.StrategicEngine;
import nuthatch.library.strategies.Visitor;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.Pattern;
import nuthatch.stratego.adapter.StrategoAdapter;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.stratego.pattern.TermPatternFactory;
import nuthatch.tree.TreeCursor;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.jsglr.client.InvalidParseTableException;
import org.spoofax.jsglr.client.ParseException;
import org.spoofax.jsglr.client.ParseTable;
import org.spoofax.jsglr.shared.BadTokenException;
import org.spoofax.jsglr.shared.SGLRException;
import org.spoofax.jsglr.shared.TokenExpectedException;

public class StrategoSignatures {
	
	private static ParseTable signaturesParseTable;

	public static IStrategoTerm parseSignatureString(String signature) throws IOException, InvalidParseTableException, SGLRException {
		return StrategoAdapter.parseString(signature, "", getSignaturesParseTable());
	}
	
	public static IStrategoTerm parseSignatureFile(String filePath) throws TokenExpectedException, BadTokenException, ParseException, SGLRException, IOException, InvalidParseTableException {
		return StrategoAdapter.parseFile(filePath, getSignaturesParseTable());
	}
	
	private static ParseTable getSignaturesParseTable() throws IOException, InvalidParseTableException  {
		if(signaturesParseTable == null) {
			InputStream stream = StrategoSignatures.class.getResourceAsStream("Stratego-Signatures.tbl");
			signaturesParseTable = StrategoAdapter.getParseTableManager().loadFromStream(stream);
		}
		
		return signaturesParseTable;
	}
	
	public static String sig2java(IStrategoTerm signature) {
		TermCursor tree = StrategoAdapter.termToTree(signature);
		
		TermPatternFactory pf = TermPatternFactory.getInstance();
		
		final Pattern<IStrategoTerm, Integer> opDeclPat = pf.appl("OpDecl", pf.var("name"), pf.var("def"));
		Visitor<IStrategoTerm, Integer> visitor = new Visitor<IStrategoTerm, Integer>() {
			@Override
			public void onEntry(Engine<IStrategoTerm, Integer> e) {
				Environment<TreeCursor<IStrategoTerm, Integer>> env = EnvironmentFactory.env();
				if(opDeclPat.match(e, env)) {
					System.out.println(env.get("name").treeToString() + ": " + env.get("def").treeToString());
				}
			}
			@Override
			public void onExit(Engine<IStrategoTerm, Integer> e) {
			}
			@Override
			public void beforeChild(Engine<IStrategoTerm, Integer> e, int i) {
			}
		};
		Engine<IStrategoTerm, Integer> e = new StrategicEngine<IStrategoTerm, Integer>(tree, visitor);
		e.engage();

		return "";
	}
}
