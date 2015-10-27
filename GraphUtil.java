package assign8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 * Utility class containing methods for operating on graphs. 
 *
 * @author Erin Parker & Erik Martin & Nick Porter
 */
public class GraphUtil {
	
	public static void main(String[] args) {
		
		// build a sample graph
		Graph g = new Graph();

		g.addEdge("V1", "V3");
		g.addEdge("V1", "V4");
		g.addEdge("V2", "V4");
		g.addEdge("V2", "V1");
		g.addEdge("V4", "V5");
		g.addEdge("V5", "V3");
		g.addEdge("V6", "V7");

		g.generateDotFile("src/assign8/graph.dot");

		// build another sample graph
		g = new Graph();
		g.setDirected(false);
		g.addEdge("0", "1");
		g.addEdge("0", "2");
		g.addEdge("0", "7");
		g.addEdge("1", "2");
		g.addEdge("2", "3");
		g.addEdge("3", "4");
		g.addEdge("3", "5");
		g.addEdge("3", "6");
		g.addEdge("4", "5");
		g.addEdge("5", "6");
		g.addEdge("7", "1");
		g.addEdge("7", "6");

		g.generateDotFile("src/assign8/graph2.dot");
			
		System.out.println(GraphUtil.breadthFirstSearch("src/assign8/examplegraph8.dot", "San Diego", "Atlanta"));

	}
	

	/**
	 * Performs a topological sort of the vertices in a directed acyclic graph. (See Lecture 14 for the algorithm.)
	 * 
	 * Throws an UnsupportedOperationException if the graph is undirected or cyclic.
	 * 
	 * @param filename
	 *          -- Name of a file in DOT format, which specifies the graph to be sorted.
	 * @return a list of the vertex names in sorted order
	 */
	public static List<String> topologicalSort(String filename) {
		//Generate a graph from the file
		Graph g = buildGraphFromDot(filename);
		Map<String, Vertex> vertices = g.vertices();
		  
		//Check to make sure the graph is directed
		if(!g.isDirected())
			throw new UnsupportedOperationException("Graph is not directed.");
		
		Queue<Vertex> verticesToBeVisited = new LinkedList<Vertex>();
		List<Vertex> verticesAlreadyVisited = new ArrayList<Vertex>();
		
		//Add all "root vertices" to the queue
		for(Map.Entry<String, Vertex> entry : vertices.entrySet()) {
			Vertex v = entry.getValue();
			if(v.getInDegree() == 0)
				verticesToBeVisited.offer(v);
		}
		
		//While there are more vertices to process,
		//Tell each vertex's "children" that it has been visited
		while(!verticesToBeVisited.isEmpty()) {
			Vertex x = verticesToBeVisited.poll();
			verticesAlreadyVisited.add(x);
			
			Iterator<Edge> iter = x.edges();
			while(iter.hasNext()) {
				Vertex w = iter.next().getNext();
				//Make sure the graph is not cyclic
				if(verticesAlreadyVisited.contains(w))
					throw new UnsupportedOperationException("Graph is cyclic.");
				w.setInDegree(w.getInDegree()-1);
				if(w.getInDegree() == 0)
					verticesToBeVisited.offer(w);
			}
		}
		
		//Output the sorted list
		ArrayList<String> output = new ArrayList<>();
		for(Vertex v : verticesAlreadyVisited) {
			output.add(v.getName());
		}
		return output;
	}

	/**
	 * Performs a breadth-first search of a graph to determine the shortest path from a starting vertex to an ending vertex.
	 * (See Lecture 14 for the algorithm.)
	 * 
	 * Throws an UnsupportedOperationException if the graph is undirected or if the starting or ending vertex does not exist in the graph.
	 * 
	 * @param filename
	 *          -- Name of a file in DOT format, which specifies the graph to be sorted.
	 * @param start
	 *          -- Name of the starting vertex in the path.
	 * @param end
	 *          -- Name of the ending vertex in the path.
	 * @return a list of the vertices that make up the shortest path from the starting vertex (inclusive) to the ending vertex (inclusive).
	 */
	public static List<String> breadthFirstSearch(String filename, String start, String end) {
  		//Generate a graph from the file
  		Graph g = buildGraphFromDot(filename);
  		Map<String, Vertex> vertices = g.vertices();
  		
  		//Check to see if the vertices are in our collection
		if(!vertices.containsKey(start) || !vertices.containsKey(end))
			throw new UnsupportedOperationException("Vertices not in graph.");
		if(!g.isDirected())
			throw new UnsupportedOperationException("Graph is not directed.");
		
		Queue<Vertex> verticesToBeVisited = new LinkedList<Vertex>();
		
		//Set distances from start to infinity
		Iterator<String> iter = vertices.keySet().iterator();
		while(iter.hasNext()) {
			String s = iter.next();
			vertices.get(s).setDistFromStart(Integer.MAX_VALUE);
			vertices.get(s).setPrev(null);
		}
		
		//Put our starting vertex into our queue of vertices to be processed
		Vertex v = vertices.get(start);
		v.setDistFromStart(0);
		verticesToBeVisited.offer(v);
		
		//While we have more vertices to visit, visit them in order of their insertion (FIFO)
		while(!verticesToBeVisited.isEmpty()) {
			v = verticesToBeVisited.poll();
			
			Iterator<Edge> itr = v.edges();
			Vertex v1;
			while(itr.hasNext()) {
				v1 = itr.next().getNext();
				//If it would be faster to get to v1 through v, update the path
				if(v1.getDistFromStart() > v.getDistFromStart() + 1) {
					v1.setPrev(v);
					verticesToBeVisited.offer(v1);
				}
				//If we have found the end vertex, build a list and return it
				if(v1.getName().equals(end)) {
					ArrayList<String> arr = new ArrayList<String>();
					Vertex ver = v1;
					while(ver.getPrev() != null) {
						arr.add(ver.getName());
						ver = ver.getPrev();
					}
					arr.add(ver.getName());
					Collections.reverse(arr);
					return arr;
				}
			}
		}
		return new ArrayList<String>();
  	}

	/**
	 * Builds a graph according to the edges specified in the given DOT file (e.g., "a -- b" or "a -> b").
	 * Accepts directed ("digraph") or undirected ("graph") graphs.
	 * 
	 * Accepts many valid DOT files (see examples posted with assignment).
	 * --accepts \\-style comments 
	 * --accepts one edge per line or edges terminated with ; 
	 * --does not accept attributes in [] (e.g., [label = "a label"])
	 * 
	 * @param filename
	 *          -- name of the DOT file
	 */
	@SuppressWarnings("resource")
	private static Graph buildGraphFromDot(String filename) {
		// creates a new, empty graph (CHANGE AS NEEDED)
	    Graph g = new Graph();
	
	    Scanner s = null;
	    try {
	    	s = new Scanner(new File(filename)).useDelimiter(";|\n");
	    } catch (FileNotFoundException e) {
	    	System.out.println(e.getMessage());
	    }
	
	    // Determine if graph is directed or not (i.e., look for "digraph id {" or
	    // "graph id {")
	    String line = "", edgeOp = "";
	    while (s.hasNext()) {
	    	line = s.next();
	    	
	    	// Skip //-style comments.
	    	line = line.replaceFirst("//.*", "");
	
	    	if (line.indexOf("digraph") >= 0) {
	    		g.setDirected(true); // Denotes that graph is directed (CHANGE AS NEEDED)
	    		edgeOp = "->";
	    		line = line.replaceFirst(".*\\{", "");
	    		break;
	    	}
	    	if (line.indexOf("graph") >= 0) {
	    		g.setDirected(false); // Denotes that graph is undirected (CHANGE AS NEEDED)
	    		edgeOp = "--";
	    		line = line.replaceFirst(".*\\{", "");
	    		break;
	    	}
	    }

	    // Look for edge operators -- (or ->) and determine the left and right
	    // vertices for each edge.
	    while (s.hasNext()) {
	    	String[] substring = line.split(edgeOp);

	    	for (int i = 0; i < substring.length - 1; i += 2) {
	    		// remove " and trim whitespace from node string on the left
	    		String vertex1 = substring[0].replace("\"", "").trim();
	    		// if string is empty, try again
	    		if (vertex1.equals(""))
	    			continue;

	    		// do the same for the node string on the right
	    		String vertex2 = substring[1].replace("\"", "").trim();
	    		if (vertex2.equals(""))
	    			continue;

	    		// add edge between vertex1 and vertex2 (CHANGE AS NEEDED)
	    		g.addEdge(vertex1, vertex2);
	    	}

	    	// do until the "}" has been read
	    	if (substring[substring.length - 1].indexOf("}") >= 0)
	    		break;

	    	line = s.next();

	    	// Skip //-style comments.
	    	line = line.replaceFirst("//.*", "");
	    }
	    return g;
	}
}
