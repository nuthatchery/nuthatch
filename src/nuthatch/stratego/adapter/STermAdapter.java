package nuthatch.stratego.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public class STermAdapter {

	public static STermCursor append(STermCursor element, STermCursor list) {
		ITermFactory termFactory = StrategoAdapter.getTermFactory();
		IStrategoTerm[] subterms = list.getData().getAllSubterms();
		IStrategoTerm[] newList = Arrays.copyOf(subterms, subterms.length + 1);
		newList[subterms.length] = element.getData();
		return StrategoAdapter.termToTree(termFactory.makeList(newList));
	}


	public static List<STermCursor> getChildren(STermCursor c) {
		IStrategoTerm term = c.getData();
		ArrayList<STermCursor> list = new ArrayList<STermCursor>();
		for(IStrategoTerm subterm : term.getAllSubterms()) {
			list.add(StrategoAdapter.termToTree(subterm));
		}
		return list;
	}


	public static boolean isList(STermCursor c) {
		IStrategoTerm term = c.getData();
		return term.isList();
	}


	public static STermCursor makeList(List<STermCursor> list) {
		ITermFactory termFactory = StrategoAdapter.getTermFactory();
		IStrategoTerm terms[] = new IStrategoTerm[list.size()];
		int i = 0;
		for(STermCursor c : list) {
			terms[i++] = c.getData();
		}
		return StrategoAdapter.termToTree(termFactory.makeList(terms));
	}
}
