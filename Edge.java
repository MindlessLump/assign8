package assign8;

/**
 * Class representation of an edge on a directed graph.
 * 
 * @author Erik Martin and Nick Porter
 */
public class Edge {
	
	private Vertex next; //The vertex to which this edge points
	
	/**
	 * Creates a new edge pointing to the given vertex
	 * @param _next The vertex to which this edge will point
	 */
	public Edge(Vertex _next) {
		next = _next;
	}
	
	/**
	 * Returns the vertex to which this edge points
	 * @return The pointer vertex, as a Vertex object
	 */
	public Vertex getNext() {
		return next;
	}
	
	/**
	 * Returns the name of the pointer vertex, as a String
	 */
	public String toString() {
		return next.getName();
	}
}
