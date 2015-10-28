package assign8;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;

public class GraphTimer {

	public static void main(String[] args) {
		String file = "src/assign8/timergraph.dot";
		
		//Experiment 1
		System.out.println("Experiment 1: Acyclic Breadth First Search");
		System.out.println("Problem size:\tTotal time:\tO(1)\tO(logN)\tO(N)\tO(NlogN)\tO(N^2)");
		//Time breadth first search for an acyclic graph
		for(int vertices = 10000; vertices <= 200000; vertices += 10000) {
			//Prepare for the timing experiment
			Random r = new Random();
			generateDotFile(file, 2, vertices, false);
			Graph g = GraphUtil.buildGraphFromDot(file);
			Map<String, Vertex> m = g.vertices();
			String start = m.get(r.nextInt(m.size())).getName();
			String end = m.get(r.nextInt(m.size())).getName();
			
			//Make sure there is a path between the two random vertices
			while(!g.thereIsAPath(start, end)) {
				start = m.get(r.nextInt(m.size())).getName();
				end = m.get(r.nextInt(m.size())).getName();
			}
			
			//Do the timing
			long bfsStart = System.nanoTime();
			GraphUtil.breadthFirstSearch(file, start, end);
			long bfsMid = System.nanoTime();
			g = GraphUtil.buildGraphFromDot(file);
			long bfsEnd = System.nanoTime();
			
			//Do the calculations
			long total = (bfsMid - bfsStart) - (bfsEnd - bfsMid);
			double totalTime = (double)total;
			double probSize = (double)vertices;
			double compare1 = totalTime;
			double compareLogN = totalTime/(Math.log10(probSize)/Math.log10(2));
			double compareN = totalTime/probSize;
			double compareNlogN = totalTime/((Math.log10(probSize)/Math.log10(2))*probSize);
			double compareNSquared = totalTime/(probSize*probSize);
			
			//Do the output
			System.out.println(probSize + "\t" + totalTime + "\t" + compare1 + "\t" + compareLogN + "\t" + compareN
					+ "\t" + compareNlogN + "\t" + compareNSquared);
		}
		
		//Experiment 2
		System.out.println("Experiment 2: Cyclic Breadth First Search");
		System.out.println("Problem size:\tTotal time:\tO(1)\tO(logN)\tO(N)\tO(NlogN)\tO(N^2)");
		//Time breadth first search on a cyclic graph
		for(int vertices = 10000; vertices <= 200000; vertices += 10000) {
			//Prepare for the timing experiment
			Random r = new Random();
			generateDotFile(file, 2, vertices, true);
			Graph g = GraphUtil.buildGraphFromDot(file);
			Map<String, Vertex> m = g.vertices();
			String start = m.get(r.nextInt(m.size())).getName();
			String end = m.get(r.nextInt(m.size())).getName();
			
			//Make sure there is a path between the two random vertices
			while(!g.thereIsAPath(start, end)) {
				start = m.get(r.nextInt(m.size())).getName();
				end = m.get(r.nextInt(m.size())).getName();
			}
			
			//Do the timing
			long bfsStart = System.nanoTime();
			GraphUtil.breadthFirstSearch(file, start, end);
			long bfsMid = System.nanoTime();
			g = GraphUtil.buildGraphFromDot(file);
			long bfsEnd = System.nanoTime();
			
			//Do the calculations
			long total = (bfsMid - bfsStart) - (bfsEnd - bfsMid);
			double totalTime = (double)total;
			double probSize = (double)vertices;
			double compare1 = totalTime;
			double compareLogN = totalTime/(Math.log10(probSize)/Math.log10(2));
			double compareN = totalTime/probSize;
			double compareNlogN = totalTime/((Math.log10(probSize)/Math.log10(2))*probSize);
			double compareNSquared = totalTime/(probSize*probSize);
			
			//Do the output
			System.out.println(probSize + "\t" + totalTime + "\t" + compare1 + "\t" + compareLogN + "\t" + compareN
					+ "\t" + compareNlogN + "\t" + compareNSquared);
		}
		
		//Experiment 3
		System.out.println("Experiment 3: Acyclic Breadth First Search with Lots of Edges");
		System.out.println("Problem size:\tTotal time:\tO(1)\tO(logN)\tO(N)\tO(NlogN)\tO(N^2)");
		//Time breadth first search for an acyclic graph
		for(int vertices = 10000; vertices <= 200000; vertices += 10000) {
			//Prepare for the timing experiment
			Random r = new Random();
			generateDotFile(file, 6, vertices, false);
			Graph g = GraphUtil.buildGraphFromDot(file);
			Map<String, Vertex> m = g.vertices();
			String start = m.get(r.nextInt(m.size())).getName();
			String end = m.get(r.nextInt(m.size())).getName();
			
			//Make sure there is a path between the two random vertices
			while(!g.thereIsAPath(start, end)) {
				start = m.get(r.nextInt(m.size())).getName();
				end = m.get(r.nextInt(m.size())).getName();
			}
			
			//Do the timing
			long bfsStart = System.nanoTime();
			GraphUtil.breadthFirstSearch(file, start, end);
			long bfsMid = System.nanoTime();
			g = GraphUtil.buildGraphFromDot(file);
			long bfsEnd = System.nanoTime();
			
			//Do the calculations
			long total = (bfsMid - bfsStart) - (bfsEnd - bfsMid);
			double totalTime = (double)total;
			double probSize = (double)vertices;
			double compare1 = totalTime;
			double compareLogN = totalTime/(Math.log10(probSize)/Math.log10(2));
			double compareN = totalTime/probSize;
			double compareNlogN = totalTime/((Math.log10(probSize)/Math.log10(2))*probSize);
			double compareNSquared = totalTime/(probSize*probSize);
			
			//Do the output
			System.out.println(probSize + "\t" + totalTime + "\t" + compare1 + "\t" + compareLogN + "\t" + compareN
					+ "\t" + compareNlogN + "\t" + compareNSquared);
		}
		
		//Experiment 4
		System.out.println("Experiment 4: Topological sort");
		System.out.println("Problem size:\tTotal time:\tO(1)\tO(logN)\tO(N)\tO(NlogN)\tO(N^2)");
		//Time the topological sort
		for(int size = 10000; size <= 200000; size+= 10000) {
			//Prepare for the timing experiment
			generateDotFile(file, 2, size, false);
			
			//Do the timing
			long bfsStart = System.nanoTime();
			GraphUtil.topologicalSort(file);
			long bfsMid = System.nanoTime();
			Graph g = GraphUtil.buildGraphFromDot(file);
			long bfsEnd = System.nanoTime();
			
			//Do the calculations
			long total = (bfsMid - bfsStart) - (bfsEnd - bfsMid);
			double totalTime = (double)total;
			double probSize = (double)size;
			double compare1 = totalTime;
			double compareLogN = totalTime/(Math.log10(probSize)/Math.log10(2));
			double compareN = totalTime/probSize;
			double compareNlogN = totalTime/((Math.log10(probSize)/Math.log10(2))*probSize);
			double compareNSquared = totalTime/(probSize*probSize);
			
			//Do the output
			System.out.println(probSize + "\t" + totalTime + "\t" + compare1 + "\t" + compareLogN + "\t" + compareN
					+ "\t" + compareNlogN + "\t" + compareNSquared);
		}
		
		//Experiment 5
		System.out.println("Experiment 5: Topological sort with lots of edges");
		System.out.println("Problem size:\tTotal time:\tO(1)\tO(logN)\tO(N)\tO(NlogN)\tO(N^2)");
		//Time the topological sort
		for(int size = 10000; size <= 200000; size+= 10000) {
			//Prepare for the timing experiment
			generateDotFile(file, 6, size, false);
			
			//Do the timing
			long bfsStart = System.nanoTime();
			GraphUtil.topologicalSort(file);
			long bfsMid = System.nanoTime();
			Graph g = GraphUtil.buildGraphFromDot(file);
			long bfsEnd = System.nanoTime();
			
			//Do the calculations
			long total = (bfsMid - bfsStart) - (bfsEnd - bfsMid);
			double totalTime = (double)total;
			double probSize = (double)size;
			double compare1 = totalTime;
			double compareLogN = totalTime/(Math.log10(probSize)/Math.log10(2));
			double compareN = totalTime/probSize;
			double compareNlogN = totalTime/((Math.log10(probSize)/Math.log10(2))*probSize);
			double compareNSquared = totalTime/(probSize*probSize);
			
			//Do the output
			System.out.println(probSize + "\t" + totalTime + "\t" + compare1 + "\t" + compareLogN + "\t" + compareN
					+ "\t" + compareNlogN + "\t" + compareNSquared);
		}
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
	
}
