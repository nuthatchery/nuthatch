package nuthatch.benchmark;

public abstract class Benchmark implements Runnable {
	public static int N = 5000;
	private String name;

	private int n;

	private String variant;

	public Benchmark(String name, int n, String variant) {
		this.name = name;
		this.n = n;
		this.variant = variant;
	}

	public Benchmark(String name, String variant) {
		this(name, N, variant);
	}

	@Override
	public void run() {
		long t = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			doIt();
		}
		t = System.currentTimeMillis() - t;
		System.out.printf(
				"%-18s %12s: %6dms, %d iterations, %5.0fµs per iteration%n",
				name, variant, t, n, 1000.0 * t / n);
		if (!check()) {
			System.out.println("  WARNING: check failed");
		}
	}

	protected abstract boolean check();

	protected abstract void doIt();

}
