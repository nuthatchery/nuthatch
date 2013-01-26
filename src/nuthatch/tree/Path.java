package nuthatch.tree;

public interface Path {
	Path copy();


	int getElement(int i);


	int popElement();


	void pushElement(int i);


	int size();


	@Override
	String toString();

}
