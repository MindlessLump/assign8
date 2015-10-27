package assign8;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class representation of a vertex within a directed graph.
 * 
 * @author Erik Martin and Nick Porter
 */
public class Vertex {
	
	private String name; //The name of the vertex
	private LinkedList<Edge> adj; //The adjacency list of the edges that lead from this vertex
	private int distFromStart; //The distance from the vertex to the start (unweighted edges)
	private Vertex prev; //The previous vertex for use in tracing paths in a breadth first search
	private int inDegree; //The number of incoming edges for use in a topological sort
	
	/**
	 * Creates a new Vertex with the given name.
	 * @param _name The given name for this vertex.
	 */
	public Vertex(String _name) {
		name = _name;
		adj = new LinkedList<>();
		distFromStart = Integer.MAX_VALUE;
		prev = null;
		inDegree = 0;
	}
	
	/**
	 * Returns the name of this vertex.
	 * @return The vertex's name, as a String.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Adds a directed edge pointing from this vertex to the given vertex.
	 * @param nextVertex The vertex to which the new edge will point.
	 */
	public void addEdge(Vertex nextVertex) {
		adj.add(new Edge(nextVertex));
	}
	
	/**
	 * Returns an Iterator of the edges pointing away from this vertex.
	 * @return The edges that point away from this vertex, as an Iterator object.
	 */
	public Iterator<Edge> edges() {
		return adj.iterator();
	}
	
	/**
	 * Returns a string listing the name of this vertex, along with all of the vertices to which this vertex points.
	 */
	public String toString() {
		String s = "Vertex " + name + " adjacent to ";
		Iterator<Edge> itr = adj.iterator();
		while(itr.hasNext())
			s += itr.next() + " ";
		return s;
	}
	
	/**
	 * Sets the distance from the start to this vertex to the given value.
	 * @param d The new distance from the start.
	 */
	public void setDistFromStart(int d) {
		distFromStart = d;
	}
	
	/**
	 * Returns the distance from the start to this vertex.
	 * @return The distance, as an int.
	 */
	public int getDistFromStart() {
		return distFromStart;
	}
	
	/**
	 * Sets the previous vertex along a shortest path to the given vertex.
	 * @param v The new previous vertex.
	 */
	public void setPrev(Vertex v) {
		prev = v;
	}
	
	/**
	 * Returns the previous vertex along the shortest path from the start.
	 * @return The previous vertex on the shortest path, as a Vertex object.
	 */
	public Vertex getPrev() {
		return prev;
	}
	
	/**
	 * Sets the number of incoming edges to the given number.
	 * @param deg The new number of incoming edges.
	 */
	public void setInDegree(int deg) {
		inDegree = deg;
	}
	
	/**
	 * Returns the number of edges pointing into this vertex.
	 * @return The number of incoming edges, as an int.
	 */
	public int getInDegree() {
		return inDegree;
	}
}
