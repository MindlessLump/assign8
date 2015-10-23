package assign8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph {
	
	private Map<String, Vertex> vertices;
	private boolean directed = false;
	
	public Graph() {
		vertices = new HashMap<String, Vertex>();
	}
	
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
		if(!directed)
			vertex2.addEdge(vertex1);
	}
	
	public boolean thereIsAPath(String name1, String name2) {
		//Check to see if the vertices are in our collection
		if(!vertices.containsKey(name1) || !vertices.containsKey(name2))
			return false;
		
		Queue<Vertex> verticesToBeVisited = new LinkedList<Vertex>();
		List<Vertex> verticesAlreadyVisited = new ArrayList<Vertex>();
		
		Vertex v = vertices.get(name1);
		verticesToBeVisited.offer(v);
		
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
	
	public void setDirected(boolean d) {
		directed = d;
	}
	
	public boolean isDirected() {
		return directed;
	}
	
	public Map<String, Vertex> vertices() {
		return vertices;
	}
}
