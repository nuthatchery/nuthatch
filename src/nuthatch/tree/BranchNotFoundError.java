package nuthatch.tree;

public class BranchNotFoundError extends RuntimeException {

	private static final long serialVersionUID = 603464659507855908L;

	public BranchNotFoundError(String message) {
		super(message);
	}
}
