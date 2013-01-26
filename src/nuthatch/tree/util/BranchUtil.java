package nuthatch.tree.util;

public class BranchUtil {
	/**
	 * Normalise the branch number.
	 * 
	 * @param i
	 *            An unnormalised branch number
	 * @param n
	 *            The number of children
	 * @return A branch number >= 0 and <= n, or -1 for an invalid
	 *         branch
	 */
	public static int normalBranch(int i, int n) {
		if(i == -1) {
			i = n;
		}
		else if(i == n + 1) {
			i = 0;
		}

		if(i < 0 || i > n) {
			return -1;
		}

		return i;
	}

}
