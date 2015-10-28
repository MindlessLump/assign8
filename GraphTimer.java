package assign8;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GraphTimer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Generates a random dot file based on the given parameters.
	 * Based on code from Lab 7.
	 * @param fileName name of the file to be saved
	 * @param edgeScale the paramter that controls the factor of edges to vertices
	 * @param numOfVerticies number of vertices
	 * @param cyclic cyclic if true, otherwise acyclic.
	 */
	public static void generateDotFile(String fileName, int edgeScale, int numOfVerticies, boolean cyclic) {
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
		String[] vertex = new String[numOfVerticies];
		for (int i = 0; i < numOfVerticies; i++) {
			vertex[i] = "v" + i;
		}

		// randomly connect the vertices using 2 * |V| edges
		if (cyclic) {
			for (int i = 0; i < edgeScale * numOfVerticies; i++) {
				int rand1 = rng.nextInt(numOfVerticies);
				int rand2 = rng.nextInt(numOfVerticies);

				out.println("\t" + "\"" + vertex[rand1] + "\"" + edgeOp + "\""
						+ vertex[rand2] + "\"");
			}
		}
		// Ensure that the graph builds downwards
		else if (!cyclic) {
			int nextVert;
			for (int j = 0; j < edgeScale; j++) {
				for (int i = 0; i < numOfVerticies - 1; i++) {
					// Make sure to run it at least one time.
					do {
						nextVert = rng.nextInt(numOfVerticies - i) + (i + 1)
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
