package chneau.graph;

import java.io.PrintStream;
import java.util.Random;

public class GraphBenchmarks {

    public static void main(String[] args) throws Exception {
        runBenchmarks(System.out);
    }

    public static void runBenchmarks(PrintStream out) throws Exception {
        out.println("========================================================");
        out.println("Running Graph Benchmarks (Java 26 Suite)");
        out.println("========================================================");

        // Build a grid graph for testing
        int gridSize = 30; // 900 vertices
        Graph g = new Graph();
        Random rng = new Random(42);

        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                int u = r * gridSize + c;
                if (c + 1 < gridSize) {
                    int right = r * gridSize + (c + 1);
                    int cost = 1 + rng.nextInt(10);
                    g.addBiEdge(u, right, cost);
                }
                if (r + 1 < gridSize) {
                    int down = (r + 1) * gridSize + c;
                    int cost = 1 + rng.nextInt(10);
                    g.addBiEdge(u, down, cost);
                }
            }
        }

        int target = (gridSize - 1) * gridSize + (gridSize - 1);

        // Warmup C2 JIT
        for (int i = 0; i < 500; i++) {
            Dijkstra.shortest(g, 0, target);
        }

        // Benchmark 1: Dijkstra on 900-vertex grid graph
        int dijkstraRuns = 5_000;
        long t0 = System.nanoTime();
        for (int i = 0; i < dijkstraRuns; i++) {
            Dijkstra.shortest(g, 0, target);
        }
        long t1 = System.nanoTime();
        double d1 = (t1 - t0) / 1_000_000.0;
        out.printf("1. Dijkstra Shortest Path (%d calls, 900 nodes):   %6.1f ms (%.3f us/op)%n", dijkstraRuns, d1, ((t1 - t0) / 1000.0) / dijkstraRuns);

        // Benchmark 2: Graph Construction
        int constructRuns = 10_000;
        t0 = System.nanoTime();
        for (int i = 0; i < constructRuns; i++) {
            Graph small = new Graph();
            for (int k = 0; k < 20; k++) {
                small.addBiEdge(k, k + 1, k + 2);
            }
        }
        t1 = System.nanoTime();
        double d2 = (t1 - t0) / 1_000_000.0;
        out.printf("2. Graph Construction (%d calls):                 %6.1f ms (%.3f us/op)%n", constructRuns, d2, ((t1 - t0) / 1000.0) / constructRuns);

        // Benchmark 3: Graph Simplification
        int simplifyRuns = 5_000;
        t0 = System.nanoTime();
        for (int i = 0; i < simplifyRuns; i++) {
            Graph chain = new Graph();
            for (int k = 0; k < 20; k++) {
                chain.addBiEdge(k, k + 1, 1);
            }
            Simplify.graph(chain);
        }
        t1 = System.nanoTime();
        double d3 = (t1 - t0) / 1_000_000.0;
        out.printf("3. Topology Reduction / Simplify (%d calls):       %6.1f ms (%.3f us/op)%n", simplifyRuns, d3, ((t1 - t0) / 1000.0) / simplifyRuns);
        out.println("========================================================");
    }
}
