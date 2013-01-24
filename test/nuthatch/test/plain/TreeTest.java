package nuthatch.test.plain;

import static org.junit.Assert.*;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.impl.StandardTree;

import org.junit.Test;

public class TreeTest {
	TreeCursor<String, String> foo = new StandardTree<String, String>("foo", "").makeCursor();
	TreeCursor<String, String> bar = new StandardTree<String, String>("bar", "", foo.getCurrentTree()).makeCursor();
	TreeCursor<String, String> baz = new StandardTree<String, String>("baz", "", foo.getCurrentTree(), bar.getCurrentTree()).makeCursor();


	/**
	 * Properties of foo
	 */
	@Test
	public final void test1() {
		assertEquals("foo", foo.getName());
		assertNull(foo.getData());
		assertFalse(foo.hasBranch(0));
		assertEquals(0, foo.numChildren());
		assertTrue(foo.isAtLeaf());
		assertTrue(foo.isAtRoot());
	}


	/**
	 * Properties of bar
	 */
	@Test
	public final void test2() {
		assertEquals("bar", bar.getName());
		assertNull(bar.getData());
		assertFalse(bar.hasBranch(0));
		assertEquals(1, bar.numChildren());
		assertFalse(bar.isAtLeaf());
		assertTrue(bar.isAtRoot());
	}


	/**
	 * Properties of foo as bar's child
	 */
	@Test
	public final void test3() {
		TreeCursor<String, String> foo2 = bar.copy().go(1);
		assertNull(foo2.getData());
		assertEquals(bar.getCurrentTree(), foo2.getTreeAtBranch(0));
		assertEquals(0, foo2.numChildren());
		assertTrue(foo2.isAtLeaf());
		assertFalse(foo2.isAtRoot());
	}


	/**
	 * Properties of baz
	 */
	@Test
	public final void test4() {
		assertEquals("baz", baz.getName());
		assertNull(baz.getData());
		assertFalse(baz.hasBranch(0));
		assertEquals(2, baz.numChildren());
		assertFalse(baz.isAtLeaf());
		assertTrue(baz.isAtRoot());
	}


	/**
	 * Properties of foo as baz's child
	 */
	@Test
	public final void test5() {
		TreeCursor<String, String> foo2 = baz.copy().go(1);
		assertNull(foo2.getData());
		assertEquals(baz.getCurrentTree(), foo2.getTreeAtBranch(0));
		assertEquals(0, foo2.numChildren());
		assertTrue(foo2.isAtLeaf());
		assertFalse(foo2.isAtRoot());
	}


	/**
	 * Properties of bar as baz's child
	 */
	@Test
	public final void test6() {
		TreeCursor<String, String> bar2 = baz.copy().go(2);
		assertNull(bar2.getData());
		assertEquals(baz.getCurrentTree(), bar2.getTreeAtBranch(0));
		assertEquals(1, bar2.numChildren());
		assertFalse(bar2.isAtLeaf());
		assertFalse(bar2.isAtRoot());
	}
}
