package assign8;

import java.util.Iterator;
import java.util.LinkedList;

public class Vertex {
	
	private String name;
	private LinkedList<Edge> adj;
	int distFromStart;
	private Vertex prev;
	
	public Vertex(String _name) {
		name = _name;
		adj = new LinkedList<>();
		distFromStart = -1;
		prev = null;
	}
	
	public String getName() {
		return name;
	}
	
	public void addEdge(Vertex nextVertex) {
		adj.add(new Edge(nextVertex));
	}
	
	public Iterator<Edge> edges() {
		return adj.iterator();
	}
	
	public String toString() {
		String s = "Vertex " + name + " adjacent to ";
		Iterator<Edge> itr = adj.iterator();
		while(itr.hasNext())
			s += itr.next() + " ";
		return s;
	}
	
	public void setDistFromStart(int d) {
		distFromStart = d;
	}
	
	public int getDistFromStart() {
		return distFromStart;
	}
	
	public void setPrev(Vertex v) {
		prev = v;
	}
	
	public Vertex getPrev() {
		return prev;
	}
}
