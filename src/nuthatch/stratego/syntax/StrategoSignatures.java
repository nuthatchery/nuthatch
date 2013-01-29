package nuthatch.stratego.syntax;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nuthatch.engine.Engine;
import nuthatch.engine.impl.StrategicEngine;
import nuthatch.library.strategies.Visitor;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.Pattern;
import nuthatch.stratego.adapter.StrategoAdapter;
import nuthatch.stratego.adapter.TermCursor;
import nuthatch.stratego.adapter.TermEngine;
import nuthatch.stratego.pattern.TermPatternFactory;
import static nuthatch.stratego.syntax.StrategoPatterns.*;
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
	
	public static IStrategoTerm parseSignatureStream(InputStream input) throws TokenExpectedException, BadTokenException, ParseException, SGLRException, IOException, InvalidParseTableException {
		return StrategoAdapter.parseStream(input, "", null, getSignaturesParseTable());
	}

	private static ParseTable getSignaturesParseTable() throws IOException, InvalidParseTableException  {
		if(signaturesParseTable == null) {
			InputStream stream = StrategoSignatures.class.getResourceAsStream("sdf/Stratego-Signatures.tbl");
			signaturesParseTable = StrategoAdapter.getParseTableManager().loadFromStream(stream);
		}
		
		return signaturesParseTable;
	}
	
	public static String sig2java(IStrategoTerm signature, String packageName, String className) {
		TermCursor tree = StrategoAdapter.termToTree(signature);
		
		TermPatternFactory pf = TermPatternFactory.getInstance();
		
		final Pattern<IStrategoTerm, Integer> opDeclPat = OpDecl(pf.var("name"), pf.var("def"));
		final Pattern<IStrategoTerm, Integer> constTypePat = ConstType(pf.var("type"));
		final Pattern<IStrategoTerm, Integer> funTypePat = FunType(pf.var("params"), pf.var("retType"));
		final Set<String> methods = new HashSet<String>(); 

		Visitor<TermEngine> visitor = new Visitor<TermEngine>() {
			@Override
			public void onEntry(TermEngine e) {
				StringBuilder builder = new StringBuilder();
				Environment<TreeCursor<IStrategoTerm, Integer>> env = EnvironmentFactory.env();
				if(opDeclPat.match(e, env)) {
					builder.append("\tpublic static Pattern<IStrategoTerm, Integer> ");
					builder.append(env.get("name").getName());
					builder.append("(");
					// System.err.println(env.get("def").treeToString());
					int numChildren = 0;
					if(constTypePat.match(env.get("def"), env)) {
						;
					}
					else if(funTypePat.match(env.get("def"), env)) {
						for(@SuppressWarnings("unused") TreeCursor<IStrategoTerm, Integer> child : env.get("params")) {
							if(numChildren > 0)
								builder.append(", ");
							builder.append("Pattern<IStrategoTerm, Integer> arg");
							builder.append(numChildren++);
						}
					}
					builder.append(")");
					builder.append(" {\n");
					builder.append("\t\treturn pf.appl(\"");
					builder.append(env.get("name").getName());
					builder.append("\"");
					for(int j = 0; j < numChildren; j++) {
						builder.append(", ");
						builder.append("arg");
						builder.append(j);
					}
					builder.append(")");
					builder.append(";");
					builder.append("\n\t}\n");
					builder.append("\n");
					methods.add(builder.toString());
				}
			}
			@Override
			public void onExit(TermEngine e) {
			}
			@Override
			public void beforeChild(TermEngine e, int i) {
			}
		};
		Engine<IStrategoTerm, Integer> e = new TermEngine(tree, visitor);
		e.engage();

		String[] array = methods.toArray(new String[methods.size()]);
		Arrays.sort(array);
		StringBuilder builder = new StringBuilder();
		if(packageName != null && !packageName.equals("")) {
			builder.append("package ");
			builder.append(packageName);
			builder.append(";\n");
			builder.append("\n");
		}
		builder.append("import nuthatch.stratego.pattern.TermPatternFactory;\n");
		builder.append("import nuthatch.pattern.Pattern;\n");
		builder.append("import org.spoofax.interpreter.terms.IStrategoTerm;\n");
		builder.append("\n");
		builder.append("\n");
		builder.append("public class ");
		builder.append(className);
		builder.append(" {\n");
		builder.append("\tprivate static final TermPatternFactory pf = TermPatternFactory.getInstance();\n");
		builder.append("\n");
		
		for(String s : array) {
			builder.append(s);
		}
		builder.append("}\n");
		
		return builder.toString();
	}
}
