package assign8;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
