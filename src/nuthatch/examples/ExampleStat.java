package nuthatch.examples;

import static nuthatch.examples.xmpllang.full.XmplPatterns.*;
import nuthatch.examples.xmpllang.Stat;

public class ExampleStat {
	static final String x = "x";
	static final String y = "y";
	/**
	 * 5 + ((7 + 3) * 3)
	 */
	public static final Stat stat1 = Nop();
	public static final Stat stat2 = Seq(Assign(Var("x"), Int(0)),  Nop(), Nop());
	public static final Stat stat3 = If(Int(1), Assign(Var("x"), Int(2)), Assign(Var("x"), Int(3)));
	public static final Stat stat4 = Declare(Var("i"), Int(10), //
			While(Var("i"), Assign(Var("i"), Add(Var("i"), Int(-1)))));

	public static final Stat[] allStats = new Stat[] { stat2, stat1, stat3, stat4 };
}
