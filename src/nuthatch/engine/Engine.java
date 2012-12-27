package nuthatch.engine;

public interface Engine {
	void engage();
	
	void transform(Transform t);


	int from();
	
	boolean from(int i);
	
	boolean isLeaf();

	void split();
}
