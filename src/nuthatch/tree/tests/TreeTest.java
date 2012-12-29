package nuthatch.tree.tests;

import static org.junit.Assert.*;
import nuthatch.tree.DoubleTree;
import nuthatch.tree.Tree;

import org.junit.Test;

public class TreeTest {
	Tree foo = new DoubleTree("foo", "");
	Tree bar = new DoubleTree("bar", "", foo);
	Tree baz = new DoubleTree("baz", "", foo, bar);


	/**
	 * Properties of foo
	 */
	@Test
	public final void test1() {
		assertEquals("foo", foo.getName());
		assertNull(foo.getData());
		assertNull(foo.getParent());
		assertEquals(0, foo.numChildren());
		assertTrue(foo.isLeaf());
		assertTrue(foo.isRoot());
	}

	/**
	 * Properties of bar
	 */
	@Test
	public final void test2() {
		assertEquals("bar", bar.getName());
		assertNull(bar.getData());
		assertNull(bar.getParent());
		assertEquals(1, bar.numChildren());
		assertFalse(bar.isLeaf());
		assertTrue(bar.isRoot());
	}

	/**
	 * Properties of foo as bar's child
	 */
	@Test
	public final void test3() {
		Tree foo2 = bar.getBranch(1);
		assertNull(foo2.getData());
		assertEquals(bar, foo2.getParent());
		assertEquals(0, foo2.numChildren());
		assertTrue(foo2.isLeaf());
		assertFalse(foo2.isRoot());
	}

	/**
	 * Properties of baz
	 */
	@Test
	public final void test4() {
		assertEquals("baz", baz.getName());
		assertNull(baz.getData());
		assertNull(baz.getParent());
		assertEquals(2, baz.numChildren());
		assertFalse(baz.isLeaf());
		assertTrue(baz.isRoot());
	}

	/**
	 * Properties of foo as baz's child
	 */
	@Test
	public final void test5() {
		Tree foo2 = baz.getBranch(1);
		assertNull(foo2.getData());
		assertEquals(baz, foo2.getParent());
		assertEquals(0, foo2.numChildren());
		assertTrue(foo2.isLeaf());
		assertFalse(foo2.isRoot());
	}

	/**
	 * Properties of bar as baz's child
	 */
	@Test
	public final void test6() {
		Tree bar2 = baz.getBranch(2);
		assertNull(bar2.getData());
		assertEquals(baz, bar2.getParent());
		assertEquals(1, bar2.numChildren());
		assertFalse(bar2.isLeaf());
		assertFalse(bar2.isRoot());
	}
}
