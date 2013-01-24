package nuthatch.test.plain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.impl.StandardTree;

import org.junit.Test;

public class EnvTest {
	TreeCursor<String, String> foo = new StandardTree<String, String>("foo", "").makeCursor();
	TreeCursor<String, String> bar = new StandardTree<String, String>("bar", "", foo.getCurrentTree()).makeCursor();
	TreeCursor<String, String> baz = new StandardTree<String, String>("baz", "", foo.getCurrentTree(), bar.getCurrentTree()).makeCursor();


	@Test
	public final void unscopedPutGet() {
		Environment env = EnvironmentFactory.env();
		env.put("foo", foo.getCurrentTree());
		env.put("bar", bar.getCurrentTree());

		assertEquals(foo.getCurrentTree(), env.get("foo"));
		assertEquals(bar.getCurrentTree(), env.get("bar"));
		assertNull(env.get("baz"));
	}


	@Test
	public final void scopedPutGet() {
		Environment env = EnvironmentFactory.env();
		env = env.enterScope();
		env.put("foo", foo.getCurrentTree());
		env.put("bar", bar.getCurrentTree());

		assertEquals(foo.getCurrentTree(), env.get("foo"));
		assertEquals(bar.getCurrentTree(), env.get("bar"));
		assertNull(env.get("baz"));
		env = env.exitScope();
		assertNull(env.get("foo"));
		assertNull(env.get("bar"));
		assertNull(env.get("baz"));
	}


	@Test
	public final void doubleScopedPutGet() {
		Environment env = EnvironmentFactory.env();
		env = env.enterScope();
		env.put("foo", foo.getCurrentTree());
		env.put("bar", bar.getCurrentTree());

		assertEquals(foo.getCurrentTree(), env.get("foo"));
		assertEquals(bar.getCurrentTree(), env.get("bar"));
		assertNull(env.get("baz"));

		env = env.enterScope();
		env.put("baz", baz.getCurrentTree());

		assertEquals(foo.getCurrentTree(), env.get("foo"));
		assertEquals(bar.getCurrentTree(), env.get("bar"));
		assertEquals(baz.getCurrentTree(), env.get("baz"));

		env = env.exitScope();

		assertEquals(foo.getCurrentTree(), env.get("foo"));
		assertEquals(bar.getCurrentTree(), env.get("bar"));
		assertNull(env.get("baz"));

		env = env.exitScope();

		assertNull(env.get("foo"));
		assertNull(env.get("bar"));
		assertNull(env.get("baz"));
	}


	@Test
	public final void transPutGetRollback() {
		Environment env = EnvironmentFactory.env();
		env.begin();
		env.put("foo", foo.getCurrentTree());
		env.put("bar", bar.getCurrentTree());

		assertEquals(foo.getCurrentTree(), env.get("foo"));
		assertEquals(bar.getCurrentTree(), env.get("bar"));
		assertNull(env.get("baz"));
		env.rollback();
		assertNull(env.get("foo"));
		assertNull(env.get("bar"));
		assertNull(env.get("baz"));
	}


	@Test
	public final void transPutGetCommit() {
		Environment env = EnvironmentFactory.env();
		env.begin();
		env.put("foo", foo.getCurrentTree());
		env.put("bar", bar.getCurrentTree());

		assertEquals(foo.getCurrentTree(), env.get("foo"));
		assertEquals(bar.getCurrentTree(), env.get("bar"));
		assertNull(env.get("baz"));
		env.commit();
		assertEquals(foo.getCurrentTree(), env.get("foo"));
		assertEquals(bar.getCurrentTree(), env.get("bar"));
		assertNull(env.get("baz"));
	}

}
