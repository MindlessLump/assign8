package assign8;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Class representation of a graph, with vertices and edges
 * 
 * @author Erik Martin and Nick Porter
 */
public class Graph {
	
	private Map<String, Vertex> vertices; //HashMap of the vertices in the graph
	private boolean directed; //Whether the graph is directed (true) or undirected (false)
	
	/**
	 * Creates a new graph
	 */
	public Graph() {
		vertices = new HashMap<String, Vertex>();
		directed = true;
	}
	
	/**
	 * Adds an edge between the two named vertices (from the first to the second, if the graph is directed)
	 * If either vertex does not yet exist, that vertex will be created as well
	 * 
	 * @param name1 The name of the first vertex
	 * @param name2 The name of the second vertex
	 */
	public void addEdge(String name1, String name2) {
		//Create the first vertex
		Vertex vertex1;
		if(vertices.containsKey(name1))
			vertex1 = vertices.get(name1);
		else {
			vertex1 = new Vertex(name1);
			vertices.put(name1, vertex1);
		}
		//Add the second vertex
		Vertex vertex2;
		if(vertices.containsKey(name2))
			vertex2 = vertices.get(name2);
		else {
			vertex2 = new Vertex(name2);
			vertices.put(name2, vertex2);
		}
		//Add the edge(s) connecting them
		vertex1.addEdge(vertex2);
		vertex2.setInDegree(vertex2.getInDegree()+1);
		if(!directed) {
			vertex2.addEdge(vertex1);
			vertex1.setInDegree(vertex1.getInDegree()+1);
		}
	}
	
	/**
	 * Determines whether there is a path from one vertex to another
	 * 
	 * @param name1 The name of the first vertex
	 * @param name2 The name of the second vertex
	 * @return Whether there is a path from the first vertex to the second vertex, as a boolean
	 */
	public boolean thereIsAPath(String name1, String name2) {
		//Check to see if the vertices are in our collection
		if(!vertices.containsKey(name1) || !vertices.containsKey(name2))
			return false;
		
		Queue<Vertex> verticesToBeVisited = new LinkedList<Vertex>();
		List<Vertex> verticesAlreadyVisited = new ArrayList<Vertex>();
		
		Vertex v = vertices.get(name1);
		verticesToBeVisited.offer(v);
		
		//While there are more vertices to check, do so
		while(!verticesToBeVisited.isEmpty()) {
			v = verticesToBeVisited.poll();
			verticesAlreadyVisited.add(v);
			
			Iterator<Edge> itr = v.edges();
			while(itr.hasNext()) {
				v = itr.next().getNext();
				if(v.getName().equals(name2))
					return true;
				if(!verticesAlreadyVisited.contains(v))
					verticesToBeVisited.offer(v);
			}
		}
		return false;
	}
	
	/**
	 * Set whether the graph is a directed one
	 * @param d A boolean determining whether the graph is directed
	 */
	public void setDirected(boolean d) {
		directed = d;
	}
	
	/**
	 * Returns whether the graph is directed
	 * @return A boolean determining whether the graph is directed
	 */
	public boolean isDirected() {
		return directed;
	}
	
	/**
	 * Returns a Map of the vertices in the graph
	 * @return The vertices in the graph, as a Map
	 */
	public Map<String, Vertex> vertices() {
		return new HashMap<String, Vertex>(vertices);
	}
	
	/**
	 * Generates a .dot file based on the this graph. 
	 * @param filename name of the filed to be created and saved
	 */
	public void generateDotFile(String filename) {
		
		// default support directed graph
		String edgeOperator = "--";
		String graphType = "graph";
		
		if (isDirected()) {
			edgeOperator = "->";
			graphType = "digraph";
		}
		
		try {
			PrintWriter out = new PrintWriter(filename);
			out.println(graphType + " G {");

			if(vertices.isEmpty())
				out.println("");
			else {
				List<Vertex> alreadyVisited = new LinkedList<Vertex>();

				for(Vertex v : vertices.values()) {
					Iterator<Edge> edges = v.edges();
					while(edges.hasNext()) {
						Vertex x = edges.next().getNext();

						if(!alreadyVisited.contains(x))
							out.println("\t\"" + v.getName() + "\"" + edgeOperator + "\"" + x.getName() + "\"");
					}
					alreadyVisited.add(v);
				}
			}

			out.println("}");
			out.close();
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}
}
