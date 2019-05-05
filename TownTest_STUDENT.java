import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TownTest_STUDENT {
	Town town1, town2, town3, town4;

	@Before
	public void setUp() throws Exception {
		 town1 = new Town ("Detroit");
		 town2 = new Town ("Jersey City");
		 town3 = new Town ("Anchorage");
		 town4 = new Town ("Detroit");
	}
	
	@After
	public void tearDown() throws Exception {
		town1 = town2 = town3 = town4 = null;
	}
	
	@Test
	public void testCompareTo() {
		
		assertTrue(town1.compareTo(town2) < 0);
		assertTrue(town2.compareTo(town3) > 0);
		assertTrue(town4.compareTo(town3) > 0);
		assertTrue(town4.compareTo(town1) == 0);
	}
	
	@Test
	public void testEquals() {
		assertTrue(town1.equals(town4));
		assertFalse(town2.equals(town3));
	}
	
	@Test
	public void testGetAdjacentTowns() {
		assertTrue("This class does not contain methods for instantiating adjacent towns", true);
	}
	
	@Test
	public void testGetLastTown() {
		town1.setLastTown(town2);
		town2.setLastTown(town3);
		town3.setLastTown(town4);
		
		assertEquals(town4, town3.getLastTown());
		assertEquals(town3, town2.getLastTown());
		assertEquals(town2, town1.getLastTown());
	}
	
	@Test
	public void testGetName() {
		assertEquals("Detroit", town1.getName());
		assertEquals("Jersey City", town2.getName());
		assertEquals("Anchorage", town3.getName());
		assertEquals("Detroit", town4.getName());
	}
	
	@Test
	public void testHashCode() {
		assertEquals(-1072080229, town1.hashCode());
		assertEquals(-890723045, town2.hashCode());
		assertEquals(1962139146, town3.hashCode());
		assertEquals(-1072080229, town4.hashCode());
	}
	
	@Test
	public void testSetLastTown() {
		town1.setLastTown(town2);
		town2.setLastTown(town3);
		town3.setLastTown(town4);
		
		assertEquals(town4, town3.getLastTown());
		assertEquals(town3, town2.getLastTown());
		assertEquals(town2, town1.getLastTown());
	}
	
	@Test
	public void testSetName() {
		town1.setName("St. Louis");
		town2.setName("Silver Spring");
		town3.setName("Miami");
		town4.setName("El Paso");
		
		assertEquals("St. Louis", town1.getName());
		assertEquals("Silver Spring", town2.getName());
		assertEquals("Miami", town3.getName());
		assertEquals("El Paso", town4.getName());
		
	}
	
	@Test
	public void testToString() {
		assertEquals("Detroit", town1.toString());
		assertEquals("Jersey City", town2.toString());
		assertEquals("Anchorage", town3.toString());
		assertEquals("Detroit", town4.toString());
	}
}
