package nuthatch.test.junit;

import static org.junit.Assert.assertThat;
import nuthatch.tree.TreeCursor;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class TreeAssert {
	public static <V, T> void assertSubtreeEquals(String message, TreeCursor<V, T> a, TreeCursor<V, T> b) {
		assertThat(message, a, isSubtreeEquals(b));
	}


	public static <V, T> void assertSubtreeEquals(TreeCursor<V, T> a, TreeCursor<V, T> b) {
		assertThat(a, isSubtreeEquals(b));
	}


	public static <V, T> Matcher<TreeCursor<V, T>> isSubtreeEquals(TreeCursor<V, T> tree) {
		return new IsSubtreeEquals<V, T>(tree);
	}


	private static class IsSubtreeEquals<V, T> extends BaseMatcher<TreeCursor<V, T>> {
		private TreeCursor<V, T> tree;


		public IsSubtreeEquals(TreeCursor<V, T> tree) {
			this.tree = tree;
		}


		@Override
		public void describeTo(Description description) {
			// TODO Auto-generated method stub

		}


		@SuppressWarnings("unchecked")
		@Override
		public boolean matches(Object item) {
			if(item instanceof TreeCursor) {
				return tree.subtreeEquals((TreeCursor<V, T>) item);
			}
			else {
				return false;
			}
		}

	}
}
