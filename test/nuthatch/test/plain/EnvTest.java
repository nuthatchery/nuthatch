package nuthatch.test.plain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nuthatch.pattern.Environment;
import nuthatch.pattern.EnvironmentFactory;
import nuthatch.tree.Tree;
import nuthatch.tree.impl.DoubleTree;

import org.junit.Test;

public class EnvTest {
	Tree foo = new DoubleTree("foo", "");
	Tree bar = new DoubleTree("bar", "", foo);
	Tree baz = new DoubleTree("baz", "", foo, bar);


	@Test
	public final void unscopedPutGet() {
		Environment env = EnvironmentFactory.env();
		env.put("foo", foo);
		env.put("bar", bar);

		assertEquals(foo, env.get("foo"));
		assertEquals(bar, env.get("bar"));
		assertNull(env.get("baz"));
	}


	@Test
	public final void scopedPutGet() {
		Environment env = EnvironmentFactory.env();
		env = env.enterScope();
		env.put("foo", foo);
		env.put("bar", bar);

		assertEquals(foo, env.get("foo"));
		assertEquals(bar, env.get("bar"));
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
		env.put("foo", foo);
		env.put("bar", bar);

		assertEquals(foo, env.get("foo"));
		assertEquals(bar, env.get("bar"));
		assertNull(env.get("baz"));

		env = env.enterScope();
		env.put("baz", baz);

		assertEquals(foo, env.get("foo"));
		assertEquals(bar, env.get("bar"));
		assertEquals(baz, env.get("baz"));

		env = env.exitScope();

		assertEquals(foo, env.get("foo"));
		assertEquals(bar, env.get("bar"));
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
		env.put("foo", foo);
		env.put("bar", bar);

		assertEquals(foo, env.get("foo"));
		assertEquals(bar, env.get("bar"));
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
		env.put("foo", foo);
		env.put("bar", bar);

		assertEquals(foo, env.get("foo"));
		assertEquals(bar, env.get("bar"));
		assertNull(env.get("baz"));
		env.commit();
		assertEquals(foo, env.get("foo"));
		assertEquals(bar, env.get("bar"));
		assertNull(env.get("baz"));
	}

}
