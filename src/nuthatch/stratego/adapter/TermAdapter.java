package nuthatch.stratego.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public class TermAdapter {

	public static TermCursor append(TermCursor element, TermCursor list) {
		ITermFactory termFactory = StrategoAdapter.getTermFactory();
		IStrategoTerm[] subterms = list.getData().getAllSubterms();
		IStrategoTerm[] newList = Arrays.copyOf(subterms, subterms.length + 1);
		newList[subterms.length] = element.getData();
		return StrategoAdapter.termToTree(termFactory.makeList(newList));
	}


	public static List<TermCursor> getChildren(TermCursor c) {
		IStrategoTerm term = c.getData();
		ArrayList<TermCursor> list = new ArrayList<TermCursor>();
		for(IStrategoTerm subterm : term.getAllSubterms()) {
			list.add(StrategoAdapter.termToTree(subterm));
		}
		return list;
	}


	public static boolean isList(TermCursor c) {
		IStrategoTerm term = c.getData();
		return term.isList();
	}


	public static TermCursor makeList(List<TermCursor> list) {
		ITermFactory termFactory = StrategoAdapter.getTermFactory();
		IStrategoTerm terms[] = new IStrategoTerm[list.size()];
		int i = 0;
		for(TermCursor c : list) {
			terms[i++] = c.getData();
		}
		return StrategoAdapter.termToTree(termFactory.makeList(terms));
	}
}
