package nuthatch.benchmark.stratego;

import static org.strategoxt.lang.Term.NO_STRATEGIES;
import static org.strategoxt.lang.Term.checkListAnnos;
import static org.strategoxt.lang.Term.checkListTail;

import java.lang.ref.WeakReference;

import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;
import org.strategoxt.lang.Context;
import org.strategoxt.lang.InteropSDefT;
import org.strategoxt.lang.SRTS_all;
import org.strategoxt.lang.StrategoExit;
import org.strategoxt.lang.Strategy;
import org.strategoxt.lang.Term;
import org.strategoxt.lang.TermReference;
import org.strategoxt.lang._Id;

@SuppressWarnings("all") public class Benchmarks
{
	protected static final boolean TRACES_ENABLED = true;

	protected static ITermFactory constantFactory;

	private static WeakReference<Context> initedContext;

	private static boolean isIniting;

	protected static IStrategoTerm const0;

	protected static IStrategoTerm constNil0;

	public static IStrategoConstructor _consConc_2;

	public static IStrategoConstructor _consNone_0;

	public static IStrategoConstructor _consSome_1;

	public static IStrategoConstructor _consInvoke_2;

	public static IStrategoConstructor _consCons_2;

	public static IStrategoConstructor _consNil_0;

	public static Strategy getMainStrategy()
	{
		return main_0_0.instance;
	}

	public static Context init()
	{
		return init(new Context());
	}

	public static Context init(Context context)
	{
		synchronized(Benchmarks.class)
		{
			if(isIniting) {
				return null;
			}
			try
			{
				isIniting = true;
				ITermFactory termFactory = context.getFactory();
				if(constantFactory == null)
				{
					initConstructors(termFactory);
					initConstants(termFactory);
				}
				if(initedContext == null || initedContext.get() != context)
				{
					org.strategoxt.stratego_lib.Main.init(context);
					context.registerComponent("Benchmarks");
				}
				initedContext = new WeakReference<Context>(context);
				constantFactory = termFactory;
			}
			finally
			{
				isIniting = false;
			}
			return context;
		}
	}

	public static void initConstants(ITermFactory termFactory)
	{
		constNil0 = (IStrategoTerm)termFactory.makeList(Term.NO_TERMS);
		const0 = termFactory.makeInt(42);
	}

	public static void initConstructors(ITermFactory termFactory)
	{
		_consConc_2 = termFactory.makeConstructor("Conc", 2);
		_consNone_0 = termFactory.makeConstructor("None", 0);
		_consSome_1 = termFactory.makeConstructor("Some", 1);
		_consInvoke_2 = termFactory.makeConstructor("Invoke", 2);
		_consCons_2 = termFactory.makeConstructor("Cons", 2);
		_consNil_0 = termFactory.makeConstructor("Nil", 0);
	}

	public static void main(String args[])
	{
		Context context = init();
		context.setStandAlone(true);
		try
		{
			IStrategoTerm result;
			try
			{
				result = context.invokeStrategyCLI(main_0_0.instance, "Benchmarks", args);
			}
			finally
			{
				context.getIOAgent().closeAllFiles();
			}
			if(result == null)
			{
				System.err.println("Benchmarks" + (TRACES_ENABLED ? ": rewriting failed, trace:" : ": rewriting failed"));
				context.printStackTrace();
				context.setStandAlone(false);
				System.exit(1);
			}
			else
			{
				System.out.println(result);
				context.setStandAlone(false);
				System.exit(0);
			}
		}
		catch(StrategoExit exit)
		{
			context.setStandAlone(false);
			System.exit(exit.getValue());
		}
	}

	public static IStrategoTerm mainNoExit(Context context, String ... args) throws StrategoExit
	{
		try
		{
			init(context);
			return context.invokeStrategyCLI(main_0_0.instance, "Benchmarks", args);
		}
		finally
		{
			context.getIOAgent().closeAllFiles();
		}
	}

	public static IStrategoTerm mainNoExit(String ... args) throws StrategoExit
	{
		return mainNoExit(new Context(), args);
	}

	public static void registerInterop(org.spoofax.interpreter.core.IContext context, Context compiledContext)
	{
		new InteropRegisterer().registerLazy(context, compiledContext, InteropRegisterer.class.getClassLoader());
	}

	@SuppressWarnings("all") public static class at_end_1_0 extends Strategy
	{
		public static at_end_1_0 instance = new at_end_1_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term, Strategy v_1)
		{
			context.push("at_end_1_0");
			Fail17:
			{
				w_1 w_10 = new w_1();
				w_10.v_1 = v_1;
				term = w_10.invoke(context, term);
				if(term == null) {
					break Fail17;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class bottomup_0_0 extends Strategy
	{
		public static bottomup_0_0 instance = new bottomup_0_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			context.push("bottomup_0_0");
			Fail4:
			{
				term = bottomup_1_0.instance.invoke(context, term, _Id.instance);
				if(term == null) {
					break Fail4;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class bottomup_1_0 extends Strategy
	{
		public static bottomup_1_0 instance = new bottomup_1_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term, Strategy y_0)
		{
			context.push("bottomup_1_0");
			Fail9:
			{
				lifted8 lifted80 = new lifted8();
				lifted80.y_0 = y_0;
				term = SRTS_all.instance.invoke(context, term, lifted80);
				if(term == null) {
					break Fail9;
				}
				term = y_0.invoke(context, term);
				if(term == null) {
					break Fail9;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class bottomup42_0_0 extends Strategy
	{
		public static bottomup42_0_0 instance = new bottomup42_0_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			context.push("bottomup42_0_0");
			Fail2:
			{
				term = bottomup_1_0.instance.invoke(context, term, lifted2.instance);
				if(term == null) {
					break Fail2;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class collect_0_0 extends Strategy
	{
		public static collect_0_0 instance = new collect_0_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			context.push("collect_0_0");
			Fail6:
			{
				term = collect_all_1_0.instance.invoke(context, term, is_string_0_0.instance);
				if(term == null) {
					break Fail6;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class collect_all_1_0 extends Strategy
	{
		public static collect_all_1_0 instance = new collect_all_1_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term, Strategy c_1)
		{
			context.push("collect_all_1_0");
			Fail12:
			{
				term = collect_all_2_0.instance.invoke(context, term, c_1, conc_0_0.instance);
				if(term == null) {
					break Fail12;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class collect_all_2_0 extends Strategy
	{
		public static collect_all_2_0 instance = new collect_all_2_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term, Strategy d_1, Strategy e_1)
		{
			context.push("collect_all_2_0");
			Fail13:
			{
				f_1 f_10 = new f_1();
				f_10.d_1 = d_1;
				f_10.e_1 = e_1;
				term = f_10.invoke(context, term);
				if(term == null) {
					break Fail13;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class commute_0_0 extends Strategy
	{
		public static commute_0_0 instance = new commute_0_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			context.push("commute_0_0");
			Fail1:
			{
				term = topdown_1_0.instance.invoke(context, term, lifted0.instance);
				if(term == null) {
					break Fail1;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class conc_0_0 extends Strategy
	{
		public static conc_0_0 instance = new conc_0_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			context.push("conc_0_0");
			Fail14:
			{
				IStrategoTerm term2 = term;
				Success1:
				{
					Fail15:
					{
					IStrategoTerm k_1 = null;
					TermReference l_1 = new TermReference();
					if(term.getTermType() != IStrategoTerm.TUPLE || term.getSubtermCount() != 2) {
						break Fail15;
					}
					k_1 = term.getSubterm(0);
					if(l_1.value == null) {
						l_1.value = term.getSubterm(1);
					} else
						if(l_1.value != term.getSubterm(1) && !l_1.value.match(term.getSubterm(1))) {
							break Fail15;
						}
					term = k_1;
					lifted14 lifted140 = new lifted14();
					lifted140.l_1 = l_1;
					term = at_end_1_0.instance.invoke(context, term, lifted140);
					if(term == null) {
						break Fail15;
					}
					if(true) {
						break Success1;
					}
					}
				term = term2;
				IStrategoTerm cons0 = context.invokePrimitive("SSL_get_constructor", term, NO_STRATEGIES, new IStrategoTerm[]{term});
				if(cons0.getTermType() != IStrategoTerm.STRING || !"".equals(((IStrategoString)cons0).stringValue())) {
					break Fail14;
				}
				IStrategoTerm args0 = context.invokePrimitive("SSL_get_arguments", term, NO_STRATEGIES, new IStrategoTerm[]{term});
				term = concat_0_0.instance.invoke(context, args0);
				if(term == null) {
					break Fail14;
				}
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class concat_0_0 extends Strategy
	{
		public static concat_0_0 instance = new concat_0_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			context.push("concat_0_0");
			Fail16:
			{
				term = p_1.instance.invoke(context, term);
				if(term == null) {
					break Fail16;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class crush_3_0 extends Strategy
	{
		public static crush_3_0 instance = new crush_3_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term, Strategy y_1, Strategy z_1, Strategy a_2)
		{
			context.push("crush_3_0");
			Fail18:
			{
				IStrategoTerm args1 = context.invokePrimitive("SSL_get_arguments", term, NO_STRATEGIES, new IStrategoTerm[]{term});
				term = foldr_3_0.instance.invoke(context, args1, y_1, z_1, a_2);
				if(term == null) {
					break Fail18;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class downup_0_0 extends Strategy
	{
		public static downup_0_0 instance = new downup_0_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			context.push("downup_0_0");
			Fail5:
			{
				term = downup_2_0.instance.invoke(context, term, _Id.instance, _Id.instance);
				if(term == null) {
					break Fail5;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class downup_2_0 extends Strategy
	{
		public static downup_2_0 instance = new downup_2_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term, Strategy a_1, Strategy b_1)
		{
			context.push("downup_2_0");
			Fail11:
			{
				term = a_1.invoke(context, term);
				if(term == null) {
					break Fail11;
				}
				lifted10 lifted100 = new lifted10();
				lifted100.a_1 = a_1;
				lifted100.b_1 = b_1;
				term = SRTS_all.instance.invoke(context, term, lifted100);
				if(term == null) {
					break Fail11;
				}
				term = b_1.invoke(context, term);
				if(term == null) {
					break Fail11;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class foldr_3_0 extends Strategy
	{
		public static foldr_3_0 instance = new foldr_3_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term, Strategy d_2, Strategy e_2, Strategy f_2)
		{
			ITermFactory termFactory = context.getFactory();
			context.push("foldr_3_0");
			Fail19:
			{
				IStrategoTerm term5 = term;
				Success2:
				{
					Fail20:
					{
					if(term.getTermType() != IStrategoTerm.LIST || !((IStrategoList)term).isEmpty()) {
						break Fail20;
					}
					term = d_2.invoke(context, term);
					if(term == null) {
						break Fail20;
					}
					if(true) {
						break Success2;
					}
					}
				term = term5;
				IStrategoTerm b_2 = null;
				IStrategoTerm c_2 = null;
				IStrategoTerm g_2 = null;
				if(term.getTermType() != IStrategoTerm.LIST || ((IStrategoList)term).isEmpty()) {
					break Fail19;
				}
				b_2 = ((IStrategoList)term).head();
				c_2 = ((IStrategoList)term).tail();
				term = f_2.invoke(context, b_2);
				if(term == null) {
					break Fail19;
				}
				g_2 = term;
				term = this.invoke(context, c_2, d_2, e_2, f_2);
				if(term == null) {
					break Fail19;
				}
				term = termFactory.makeTuple(g_2, term);
				term = e_2.invoke(context, term);
				if(term == null) {
					break Fail19;
				}
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("unused") public static class InteropRegisterer extends org.strategoxt.lang.InteropRegisterer
	{
		@Override public void register(org.spoofax.interpreter.core.IContext context, Context compiledContext)
		{
			register(context, compiledContext, context.getVarScope());
		}

		@Override public void registerLazy(org.spoofax.interpreter.core.IContext context, Context compiledContext, ClassLoader classLoader)
		{
			registerLazy(context, compiledContext, classLoader, context.getVarScope());
		}

		private void register(org.spoofax.interpreter.core.IContext context, Context compiledContext, org.spoofax.interpreter.core.VarScope varScope)
		{
			compiledContext.registerComponent("Benchmarks");
			Benchmarks.init(compiledContext);
			varScope.addSVar("main_0_0", new InteropSDefT(main_0_0.instance, context));
			varScope.addSVar("commute_0_0", new InteropSDefT(commute_0_0.instance, context));
			varScope.addSVar("bottomup42_0_0", new InteropSDefT(bottomup42_0_0.instance, context));
			varScope.addSVar("topdown_0_0", new InteropSDefT(topdown_0_0.instance, context));
			varScope.addSVar("bottomup_0_0", new InteropSDefT(bottomup_0_0.instance, context));
			varScope.addSVar("downup_0_0", new InteropSDefT(downup_0_0.instance, context));
			varScope.addSVar("collect_0_0", new InteropSDefT(collect_0_0.instance, context));
			varScope.addSVar("try_1_0", new InteropSDefT(try_1_0.instance, context));
			varScope.addSVar("bottomup_1_0", new InteropSDefT(bottomup_1_0.instance, context));
			varScope.addSVar("topdown_1_0", new InteropSDefT(topdown_1_0.instance, context));
			varScope.addSVar("downup_2_0", new InteropSDefT(downup_2_0.instance, context));
			varScope.addSVar("collect_all_1_0", new InteropSDefT(collect_all_1_0.instance, context));
			varScope.addSVar("collect_all_2_0", new InteropSDefT(collect_all_2_0.instance, context));
			varScope.addSVar("conc_0_0", new InteropSDefT(conc_0_0.instance, context));
			varScope.addSVar("concat_0_0", new InteropSDefT(concat_0_0.instance, context));
			varScope.addSVar("at_end_1_0", new InteropSDefT(at_end_1_0.instance, context));
			varScope.addSVar("crush_3_0", new InteropSDefT(crush_3_0.instance, context));
			varScope.addSVar("foldr_3_0", new InteropSDefT(foldr_3_0.instance, context));
			varScope.addSVar("is_string_0_0", new InteropSDefT(is_string_0_0.instance, context));
		}

		private void registerLazy(org.spoofax.interpreter.core.IContext context, Context compiledContext, ClassLoader classLoader, org.spoofax.interpreter.core.VarScope varScope)
		{
			compiledContext.registerComponent("Benchmarks");
			Benchmarks.init(compiledContext);
			varScope.addSVar("main_0_0", new InteropSDefT(classLoader, "Benchmarks$main_0_0", context));
			varScope.addSVar("commute_0_0", new InteropSDefT(classLoader, "Benchmarks$commute_0_0", context));
			varScope.addSVar("bottomup42_0_0", new InteropSDefT(classLoader, "Benchmarks$bottomup42_0_0", context));
			varScope.addSVar("topdown_0_0", new InteropSDefT(classLoader, "Benchmarks$topdown_0_0", context));
			varScope.addSVar("bottomup_0_0", new InteropSDefT(classLoader, "Benchmarks$bottomup_0_0", context));
			varScope.addSVar("downup_0_0", new InteropSDefT(classLoader, "Benchmarks$downup_0_0", context));
			varScope.addSVar("collect_0_0", new InteropSDefT(classLoader, "Benchmarks$collect_0_0", context));
			varScope.addSVar("try_1_0", new InteropSDefT(classLoader, "Benchmarks$try_1_0", context));
			varScope.addSVar("bottomup_1_0", new InteropSDefT(classLoader, "Benchmarks$bottomup_1_0", context));
			varScope.addSVar("topdown_1_0", new InteropSDefT(classLoader, "Benchmarks$topdown_1_0", context));
			varScope.addSVar("downup_2_0", new InteropSDefT(classLoader, "Benchmarks$downup_2_0", context));
			varScope.addSVar("collect_all_1_0", new InteropSDefT(classLoader, "Benchmarks$collect_all_1_0", context));
			varScope.addSVar("collect_all_2_0", new InteropSDefT(classLoader, "Benchmarks$collect_all_2_0", context));
			varScope.addSVar("conc_0_0", new InteropSDefT(classLoader, "Benchmarks$conc_0_0", context));
			varScope.addSVar("concat_0_0", new InteropSDefT(classLoader, "Benchmarks$concat_0_0", context));
			varScope.addSVar("at_end_1_0", new InteropSDefT(classLoader, "Benchmarks$at_end_1_0", context));
			varScope.addSVar("crush_3_0", new InteropSDefT(classLoader, "Benchmarks$crush_3_0", context));
			varScope.addSVar("foldr_3_0", new InteropSDefT(classLoader, "Benchmarks$foldr_3_0", context));
			varScope.addSVar("is_string_0_0", new InteropSDefT(classLoader, "Benchmarks$is_string_0_0", context));
		}
	}

	@SuppressWarnings("all") public static class is_string_0_0 extends Strategy
	{
		public static is_string_0_0 instance = new is_string_0_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			Fail21:
			{
			term = context.invokePrimitive("SSL_is_string", term, NO_STRATEGIES, new IStrategoTerm[]{term});
			if(term == null) {
				break Fail21;
			}
			if(true) {
				return term;
			}
			}
		context.push("is_string_0_0");
		context.popOnFailure();
		return null;
		}
	}

	@SuppressWarnings("all") public static class main_0_0 extends Strategy
	{
		public static main_0_0 instance = new main_0_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			context.push("main_0_0");
			Fail0:
			{
				term = commute_0_0.instance.invoke(context, term);
				if(term == null) {
					break Fail0;
				}
				term = bottomup42_0_0.instance.invoke(context, term);
				if(term == null) {
					break Fail0;
				}
				term = topdown_0_0.instance.invoke(context, term);
				if(term == null) {
					break Fail0;
				}
				term = bottomup_0_0.instance.invoke(context, term);
				if(term == null) {
					break Fail0;
				}
				term = downup_0_0.instance.invoke(context, term);
				if(term == null) {
					break Fail0;
				}
				term = collect_0_0.instance.invoke(context, term);
				if(term == null) {
					break Fail0;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class topdown_0_0 extends Strategy
	{
		public static topdown_0_0 instance = new topdown_0_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			context.push("topdown_0_0");
			Fail3:
			{
				term = topdown_1_0.instance.invoke(context, term, _Id.instance);
				if(term == null) {
					break Fail3;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class topdown_1_0 extends Strategy
	{
		public static topdown_1_0 instance = new topdown_1_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term, Strategy z_0)
		{
			context.push("topdown_1_0");
			Fail10:
			{
				term = z_0.invoke(context, term);
				if(term == null) {
					break Fail10;
				}
				lifted9 lifted90 = new lifted9();
				lifted90.z_0 = z_0;
				term = SRTS_all.instance.invoke(context, term, lifted90);
				if(term == null) {
					break Fail10;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") public static class try_1_0 extends Strategy
	{
		public static try_1_0 instance = new try_1_0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term, Strategy x_0)
		{
			context.push("try_1_0");
			Fail7:
			{
				IStrategoTerm term0 = term;
				Success0:
				{
					Fail8:
					{
					term = x_0.invoke(context, term);
					if(term == null) {
						break Fail8;
					}
					if(true) {
						break Success0;
					}
					}
				term = term0;
				}
				context.popOnSuccess();
				if(true) {
					return term;
				}
			}
			context.popOnFailure();
			return null;
		}
	}

	@SuppressWarnings("all") private static final class f_1 extends Strategy
	{
		Strategy d_1;

		Strategy e_1;

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			ITermFactory termFactory = context.getFactory();
			Fail28:
			{
				IStrategoTerm term1 = term;
				Success5:
				{
					Fail29:
					{
					IStrategoTerm g_1 = null;
					IStrategoTerm i_1 = null;
					IStrategoTerm h_1 = null;
					IStrategoTerm j_1 = null;
					i_1 = term;
					term = d_1.invoke(context, term);
					if(term == null) {
						break Fail29;
					}
					g_1 = term;
					term = i_1;
					j_1 = i_1;
					term = crush_3_0.instance.invoke(context, term, lifted12.instance, e_1, this);
					if(term == null) {
						break Fail29;
					}
					h_1 = term;
					term = j_1;
					IStrategoList list0;
					list0 = checkListTail(h_1);
					if(list0 == null) {
						break Fail29;
					}
					term = (IStrategoTerm)termFactory.makeListCons(g_1, list0);
					if(true) {
						break Success5;
					}
					}
				term = crush_3_0.instance.invoke(context, term1, lifted13.instance, e_1, this);
				if(term == null) {
					break Fail28;
				}
				}
				if(true) {
					return term;
				}
			}
			return null;
		}
	}

	@SuppressWarnings("all") private static final class lifted0 extends Strategy
	{
		public static final lifted0 instance = new lifted0();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			Fail36:
			{
			term = try_1_0.instance.invoke(context, term, lifted1.instance);
			if(term == null) {
				break Fail36;
			}
			if(true) {
				return term;
			}
			}
		return null;
		}
	}

	@SuppressWarnings("all") private static final class lifted1 extends Strategy
	{
		public static final lifted1 instance = new lifted1();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			ITermFactory termFactory = context.getFactory();
			Fail37:
			{
				IStrategoTerm u_0 = null;
				IStrategoTerm v_0 = null;
				IStrategoTerm w_0 = null;
				if(term.getTermType() != IStrategoTerm.APPL || Benchmarks._consInvoke_2 != ((IStrategoAppl)term).getConstructor()) {
					break Fail37;
				}
				u_0 = term.getSubterm(0);
				IStrategoTerm arg0 = term.getSubterm(1);
				if(arg0.getTermType() != IStrategoTerm.LIST || ((IStrategoList)arg0).isEmpty()) {
					break Fail37;
				}
				v_0 = ((IStrategoList)arg0).head();
				IStrategoTerm arg1 = ((IStrategoList)arg0).tail();
				if(arg1.getTermType() != IStrategoTerm.LIST || ((IStrategoList)arg1).isEmpty()) {
					break Fail37;
				}
				w_0 = ((IStrategoList)arg1).head();
				IStrategoTerm arg2 = ((IStrategoList)arg1).tail();
				if(arg2.getTermType() != IStrategoTerm.LIST || !((IStrategoList)arg2).isEmpty()) {
					break Fail37;
				}
				term = termFactory.makeAppl(Benchmarks._consInvoke_2, new IStrategoTerm[]{u_0, (IStrategoTerm)termFactory.makeListCons(w_0, termFactory.makeListCons(v_0, (IStrategoList)Benchmarks.constNil0))});
				if(true) {
					return term;
				}
			}
			return null;
		}
	}

	@SuppressWarnings("all") private static final class lifted10 extends Strategy
	{
		Strategy a_1;

		Strategy b_1;

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			Fail32:
			{
			term = downup_2_0.instance.invoke(context, term, a_1, b_1);
			if(term == null) {
				break Fail32;
			}
			if(true) {
				return term;
			}
			}
		return null;
		}
	}

	@SuppressWarnings("all") private static final class lifted12 extends Strategy
	{
		public static final lifted12 instance = new lifted12();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			Fail31:
			{
			term = Benchmarks.constNil0;
			if(true) {
				return term;
			}
			}
		return null;
		}
	}

	@SuppressWarnings("all") private static final class lifted13 extends Strategy
	{
		public static final lifted13 instance = new lifted13();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			Fail30:
			{
			term = Benchmarks.constNil0;
			if(true) {
				return term;
			}
			}
		return null;
		}
	}

	@SuppressWarnings("all") private static final class lifted14 extends Strategy
	{
		TermReference l_1;

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			Fail27:
			{
			if(l_1.value == null) {
				break Fail27;
			}
			term = l_1.value;
			if(true) {
				return term;
			}
			}
		return null;
		}
	}

	@SuppressWarnings("all") private static final class lifted15 extends Strategy
	{
		TermReference o_1;

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			Fail26:
			{
			if(o_1.value == null) {
				break Fail26;
			}
			term = p_1.instance.invoke(context, o_1.value);
			if(term == null) {
				break Fail26;
			}
			if(true) {
				return term;
			}
			}
		return null;
		}
	}

	@SuppressWarnings("all") private static final class lifted2 extends Strategy
	{
		public static final lifted2 instance = new lifted2();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			Fail35:
			{
			term = Benchmarks.const0;
			if(true) {
				return term;
			}
			}
		return null;
		}
	}

	@SuppressWarnings("all") private static final class lifted8 extends Strategy
	{
		Strategy y_0;

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			Fail34:
			{
			term = bottomup_1_0.instance.invoke(context, term, y_0);
			if(term == null) {
				break Fail34;
			}
			if(true) {
				return term;
			}
			}
		return null;
		}
	}

	@SuppressWarnings("all") private static final class lifted9 extends Strategy
	{
		Strategy z_0;

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			Fail33:
			{
			term = topdown_1_0.instance.invoke(context, term, z_0);
			if(term == null) {
				break Fail33;
			}
			if(true) {
				return term;
			}
			}
		return null;
		}
	}

	@SuppressWarnings("all") private static final class p_1 extends Strategy
	{
		public static final p_1 instance = new p_1();

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			Fail24:
			{
			IStrategoTerm term3 = term;
			Success4:
			{
				Fail25:
				{
				if(term.getTermType() != IStrategoTerm.LIST || !((IStrategoList)term).isEmpty()) {
					break Fail25;
				}
				if(true) {
					break Success4;
				}
				}
			term = term3;
			IStrategoTerm n_1 = null;
			TermReference o_1 = new TermReference();
			if(term.getTermType() != IStrategoTerm.LIST || ((IStrategoList)term).isEmpty()) {
				break Fail24;
			}
			n_1 = ((IStrategoList)term).head();
			if(o_1.value == null) {
				o_1.value = ((IStrategoList)term).tail();
			} else
				if(o_1.value != ((IStrategoList)term).tail() && !o_1.value.match(((IStrategoList)term).tail())) {
					break Fail24;
				}
			term = n_1;
			lifted15 lifted150 = new lifted15();
			lifted150.o_1 = o_1;
			term = at_end_1_0.instance.invoke(context, term, lifted150);
			if(term == null) {
				break Fail24;
			}
			}
			if(true) {
				return term;
			}
			}
		return null;
		}
	}

	@SuppressWarnings("all") private static final class w_1 extends Strategy
	{
		Strategy v_1;

		@Override public IStrategoTerm invoke(Context context, IStrategoTerm term)
		{
			ITermFactory termFactory = context.getFactory();
			Fail22:
			{
				IStrategoTerm term4 = term;
				Success3:
				{
					Fail23:
					{
					IStrategoTerm q_1 = null;
					IStrategoTerm r_1 = null;
					IStrategoTerm s_1 = null;
					IStrategoTerm t_1 = null;
					IStrategoTerm u_1 = null;
					if(term.getTermType() != IStrategoTerm.LIST || ((IStrategoList)term).isEmpty()) {
						break Fail23;
					}
					q_1 = ((IStrategoList)term).head();
					r_1 = ((IStrategoList)term).tail();
					IStrategoList annos0 = term.getAnnotations();
					u_1 = annos0;
					s_1 = q_1;
					term = this.invoke(context, r_1);
					if(term == null) {
						break Fail23;
					}
					t_1 = term;
					IStrategoList list1;
					list1 = checkListTail(t_1);
					if(list1 == null) {
						break Fail23;
					}
					term = termFactory.annotateTerm((IStrategoTerm)termFactory.makeListCons(s_1, list1), checkListAnnos(termFactory, u_1));
					if(true) {
						break Success3;
					}
					}
				term = term4;
				if(term.getTermType() != IStrategoTerm.LIST || !((IStrategoList)term).isEmpty()) {
					break Fail22;
				}
				term = v_1.invoke(context, term);
				if(term == null) {
					break Fail22;
				}
				}
				if(true) {
					return term;
				}
			}
			return null;
		}
	}
}