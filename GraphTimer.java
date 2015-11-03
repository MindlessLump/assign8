package assign8;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;

public class GraphTimer {

	// Sample size controls
	private static int startSize   = 10000;
	private static int endSize     = 200000;
	private static int stepSize    = 10000;
	private static int timesToLoop = 10;
	private static String file = "src/assign8/timergraph.dot";
	
	private static long startTime, midpointTime, stopTime;					// Timer variables
	private static DecimalFormat formatter = new DecimalFormat("0000E0");	// Time string formatter
	
	public static void main(String[] args) throws NullPointerException {
		printHeader("Experiment 1: Acyclic Breadth First Search");
		experiment1();
		printHeader("Experiment 2: Cyclic Breadth First Search");
		experiment2();
		printHeader("Experiment 3: Acyclic Breadth First Search With Edge Factor of 6");
		experiment3();
		printHeader("Experiment 4: Topological Sort");
		experiment4();
		printHeader("Experiment 5: Toplogical Sort With Edge Factor of 6");
		experiment5();
	}
	/**
	 * Generates a random dot file based on the given parameters.
	 * Based on code from Lab 7.
	 * @param fileName name of the file to be saved
	 * @param edgeScale the parameter that controls the factor of edges to vertices
	 * @param numOfVertices number of vertices
	 * @param cyclic cyclic if true, otherwise acyclic.
	 */
	public static void generateDotFile(String fileName, int edgeScale, int numOfVertices, boolean cyclic) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(fileName);
		} catch (IOException e) {
			System.out.println(e);
		}

		Random rng = new Random();

		// Construct a digraph
		String edgeOp = "->";
		out.println("digraph G {");

		// generate a list of vertices
		String[] vertex = new String[numOfVertices];
		for (int i = 0; i < numOfVertices; i++) {
			vertex[i] = "v" + i;
		}

		// randomly connect the vertices using 2 * |V| edges
		if (cyclic) {
			for (int i = 0; i < edgeScale * numOfVertices; i++) {
				int rand1 = rng.nextInt(numOfVertices);
				int rand2 = rng.nextInt(numOfVertices);

				out.println("\t" + "\"" + vertex[rand1] + "\"" + edgeOp + "\""
						+ vertex[rand2] + "\"");
			}
		}
		// Ensure that the graph builds downwards to avoid cycles
		else {
			int nextVert;
			for (int j = 0; j < edgeScale; j++) {
				for (int i = 0; i < numOfVertices - 1; i++) {
					// Make sure to run it at least one time.
					do {
						nextVert = rng.nextInt(numOfVertices - i) + (i + 1)
								- 1;
					} while (nextVert <= i || nextVert > vertex.length - 1);

					out.println("\t" + "\"" + vertex[i] + "\"" + edgeOp + "\""
							+ vertex[nextVert] + "\"");
				}
			}
		}

		out.println("}");
		out.close();
	}
	
	private static void experiment1() {
		
		for(int n = startSize; n <= endSize; n += stepSize) {
			
			Random r = new Random();
			generateDotFile(file, 2, n, false);
			Graph g = GraphUtil.buildGraphFromDot(file);
			Map<String, Vertex> m = g.vertices();
			
			String startKey = "v" + r.nextInt(m.size());
			String endKey = "v" + r.nextInt(m.size());
			
			String start = m.get(startKey).getName();
			String end = m.get(endKey).getName();
			
			//Map is key/value, indexs can't be used.
			
			//Make sure there is a path between the two random vertices
			while(!g.thereIsAPath(start, end)) {

				startKey = "v" + r.nextInt(m.size());
				endKey = "v" + r.nextInt(m.size());
				
				start = m.get(startKey).getName();
				end = m.get(endKey).getName();
			}
			
			System.out.print(n + "\t");

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) { // empty block
				;
			}

			// Start timing
			startTime = System.nanoTime();

			for(int i = 0; i < timesToLoop; i++) {
				GraphUtil.breadthFirstSearch(file, start, end);
			}

			midpointTime = System.nanoTime();

			// Calculating overhead
			for(int i = 0; i < timesToLoop; i++) {
				GraphUtil.buildGraphFromDot(file);
			}

			stopTime = System.nanoTime();

			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));	
		}
	}
	
	private static void experiment2() {
		
		for(int n = startSize; n <= endSize; n += stepSize) {
			
			Random r = new Random();
			generateDotFile(file, 2, n, true);
			Graph g = GraphUtil.buildGraphFromDot(file);
			Map<String, Vertex> m = g.vertices();
			
			String startKey = "v" + r.nextInt(m.size());
			String endKey = "v" + r.nextInt(m.size());
			
			String start = "";
			String end = "";
			boolean found = false;
			
			while(!found) {
				try {
					start = m.get(startKey).getName();
					end = m.get(endKey).getName();
					found = true;
				} catch (NullPointerException e) {
					startKey = "v" + r.nextInt(m.size());
					endKey = "v" + r.nextInt(m.size());
				}
			}
			
			//Map is key/value, indices can't be used.
			
			//Make sure there is a path between the two random vertices
			while(!g.thereIsAPath(start, end)) {

				startKey = "v" + r.nextInt(m.size());
				endKey = "v" + r.nextInt(m.size());
				
				start = m.get(startKey).getName();
				end = m.get(endKey).getName();
			}
			
			System.out.print(n + "\t");

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) { // empty block
				;
			}

			// Start timing
			startTime = System.nanoTime();

			for(int i = 0; i < timesToLoop; i++) {
				GraphUtil.breadthFirstSearch(file, start, end);
			}

			midpointTime = System.nanoTime();

			// Calculating overhead
			for(int i = 0; i < timesToLoop; i++) {
				GraphUtil.buildGraphFromDot(file);
			}

			stopTime = System.nanoTime();

			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));	
		}
	}
	
	private static void experiment3() {
		
		for(int n = startSize; n <= endSize; n += stepSize) {
			
			Random r = new Random();
			generateDotFile(file, 6, n, true);
			Graph g = GraphUtil.buildGraphFromDot(file);
			Map<String, Vertex> m = g.vertices();
			
			String startKey = "v" + r.nextInt(m.size());
			String endKey = "v" + r.nextInt(m.size());
			
			String start = m.get(startKey).getName();
			String end = m.get(endKey).getName();
			
			//Map is key/value, indexs can't be used.
			
			//Make sure there is a path between the two random vertices
			while(!g.thereIsAPath(start, end)) {

				startKey = "v" + r.nextInt(m.size());
				endKey = "v" + r.nextInt(m.size());
				
				start = m.get(startKey).getName();
				end = m.get(endKey).getName();
			}
			
			System.out.print(n + "\t");

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) { // empty block
				;
			}

			// Start timing
			startTime = System.nanoTime();

			for(int i = 0; i < timesToLoop; i++) {
				GraphUtil.breadthFirstSearch(file, start, end);
			}

			midpointTime = System.nanoTime();

			// Calculating overhead
			for(int i = 0; i < timesToLoop; i++) {
				GraphUtil.buildGraphFromDot(file);
			}

			stopTime = System.nanoTime();

			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));	
		}
	}
	
	private static void experiment4() {
		
		for(int n = startSize; n <= endSize; n += stepSize) {
			
			generateDotFile(file, 2, n, false);	
			
			System.out.print(n + "\t");

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) { // empty block
				;
			}

			// Start timing
			startTime = System.nanoTime();

			for(int i = 0; i < timesToLoop; i++) {
				GraphUtil.topologicalSort(file);
			}

			midpointTime = System.nanoTime();

			// Calculating overhead
			for(int i = 0; i < timesToLoop; i++) {
				GraphUtil.buildGraphFromDot(file);
			}

			stopTime = System.nanoTime();

			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));	
		}
	}
	
	private static void experiment5() {
		
		for(int n = startSize; n <= endSize; n += stepSize) {
			
			generateDotFile(file, 6, n, false);	
			
			System.out.print(n + "\t");

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) { // empty block
				;
			}

			// Start timing
			startTime = System.nanoTime();

			for(int i = 0; i < timesToLoop; i++) {
				GraphUtil.topologicalSort(file);
			}

			midpointTime = System.nanoTime();

			// Calculating overhead
			for(int i = 0; i < timesToLoop; i++) {
				GraphUtil.buildGraphFromDot(file);
			}

			stopTime = System.nanoTime();

			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

			System.out.println(formatter.format(averageTime) + "\t|\t"
					+ formatter.format(averageTime / (Math.log10(n) / Math.log10(2))) + "\t\t"
					+ formatter.format(averageTime / n) + "\t\t"
					+ formatter.format(averageTime / (n * ((Math.log10(n) / Math.log10(2))))) + "\t\t"
					+ formatter.format(averageTime / (n * n)) + "\t\t"
					+ formatter.format(averageTime / (n * n * n)));	
		}
	}
	
	private static void printHeader(String title) {
		System.out.println("--------------------------  Timing Analysis: "+ title +" ---------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------");
		System.out.println("N\tT(N)  \t|\tT(N)/logN\tT(N)/N\t\tT(N)/Nlog(N)\tT(N)/N^2\tT(N)/N^3");
		System.out.println("-------------------------------------------------------------------------------------------------");
	}
	
}
