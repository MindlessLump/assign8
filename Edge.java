package assign8;

public class Edge {
	
	private Vertex next;
	
	public Edge(Vertex _next) {
		next = _next;
	}
	
	public Vertex getNext() {
		return next;
	}
	
	public String toString() {
		return next.getName();
	}
}
