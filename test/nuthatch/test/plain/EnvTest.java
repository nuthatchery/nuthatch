package nuthatch.test.plain;

import static nuthatch.test.junit.TreeAssert.assertSubtreeEquals;
import static org.junit.Assert.assertNull;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.tree.TreeCursor;
import nuthatch.tree.impl.StandardTree;

import org.junit.Test;

public class EnvTest {
	StandardTree<String, String> fooTree = new StandardTree<String, String>("foo", "");
	StandardTree<String, String> barTree = new StandardTree<String, String>("bar", "", fooTree);
	StandardTree<String, String> bazTree = new StandardTree<String, String>("baz", "", fooTree, barTree);
	TreeCursor<String, String> foo = fooTree.makeCursor();
	TreeCursor<String, String> bar = barTree.makeCursor();
	TreeCursor<String, String> baz = bazTree.makeCursor();


	@Test
	public final void doubleScopedPutGet() {
		Environment<String, TreeCursor<String, String>> env = EnvironmentFactory.env();
		env = env.enterScope();
		env.put("foo", foo);
		env.put("bar", bar);

		assertSubtreeEquals(foo, env.get("foo"));
		assertSubtreeEquals(bar, env.get("bar"));
		assertNull(env.get("baz"));

		env = env.enterScope();
		env.put("baz", baz);

		assertSubtreeEquals(foo, env.get("foo"));
		assertSubtreeEquals(bar, env.get("bar"));
		assertSubtreeEquals(baz, env.get("baz"));

		env = env.exitScope();

		assertSubtreeEquals(foo, env.get("foo"));
		assertSubtreeEquals(bar, env.get("bar"));
		assertNull(env.get("baz"));

		env = env.exitScope();

		assertNull(env.get("foo"));
		assertNull(env.get("bar"));
		assertNull(env.get("baz"));
	}


	@Test
	public final void scopedPutGet() {
		Environment<String, TreeCursor<String, String>> env = EnvironmentFactory.env();
		env = env.enterScope();
		env.put("foo", foo);
		env.put("bar", bar);

		assertSubtreeEquals(foo, env.get("foo"));
		assertSubtreeEquals(bar, env.get("bar"));
		assertNull(env.get("baz"));
		env = env.exitScope();
		assertNull(env.get("foo"));
		assertNull(env.get("bar"));
		assertNull(env.get("baz"));
	}


	@Test
	public final void transPutGetCommit() {
		Environment<String, TreeCursor<String, String>> env = EnvironmentFactory.env();
		env.begin();
		env.put("foo", foo);
		env.put("bar", bar);

		assertSubtreeEquals(foo, env.get("foo"));
		assertSubtreeEquals(bar, env.get("bar"));
		assertNull(env.get("baz"));
		env.commit();
		assertSubtreeEquals(foo, env.get("foo"));
		assertSubtreeEquals(bar, env.get("bar"));
		assertNull(env.get("baz"));
	}


	@Test
	public final void transPutGetRollback() {
		Environment<String, TreeCursor<String, String>> env = EnvironmentFactory.env();
		env.begin();
		env.put("foo", foo);
		env.put("bar", bar);

		assertSubtreeEquals(foo, env.get("foo"));
		assertSubtreeEquals(bar, env.get("bar"));
		assertNull(env.get("baz"));
		env.rollback();
		assertNull(env.get("foo"));
		assertNull(env.get("bar"));
		assertNull(env.get("baz"));
	}


	@Test
	public final void unscopedPutGet() {
		Environment<String, TreeCursor<String, String>> env = EnvironmentFactory.env();
		env.put("foo", foo);
		env.put("bar", bar);

		assertSubtreeEquals(foo, env.get("foo"));
		assertSubtreeEquals(bar, env.get("bar"));
		assertNull(env.get("baz"));
	}

}
