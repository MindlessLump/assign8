package assign8;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
* JUnit test class for our Graph and GraphUtil.
* 
* @author Erik Martin and Nick Porter
*/

public class GraphTester {

	//Test ensures that invalid vertices return false
	@Test
	public void testThereIsAPath() {
		
		Graph g = new Graph();

		g.addEdge("V1", "V3");
		g.addEdge("V1", "V4");
		g.addEdge("V2", "V4");
		g.addEdge("V2", "V1");
		g.addEdge("V4", "V5");
		g.addEdge("V5", "V3");
		g.addEdge("V6", "V7");
		
		//V8 does not exist, but V3, does.
		assertFalse(g.thereIsAPath("V8", "V3"));
	}
	
	@Test
	public void testThereIsAPath1() {
		
		Graph g = new Graph();
		
		//More boundary checking
		assertFalse(g.thereIsAPath("V2", "V4"));
	}
	
	@Test
	public void testThereIsAPath2() {
		
		Graph g = new Graph();

		g.addEdge("V1", "V3");
		g.addEdge("V1", "V4");
		g.addEdge("V2", "V4");
		g.addEdge("V2", "V1");
		g.addEdge("V4", "V5");
		g.addEdge("V5", "V3");
		g.addEdge("V6", "V7");
		
		assertTrue(g.thereIsAPath("V1", "V4"));
	}
	
	@Test
	public void testThereIsAPath3() {
		
		Graph g = new Graph();

		g.addEdge("V1", "V3");
		g.addEdge("V1", "V4");
		g.addEdge("V2", "V4");
		g.addEdge("V2", "V1");
		g.addEdge("V4", "V5");
		g.addEdge("V5", "V3");
		g.addEdge("V6", "V7");
		
		assertTrue(g.thereIsAPath("V1", "V5"));
	}
	
	@Test
	public void testThereIsAPath4() {
		
		Graph g = new Graph();

		g.addEdge("V1", "V3");
		g.addEdge("V1", "V4");
		g.addEdge("V2", "V4");
		g.addEdge("V2", "V1");
		g.addEdge("V4", "V5");
		g.addEdge("V5", "V3");
		g.addEdge("V6", "V7");
		
		assertFalse(g.thereIsAPath("V6", "V1"));
	}

	@Test
	public void testGraph() {
		Graph g = new Graph();
		assertTrue(g.isDirected());
		
		g.addEdge("1", "2");
		assertTrue(g.thereIsAPath("1", "2"));
		assertTrue(g.vertices().containsKey("1"));
		assertTrue(g.vertices().containsKey("2"));
		
		g.setDirected(false);
		assertFalse(g.isDirected());
	}
	
	@Test
	public void testVertex() {
		Vertex v1 = new Vertex("Hello");
		Vertex v2 = new Vertex("World");
		assertEquals("Hello", v1.getName());
		assertEquals("World", v2.getName());
		
		v1.addEdge(v2);
		assertTrue(v1.edges().next().getNext().equals(v2));
		assertEquals("Vertex Hello adjacent to World ", v1.toString());
		
		assertEquals(Integer.MAX_VALUE, v1.getDistFromStart());
		assertEquals(Integer.MAX_VALUE, v2.getDistFromStart());
		v1.setDistFromStart(18);
		assertEquals(18, v1.getDistFromStart());
		
		assertNull(v1.getPrev());
		assertNull(v2.getPrev());
		v2.setPrev(v1);
		assertEquals(v1, v2.getPrev());
		
		assertEquals(0, v1.getInDegree());
		assertEquals(0, v2.getInDegree());
		v2.setInDegree(4);
		assertEquals(4, v2.getInDegree());
	}
	
	@Test
	public void testEdge() {
		Vertex v = new Vertex("a");
		Edge e = new Edge(v);
		assertEquals(v, e.getNext());
		
		assertEquals("a", e.toString());
	}
	
	@Test
	public void sparseBreadthFirstSearch() {
		ArrayList<String> ans = new ArrayList<>();
		ans.add("San Diego");
		ans.add("Salt Lake City");
		ans.add("Atlanta");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph8.dot", "San Diego", "Atlanta"));
		
		ans.remove("Atlanta");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph8.dot", "San Diego", "Salt Lake City"));
		
		assertEquals(0, GraphUtil.breadthFirstSearch("src/assign8/examplegraph8.dot", "San Diego", "San Diego").size());
	}
	
	@Test
	public void denseBreadthFirstSearch() {
		ArrayList<String> ans = new ArrayList<>();
		ans.add("A");
		ans.add("B");
		ans.add("C");
		ans.add("D");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph10.dot", "A", "D"));
		
		ans.clear();
		ans.add("C");
		ans.add("A");
		ans.add("B");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph10.dot", "C", "B"));
	}
	
	@Test
	public void testBFS1() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("vertex 2");
		ans.add("vertex 3");
		ans.add("vertex 4");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph.dot", "vertex 2", "vertex 4"));
		
	}
	
	@Test
	public void testBFS2() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("vertex 1");
		ans.add("vertex 4");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph.dot", "vertex 1", "vertex 4"));
		
	}
	
	@Test
	public void testBFS3() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("B");
		ans.add("A");
		ans.add("D");
		ans.add("C");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph2.dot", "B", "C"));
		
	}
	
	@Test
	public void testBFS4() {	
		// No paths
		assertEquals(new ArrayList<String>(), GraphUtil.breadthFirstSearch("src/assign8/examplegraph3.dot", "n1", "n5"));
		assertEquals(new ArrayList<String>(), GraphUtil.breadthFirstSearch("src/assign8/examplegraph3.dot", "n1", "n3"));
		assertEquals(new ArrayList<String>(), GraphUtil.breadthFirstSearch("src/assign8/examplegraph3.dot", "n0", "n5"));
		
	}
	
	@Test
	public void testBFS5() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("n1");
		ans.add("n0");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph3.dot", "n1", "n0"));
		
	}
	
	@Test
	public void testBFS6() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("u2");
		ans.add("u3");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph4.dot", "u2", "u3"));
		
	}
	
	@Test
	public void testBFS7() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("u0");
		ans.add("u2");
		ans.add("u3");
		ans.add("u4");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph4.dot", "u0", "u4"));
		
	}
	
	@Test
	public void testBFS8() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("2");
		ans.add("3");
		ans.add("4");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph5.dot", "2", "4"));
		
	}
	
	@Test
	public void testBFS9() {	
		//No path
		assertEquals(new ArrayList<String>(), GraphUtil.breadthFirstSearch("src/assign8/examplegraph5.dot", "5", "1"));
	
	}
	
	@Test
	public void testBFS10() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("5");
		ans.add("3");
		ans.add("4");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph6.dot", "5", "4"));
		
	}
	
	@Test
	public void testBFS11() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("1");
		ans.add("2");
		ans.add("7");
		ans.add("5");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph6.dot", "1", "5"));
		
	}
	
	@Test
	public void testBFS12() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("CS 1410");
		ans.add("CS 2420");
		ans.add("CS 3200*");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph7.dot", "CS 1410", "CS 3200*"));
		
	}
	
	@Test
	public void testBFS13() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("MATH 2250");
		ans.add("CS 3200*");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph7.dot", "MATH 2250", "CS 3200*"));
		
	}
	
	@Test
	public void testBFS14() {	
		ArrayList<String> ans = new ArrayList<>();
		ans.add("CS 2420");
		ans.add("CS 3500");
		ans.add("CS 3505");
		ans.add("CS 4500");
		assertEquals(ans, GraphUtil.breadthFirstSearch("src/assign8/examplegraph7.dot", "CS 2420", "CS 4500"));
		
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testBFS15() {	
		//Undirected graph
		assertEquals(null, GraphUtil.breadthFirstSearch("src/assign8/examplegraph9.dot", "CS 2420", "CS 4500"));
		
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testTopoSort() {
		//Cyclic
		GraphUtil.topologicalSort("src/assign8/examplegraph.dot");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testTopoSort2() {
		//Cyclic
		GraphUtil.topologicalSort("src/assign8/examplegraph2.dot");
	}
	
	@Test
	public void testTopoSort3() {
		//Ordering of first several don't matter.
		assertEquals("n0", GraphUtil.topologicalSort("src/assign8/examplegraph3.dot").get(5));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testTopoSort4() {
		//Cyclic
		GraphUtil.topologicalSort("src/assign8/examplegraph4.dot");
	}
	
	@Test
	public void testTopoSort5() {
		String ans = "[1, 2, 3, 4, 5]";
		assertEquals(ans, GraphUtil.topologicalSort("src/assign8/examplegraph5.dot").toString());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testTopoSort6() {
		//Cyclic
		GraphUtil.topologicalSort("src/assign8/examplegraph6.dot");
	}
	
	@Test
	public void testTopoSort7() {
		//2 valid sorts
		String ans = "[CS 1410, MATH 2250, CS 2420, CS 2100, CS 3200*, CS 3500, CS 3810, CS 3100*, CS 4150, CS 3505, CS 4400, CS 4500]";
		String ans1 = "[MATH 2250, CS 1410, CS 2420, CS 2100, CS 3200*, CS 3500, CS 3810, CS 3100*, CS 4150, CS 3505, CS 4400, CS 4500]";
		boolean test = GraphUtil.topologicalSort("src/assign8/examplegraph7.dot").toString().equals(ans) || 
				GraphUtil.topologicalSort("src/assign8/examplegraph7.dot").toString().equals(ans1); 
		assertTrue(test);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testTopoSort8() {
		//Cyclic
		GraphUtil.topologicalSort("src/assign8/examplegraph8.dot");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testTopoSort9() {
		//Undirected & cyclic
		GraphUtil.topologicalSort("src/assign8/examplegraph9.dot");
	}
	
}
