module nuthatch::benchmark::rascal::Benchmarks
import util::Benchmark;
import ValueIO;
import IO;

int N = 1000;

public void runBenchmarks() {
	term = readTextValueFile(|project://nuthatch-benchmark/src/nuthatch/benchmark/trees/java.aterm|);
	simpleTerm = readTextValueFile(|project://nuthatch-benchmark/src/nuthatch/benchmark/trees/Simple.java.aterm|);


	runBenchmarks(term);
}


void runBenchmarks(node term) {
//	benchmark("Commute", term, commuteBenchmark);	
	benchmark("TopDown", term, topdownBenchmark);	
	benchmark("BottomUp", term, bottomupBenchmark);	
}

void benchmark(str name, node term, void(node) f) {
	t = realTime(void() {
		i = 0;
		while(i < N) {
			f(term);
			i = i + 1;
		}
	});
	println("<name>\t(Rascal): <t>ms, <N> iterations, <(t*1000)/N>µs per iteration");
}

void commuteBenchmark(node tree) {
	tree = visit(tree) {
		case "Invoke"(m, [x, y]) =>  "Invoke"(m, [y, x]) 
	};
}
void topdownBenchmark(node term) {
	top-down visit(term) {
		default: ;
	};
}

void bottomupBenchmark(node term) {
	bottom-up visit(term) {
		default: ;
	};
}