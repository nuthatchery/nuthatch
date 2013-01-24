package nuthatch.tree;

public interface Path {
	int popElement();


	void pushElement(int i);


	int getElement(int i);


	int size();


	@Override
	String toString();


	Path copy();

}
