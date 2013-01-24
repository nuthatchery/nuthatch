package nuthatch.test.plain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.pattern.Pattern;
import nuthatch.pattern.PatternFactory;
import nuthatch.tree.impl.StandardTree;
import nuthatch.tree.impl.StandardTreeCursor;

import org.junit.Before;
import org.junit.Test;

public class PatternTest {
	StandardTreeCursor<String, String> foo = new StandardTreeCursor<String, String>(new StandardTree<String, String>("foo", ""));
	StandardTreeCursor<String, String> bar = new StandardTreeCursor<String, String>(new StandardTree<String, String>("bar", "", foo.getCurrentTree()));
	StandardTreeCursor<String, String> baz = new StandardTreeCursor<String, String>(new StandardTree<String, String>("baz", "", foo.getCurrentTree(), bar.getCurrentTree()));
	PatternFactory pf = PatternFactory.getInstance();
	Environment env;


	@Before
	public void setup() {
		env = EnvironmentFactory.env();
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
	public void matchType() {
		Pattern<String, String> pat = pf.nodeType("");

		assertTrue(pat.match(foo, env));
		assertTrue(pat.match(bar, env));
		assertTrue(pat.match(baz, env));
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
	public void matchOr() {
		Pattern<String, String> pat = pf.or(pf.nodeName("foo"), pf.nodeName("bar"));

		assertTrue(pat.match(foo, env));
		assertTrue(pat.match(bar, env));
		assertFalse(pat.match(baz, env));
		assertTrue(env.isEmpty());

	}


	@Test
	public void matchAnd() {
		Pattern<String, String> pat = pf.and(pf.nodeName("bar"), pf.children(pf.nodeName("foo")));

		assertFalse(pat.match(foo, env));
		assertTrue(pat.match(bar, env));
		assertFalse(pat.match(baz, env));
		assertTrue(env.isEmpty());

	}
}
