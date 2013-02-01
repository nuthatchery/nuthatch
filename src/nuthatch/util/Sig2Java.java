package nuthatch.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import nuthatch.stratego.syntax.StrategoSignatures;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.jsglr.client.InvalidParseTableException;
import org.spoofax.jsglr.client.ParseException;
import org.spoofax.jsglr.shared.BadTokenException;
import org.spoofax.jsglr.shared.SGLRException;
import org.spoofax.jsglr.shared.TokenExpectedException;

/**
 * Convert a Stratego signature file to a Java static pattern factory.
 * 
 * @author anya
 */
public class Sig2Java {

	/**
	 * @param args
	 * @throws InvalidParseTableException
	 * @throws IOException
	 * @throws SGLRException
	 * @throws ParseException
	 * @throws BadTokenException
	 * @throws TokenExpectedException
	 */
	public static void main(String[] args) throws TokenExpectedException, BadTokenException, ParseException, SGLRException, IOException, InvalidParseTableException {
		String inFile = null;
		String outFile = null;
		String className = null;
		String pkgName = null;
		int i = 0;
		while(i < args.length) {
			switch(args[i]) {
			case "-h":
			case "--help":
				help();
				break;
			case "-i":
				if(i == args.length) {
					fatalMissingArgument("-i");
				}
				inFile = args[++i];
				break;
			case "-o":
				if(i == args.length) {
					fatalMissingArgument("-o");
				}
				outFile = args[++i];
				break;
			case "-c":
				if(i == args.length) {
					fatalMissingArgument("-c");
				}
				className = args[++i];
				break;
			case "-p":
				if(i == args.length) {
					fatalMissingArgument("-p");
				}
				pkgName = args[++i];
				break;
			default:
				if(inFile == null) {
					inFile = args[i];
				} else {
					fatalExtraArgument(args[i]);
				}
				break;
			}
			i++;
		}

		if(outFile != null && !outFile.endsWith(".java")) {
			outFile = outFile + ".java";
		}

		if(className == null) {
			if(outFile != null) {
				className = outFile;
				if(className.contains(File.separator)) {
					className = outFile.substring(outFile.lastIndexOf(File.separator) + File.separator.length());
				}
				className = className.substring(0, className.indexOf("."));
			}
			else {
				fatalMissingName("class");
			}
		}

		if(pkgName == null && outFile != null && outFile.contains(File.separator)) {
			pkgName = outFile.substring(0, outFile.lastIndexOf(File.separator));
			pkgName = pkgName.replaceAll(File.separator, ".");
		}

		/*
		System.out.println("Path separator: " + File.separator);
		System.out.println("Class name: " + className);
		System.out.println("Package name: " + pkgName);
		System.exit(0);
		 */
		IStrategoTerm signature;
		if(inFile != null) {
			signature = StrategoSignatures.parseSignatureFile(inFile);
		}
		else {
			signature = StrategoSignatures.parseSignatureStream(System.in);
		}

		String javaCode = StrategoSignatures.sig2java(signature, pkgName, className);

		if(outFile != null) {
			try(PrintWriter out = new PrintWriter(new FileOutputStream(outFile))) {
				out.print(javaCode);
			}
		}
		else {
			System.out.print(javaCode);
		}
	}

	private static void fatalExtraArgument(String s) {
		System.err.println("Unknown extra argument: " + s);
		System.exit(1);
	}

	private static void fatalMissingArgument(String s) {
		System.err.println("Missing argument to " + s);
		System.exit(1);
	}

	private static void fatalMissingName(String s) {
		System.err.println("Missing " + s + " name, and not output file given");
		System.exit(1);
	}

	private static void help() {
		System.err.println("usage: Sig2Java [[-i] inFile.str] [-o outFile.java]");
		System.err.println("Extra options: ");
		System.err.println("  -c Name      Set class name");
		System.err.println("  -p Name      Set package name");
		System.err.println("By default, class and package names are inferred from output file name");
	}

}
