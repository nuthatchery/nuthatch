package nuthatch.stratego.adapter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import nuthatch.tree.TreeCursor;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.jsglr.client.InvalidParseTableException;
import org.spoofax.jsglr.client.ParseException;
import org.spoofax.jsglr.client.ParseTable;
import org.spoofax.jsglr.io.SGLR;
import org.spoofax.jsglr.client.imploder.TermTreeFactory;
import org.spoofax.jsglr.client.imploder.TreeBuilder;
import org.spoofax.jsglr.io.ParseTableManager;
import org.spoofax.jsglr.shared.BadTokenException;
import org.spoofax.jsglr.shared.SGLRException;
import org.spoofax.jsglr.shared.TokenExpectedException;

public class StrategoAdapter {
	private static final ParseTableManager ptm = new ParseTableManager();

	public static ParseTableManager getParseTableManager() {
		return ptm;
	}
	/**
	 * Wrap a Stratego term in a Nuthatch tree cursor
	 * @param term The term
	 * @return A tree cursor wrapping the term
	 */
	public static TermCursor termToTree(IStrategoTerm term) {
		return new TermCursor(term, ptm.getFactory());
	}
	
	/**
	 * Unwrap a Stratego term from a Nuthatch tree cursor
	 * @param tree The tree cursor
	 * @return The wrapped term, or null if the cursor doesn't wrap a Stratego term
	 */
	public static IStrategoTerm treeToTerm(TreeCursor<IStrategoTerm, Integer> tree) {
		if(tree instanceof TermCursor) {
			return ((TermCursor)tree).getTerm();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Parse a file.
	 * 
	 * @param inputFile Path of the input file
	 * @param parseTable Path of the parse table
	 * @return An imploded AST of the file
	 * @throws FileNotFoundException if the input file or parse table wasn't found
	 * @throws IOException on error reading the file or parse table
	 * @throws InvalidParseTableException in case of an invalid parse table
	 * @throws TokenExpectedException on parse error
	 * @throws BadTokenException on parse error
	 * @throws ParseException on parse error
	 * @throws SGLRException on parse error
	 */
	public static IStrategoTerm parseFile(String inputFile, String parseTable) throws TokenExpectedException, BadTokenException, ParseException, SGLRException, IOException, InvalidParseTableException {
		return parseFile(inputFile, null, parseTable);
	}

	/**
	 * Parse a file.
	 * 
	 * @param inputFile Path of the input file
	 * @param parseTable Path of the parse table
	 * @return An imploded AST of the file
	 * @throws FileNotFoundException if the input file or parse table wasn't found
	 * @throws IOException on error reading the file or parse table
	 * @throws InvalidParseTableException in case of an invalid parse table
	 * @throws TokenExpectedException on parse error
	 * @throws BadTokenException on parse error
	 * @throws ParseException on parse error
	 * @throws SGLRException on parse error
	 */
	public static IStrategoTerm parseFile(String inputFile, ParseTable parseTable) throws TokenExpectedException, BadTokenException, ParseException, SGLRException, IOException {
		return parseFile(inputFile, null, parseTable);
	}

	/**
	 * Parse a file.
	 * 
	 * @param inputFile Path of the input file
	 * @param startSymbol The start symbol, or null
	 * @param parseTable Path of the parse table
	 * @return An imploded AST of the file
	 * @throws FileNotFoundException if the input file or parse table wasn't found
	 * @throws IOException on error reading the file or parse table
	 * @throws InvalidParseTableException in case of an invalid parse table
	 * @throws TokenExpectedException on parse error
	 * @throws BadTokenException on parse error
	 * @throws ParseException on parse error
	 * @throws SGLRException on parse error
	 */
	public static IStrategoTerm parseFile(String inputFile, String startSymbol, String parseTable) throws TokenExpectedException, BadTokenException, ParseException, SGLRException, IOException, InvalidParseTableException {
		SGLR sglr = new SGLR(new TreeBuilder(new TermTreeFactory(
					ptm.getFactory())), ptm.loadFromFile(parseTable));
		try(Reader in = new FileReader(inputFile)) {
			return (IStrategoTerm) sglr.parse(in, inputFile, startSymbol);
		}
	}

	/**
	 * Parse a file.
	 * 
	 * @param inputFile Path of the input file
	 * @param startSymbol The start symbol, or null
	 * @param parseTable Path of the parse table
	 * @return An imploded AST of the file
	 * @throws FileNotFoundException if the input file or parse table wasn't found
	 * @throws IOException on error reading the file or parse table
	 * @throws InvalidParseTableException in case of an invalid parse table
	 * @throws TokenExpectedException on parse error
	 * @throws BadTokenException on parse error
	 * @throws ParseException on parse error
	 * @throws SGLRException on parse error
	 */
	public static IStrategoTerm parseFile(String inputFile, String startSymbol, ParseTable parseTable) throws TokenExpectedException, BadTokenException, ParseException, SGLRException, IOException {
		SGLR sglr = new SGLR(new TreeBuilder(new TermTreeFactory(
					ptm.getFactory())), parseTable);
		try(Reader in = new FileReader(inputFile)) {
			return (IStrategoTerm) sglr.parse(in, inputFile, startSymbol);
		}
	}

	/**
	 * Parse a string.
	 * 
	 * @param input The input string
	 * @param fileName The file name, for error reporting, or null
	 * @param parseTable Path of the parse table
	 * @return An imploded AST of the file
	 * @throws FileNotFoundException if the parse table wasn't found
	 * @throws IOException on error reading parse table
	 * @throws InvalidParseTableException in case of an invalid parse table
	 * @throws TokenExpectedException on parse error
	 * @throws BadTokenException on parse error
	 * @throws ParseException on parse error
	 * @throws SGLRException on parse error
	 */
	public static IStrategoTerm parseString(String input, String fileName, String parseTable) throws TokenExpectedException, FileNotFoundException, BadTokenException, ParseException, IOException, InvalidParseTableException, SGLRException {
		return parseString(input, fileName, null, parseTable);
	}
	
	/**
	 * Parse a string.
	 * 
	 * @param input The input string
	 * @param fileName The file name, for error reporting, or null
	 * @param parseTable Path of the parse table
	 * @return An imploded AST of the file
	 * @throws FileNotFoundException if the parse table wasn't found
	 * @throws IOException on error reading parse table
	 * @throws InvalidParseTableException in case of an invalid parse table
	 * @throws TokenExpectedException on parse error
	 * @throws BadTokenException on parse error
	 * @throws ParseException on parse error
	 * @throws SGLRException on parse error
	 */
	public static IStrategoTerm parseString(String input, String fileName, ParseTable parseTable) throws TokenExpectedException, BadTokenException, ParseException, SGLRException {
		return parseString(input, fileName, null, parseTable);
	}

	/**
	 * Parse a string.
	 * 
	 * @param input The input string
	 * @param fileName The file name, for error reporting, or null
	 * @param startSymbol The start symbol, or null
	 * @param parseTable Path of the parse table
	 * @return An imploded AST of the file
	 * @throws FileNotFoundException if the parse table wasn't found
	 * @throws IOException on error reading parse table
	 * @throws InvalidParseTableException in case of an invalid parse table
	 * @throws TokenExpectedException on parse error
	 * @throws BadTokenException on parse error
	 * @throws ParseException on parse error
	 * @throws SGLRException on parse error
	 */
	public static IStrategoTerm parseString(String input, String fileName, String startSymbol, String parseTable) throws FileNotFoundException, IOException, InvalidParseTableException, TokenExpectedException, BadTokenException, ParseException, SGLRException {
		SGLR sglr = new SGLR(new TreeBuilder(new TermTreeFactory(
					ptm.getFactory())), ptm.loadFromFile(parseTable));
		return (IStrategoTerm) sglr.parse(input, fileName, startSymbol);
	}


	/**
	 * Parse a string.
	 * 
	 * @param input The input string
	 * @param fileName The file name, for error reporting, or null
	 * @param startSymbol The start symbol, or null
	 * @param parseTable Path of the parse table
	 * @return An imploded AST of the file
	 * @throws FileNotFoundException if the parse table wasn't found
	 * @throws IOException on error reading parse table
	 * @throws InvalidParseTableException in case of an invalid parse table
	 * @throws TokenExpectedException on parse error
	 * @throws BadTokenException on parse error
	 * @throws ParseException on parse error
	 * @throws SGLRException on parse error
	 */
	public static IStrategoTerm parseString(String input, String fileName, String startSymbol, ParseTable parseTable) throws TokenExpectedException, BadTokenException, ParseException, SGLRException {
		SGLR sglr = new SGLR(new TreeBuilder(new TermTreeFactory(
					ptm.getFactory())), parseTable);
		return (IStrategoTerm) sglr.parse(input, fileName, startSymbol);
	}

}
