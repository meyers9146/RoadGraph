import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TownGraphManagerTest_STUDENT {
	private TownGraphManager graph;
	
	@Before
	public void setUp() throws Exception {
		graph = new TownGraphManager();
		
		for (int i = 0; i < 8; i++) {
			graph.addTown("Town " + i);
		}
		
		graph.addRoad("Town 1", "Town 2", 45, "Road 1");
		graph.addRoad("Town 2", "Town 3", 43, "Road 2");
		graph.addRoad("Town 4", "Town 5", 34, "Road 3");
		graph.addRoad("Town 5", "Town 6", 15, "Road 4");
		graph.addRoad("Town 3", "Town 1", 20, "Road 5");
		graph.addRoad("Town 5", "Town 1", 25, "Road 6");
		graph.addRoad("Town 7", "Town 5", 33, "Road 7");
	}
	
	@After
	public void tearDown() throws Exception {
		graph = null;
	}
	
	@Test
	public void testAddTown() {
		assertTrue(graph.containsTown("Town 1"));
		assertTrue(graph.containsTown("Town 3"));
		assertTrue(graph.containsTown("Town 5"));
		assertFalse(graph.containsTown("NotATown"));
	}
	
	@Test
	public void testAddRoad() {
		assertTrue(graph.containsRoadConnection("Town 1", "Town 2"));
		assertTrue(graph.containsRoadConnection("Town 4", "Town 5"));
		assertTrue(graph.containsRoadConnection("Town 3", "Town 1"));
		assertFalse(graph.containsRoadConnection("Town 4", "Town 8"));
		
	}
	
	@Test
	public void testAllRoads() {
		ArrayList<String> roads = graph.allRoads();
		
		assertEquals("Road 1", roads.get(0));
		assertEquals("Road 3", roads.get(2));
		assertEquals("Road 5", roads.get(4));
		assertEquals("Road 7", roads.get(6));
	}
	
	@Test
	public void testAllTowns() {
		ArrayList<String> towns = graph.allTowns();
		
		assertEquals("Town 1", towns.get(1));
		assertEquals("Town 2", towns.get(2));
		assertEquals("Town 3", towns.get(3));
		assertEquals("Town 4", towns.get(4));
		assertEquals("Town 5", towns.get(5));
	}
	
	@Test
	public void containsRoadConnection() {
		assertTrue(graph.containsRoadConnection("Town 1", "Town 2"));
		assertTrue(graph.containsRoadConnection("Town 4", "Town 5"));
		assertTrue(graph.containsRoadConnection("Town 3", "Town 1"));
		assertFalse(graph.containsRoadConnection("Town 4", "Town 8"));
		
		graph.deleteRoadConnection("Town 1", "Town 2", "Road 1");
		assertFalse(graph.containsRoadConnection("Town 1", "Town 2"));
	}
	
	@Test
	public void testDeleteRoadConnection() {
		assertEquals(7, graph.allRoads().size());
		
		graph.deleteRoadConnection("Town 1", "Town 2", "Road 1");
		graph.deleteRoadConnection("Town 5", "Town 6", "Road 4");
		graph.deleteRoadConnection("town 5", "Town 7", "Road 7");
		
		assertEquals(5, graph.allRoads().size());
	}
	
	@Test
	public void testDeleteTown() {
		assertEquals(8, graph.allTowns().size());
		
		graph.deleteTown("Town 1");
		graph.deleteTown("Town 3");
		graph.deleteTown("Town 5");
		graph.deleteTown("Town 7");
		
		assertEquals(4, graph.allTowns().size());
	}
	
	@Test
	public void testGetPath() {
		ArrayList<String> list = graph.getPath("Town 1", "Town 5");
		
		assertEquals("Town 1 via Road 6 to Town 5 25 miles", list.get(0));
		assertEquals("Total miles: 25 miles", list.get(1));
		
		list = graph.getPath("Town 7", "Town 2");
		
		assertEquals("Town 7 via Road 7 to Town 5 33 miles", list.get(0));
		assertEquals("Town 5 via Road 6 to Town 1 25 miles", list.get(1));
		assertEquals("Town 1 via Road 1 to Town 2 45 miles", list.get(2));
		assertEquals("Total miles: 103 miles", list.get(3));
	}
	
	@Test
	public void testGetPathSets() {
		ArrayList<String> list = graph.getPathSets("Town 1", "Town 5");
		
		assertEquals("Town 1 via Road 6 to Town 5 25", list.get(0));
		
		list = graph.getPathSets("Town 7", "Town 2");
		
		assertEquals("Town 7 via Road 7 to Town 5 33", list.get(0));
		assertEquals("Town 5 via Road 6 to Town 1 25", list.get(1));
		assertEquals("Town 1 via Road 1 to Town 2 45", list.get(2));
	}
	
	@Test
	public void testGetRoad() {
		assertEquals("Road 6", graph.getRoad("Town 5", "Town 1"));
		assertEquals("Road 1", graph.getRoad("Town 1", "Town 2"));
		assertEquals("Road 7", graph.getRoad("Town 7", "Town 5"));
	}
	
	@Test
	public void testGetTown() {
		assertEquals("Town 1", graph.getTown("Town 1").toString());
		assertEquals("Town 4", graph.getTown("Town 4").toString());
		assertEquals("Town 6", graph.getTown("Town 6").toString());
		assertEquals("Town 7", graph.getTown("Town 7").toString());
	}
	
	@Test
	public void testPopulateTownGraph() {
		File file = new File("US Towns(1).txt");
		
		graph = new TownGraphManager();
		
		try {
			graph.populateTownGraph(file);
		}
		catch (IOException e) {
			assertTrue("File failed to open", false);
		}
		
		assertEquals("I-65", graph.getRoad("Indianapolis", "Nashville"));
		assertEquals("I-75", graph.getRoad("Miami", "Atlanta"));
		
		ArrayList<String> list = graph.getPath("Atlanta", "Detroit");
		assertEquals("Atlanta via I-75 to Nashville 249 miles", list.get(0));
		assertEquals("Nashville via I-65 to Indianapolis 288 miles", list.get(1));
		assertEquals("Indianapolis via I-69 to Detroit 287 miles", list.get(2));
		assertEquals("Total 111 miles", list.get(3));
		
	}
}
