import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class TownGraphTest_STUDENT {

	public TownGraph graph;
	Town[] towns = new Town[6];
	Road[] roads = new Road[7];
	
	@Before
	public void setUp() throws Exception {
		graph = new TownGraph();
		
		for (int i = 0; i < 6; i++) {
			towns[i] = new Town("Town " + i);
			graph.addVertex(towns[i]);
		}
		
		roads[0] = new Road(towns[0], towns[1], 14, "Road 0");
		roads[1] = new Road(towns[1], towns[2], 43, "Road 1");
		roads[2] = new Road(towns[2], towns[3], 34, "Road 2");
		roads[3] = new Road(towns[3], towns[4], 20, "Road 3");
		roads[4] = new Road(towns[4], towns[0], 33, "Road 4");
		roads[5] = new Road(towns[2], towns[4], 10, "Road 5");
		roads[6] = new Road(towns[5], towns[1], 5, "Road 6");
		
		graph.addEdge(towns[0], towns[1], 14, "Road 0");
		graph.addEdge(towns[1], towns[2], 43, "Road 1");
		graph.addEdge(towns[2], towns[3], 34, "Road 2");
		graph.addEdge(towns[3], towns[4], 20, "Road 3");
		graph.addEdge(towns[4], towns[0], 33, "Road 4");
		graph.addEdge(towns[2], towns[4], 10, "Road 5");
		graph.addEdge(towns[5], towns[1], 5, "Road 6");
	}
	
	@After
	public void tearDown() throws Exception {
		graph = null;
	}
	
	@Test
	public void testAddEdge() {
		assertTrue(graph.containsEdge(towns[1],towns[2]));
		assertTrue(graph.containsEdge(towns[2], towns[3]));
		assertTrue(graph.containsEdge(towns[3], towns[4]));
		assertTrue(graph.containsEdge(towns[5], towns[1]));
	}
	
	@Test
	public void testAddVertex() {
		Set<Town> set = graph.vertexSet();
		assertTrue(set.contains(towns[0]));
		assertTrue(set.contains(towns[1]));
		assertTrue(set.contains(towns[2]));
		assertTrue(set.contains(towns[3]));
		assertTrue(set.contains(towns[4]));
	}
	
	@Test
	public void testContainsEdge() {
		assertTrue(graph.containsEdge(towns[0], towns[1]));
		assertTrue(graph.containsEdge(towns[1], towns[5]));
		assertTrue(graph.containsEdge(towns[3], towns[4]));
		assertFalse(graph.containsEdge(towns[3], towns[5]));
	}
	
	@Test
	public void testContainsVertex() {
		for (int i = 0; i < towns.length; i++) {
			assertTrue(graph.containsVertex(towns[i]));
		}
	}
	
	@Test
	public void testDijstraShortestPath() {
		graph.dijkstraShortestPath(towns[1]);
		
		assertEquals(graph.getDistance(towns[2]), 43, 0.1);
		assertEquals(graph.getDistance(towns[4]), 47, 0.1);
		assertEquals(graph.getDistance(towns[5]), 5, 0.1);
		assertEquals(graph.getDistance(towns[3]), 67, 0.1);
		
	}
	
	@Test
	public void testEdgeSet() {
		TreeSet<Road> set = new TreeSet<Road>(graph.edgeSet());
		
		for (Road road : roads) {
			assertTrue(set.remove(road)); //TreeSet.remove returns true on a successful remove
		}
		
		assertTrue(set.size() == 0);
	}
	
	@Test
	public void testEdgesOf() {
		TreeSet<Road> set = new TreeSet<Road>(graph.edgesOf(towns[1]));
		
		assertTrue(set.remove(roads[0]));
		assertTrue(set.remove(roads[1]));
		assertFalse(set.remove(roads[2]));
		assertFalse(set.remove(roads[3]));
		
	}
	
	@Test
	public void testGetDistance() {
		graph.dijkstraShortestPath(towns[0]);
		
		assertEquals(graph.getDistance(towns[1]), 14, 0.1);
		assertEquals(graph.getDistance(towns[4]), 33, 0.1);
		assertEquals(graph.getDistance(towns[5]), 19, 0.1);
		
	}
	
	@Test
	public void testGetEdge() {
		assertEquals(roads[0], graph.getEdge(towns[0], towns[1]));
		assertEquals(roads[4], graph.getEdge(towns[4], towns[0]));
		assertEquals(roads[6], graph.getEdge(towns[5], towns[1]));
	}
	
	@Test
	public void testGetVertex() {
		assertEquals(towns[0], graph.getVertex(towns[0]));
		assertEquals(towns[1], graph.getVertex(towns[1]));
		assertEquals(towns[4], graph.getVertex(towns[4]));
	}
	
	@Test
	public void testRemoveEdge() {
		assertEquals(7, graph.edgeSet().size());
		graph.removeEdge(roads[0]);
		graph.removeEdge(roads[4]);
		assertEquals(5, graph.edgeSet().size());
		assertFalse(graph.containsEdge(towns[0], towns[4]));
		assertFalse(graph.containsEdge(towns[0], towns[1]));
		
		graph.removeEdge(towns[1], towns[2], "Road 1");
		assertFalse(graph.containsEdge(towns[1], towns[2]));
		
		graph.removeEdge(towns[1], towns[5], 5, "Road 6");
		assertFalse(graph.containsEdge(towns[1], towns[5]));
		
		assertEquals(3, graph.edgeSet().size());
	}
	
	@Test
	public void testRemoveVertex() {
		assertTrue(graph.removeVertex(towns[0]));
		assertTrue(graph.removeVertex(towns[1]));
		assertFalse(graph.removeVertex(towns[0]));
		
		assertEquals(4, graph.vertexSet().size());
	}
	
	@Test
	public void testShortestPath() {
		ArrayList<String> path = graph.shortestPath(towns[0], towns[3]);
		assertTrue(path.contains("Town 0 via Road 4 to Town 4 33"));
		assertTrue(path.contains("Town 4 via Road 3 to Town 3 20"));
		
		path = graph.shortestPath(towns[3], towns[5]);
		assertTrue(path.contains("Town 3 via Road 3 to Town 4 20"));
		assertTrue(path.contains("Town 4 via Road 4 to Town 0 33"));
		assertTrue(path.contains("Town 0 via Road 0 to Town 1 14"));
		assertTrue(path.contains("Town 1 via Road 6 to Town 5 5"));
	}
	
	@Test
	public void testVertexSet() {
		Set<Town> set = graph.vertexSet();
		
		assertEquals(6, set.size(), 0.1);
		
		for (Town town : towns) {
			assertTrue(set.remove(town));
		}
		
		assertEquals(0, set.size(), 0.1);
	}
}
