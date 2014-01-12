package nuthatch.test.plain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.Pattern;
import nuthatch.pattern.PatternFactory;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.impl.StandardTree;

import org.junit.Before;
import org.junit.Test;

public class PatternTest {
	StandardTree<String, String> fooTree = new StandardTree<String, String>("foo", "");
	StandardTree<String, String> barTree = new StandardTree<String, String>("bar", "", fooTree);
	StandardTree<String, String> bazTree = new StandardTree<String, String>("baz", "", fooTree, barTree);
	TreeCursor<String, String> foo = fooTree.makeCursor();
	TreeCursor<String, String> bar = barTree.makeCursor();
	TreeCursor<String, String> baz = bazTree.makeCursor();
	PatternFactory<String, String> pf = PatternFactory.getInstance(String.class, String.class);
	Environment<String, TreeCursor<String, String>> env;


	@Test
	public void matchAnd() {
		Pattern<String, String> pat = pf.and(pf.nodeName("bar"), pf.children(pf.nodeName("foo")));

		assertFalse(pat.match(foo, env));
		assertTrue(pat.match(bar, env));
		assertFalse(pat.match(baz, env));
		assertTrue(env.isEmpty());

	}


	@Test
	public void matchChildren() {
		assertTrue(pf.children().match(foo, env));
		assertTrue(pf.children(pf.nodeName("foo")).match(bar, env));
		assertTrue(pf.children(pf.nodeName("foo"), pf.nodeWithChildren("bar", pf.nodeName("foo"))).match(baz, env));

		assertTrue(env.isEmpty());
	}


	@Test
	public void matchName() {
		Pattern<String, String> pat = pf.nodeName("foo");

		assertTrue(pat.match(foo, env));
		assertFalse(pat.match(bar, env));
		assertFalse(pat.match(baz, env));
		assertTrue(env.isEmpty());
	}


	@Test
	public void matchOr() {
		Pattern<String, String> pat = pf.or(pf.nodeName("foo"), pf.nodeName("bar"));

		assertTrue(pat.match(foo, env));
		assertTrue(pat.match(bar, env));
		assertFalse(pat.match(baz, env));
		assertTrue(env.isEmpty());

	}


	@Test
	public void matchType() {
		Pattern<String, String> pat = pf.nodeType("");

		assertTrue(pat.match(foo, env));
		assertTrue(pat.match(bar, env));
		assertTrue(pat.match(baz, env));
		assertTrue(env.isEmpty());
	}


	@Before
	public void setup() {
		env = EnvironmentFactory.env();
	}
}
