import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TownGraphManager implements TownGraphManagerInterface{

	private TownGraph graph = new TownGraph();
	
	public TownGraphManager() {
	}
	
	/**
	 * Add a Road to the graph
	 * 
	 * @param town1 the name of the first Town
	 * @param town2 the name of the second Town
	 * @param weight the weight of the Road
	 * @param roadName the name of the Road
	 * 
	 * @return true if the operation was successful, or false otherwise
	 */
	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		
		try {
			//Create two new Towns from the passed Strings
			Town t1 = new Town (town1);
			Town t2 = new Town (town2);
			
			//Create the Road to join the two Towns
			graph.addEdge(t1, t2, weight, roadName);
		
			return true;
		}
		//If something goes wrong, return false (such as if the Towns aren't in the graph)
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Retrieve a string representation of a Road in the graph
	 * 
	 * @param town1 the first Town of the Road
	 * @param town2 the second Town of the Road
	 * 
	 * @return a String representing the requested Road
	 */
	@Override
	public String getRoad(String town1, String town2) {
		return graph.getEdge(new Town(town1), new Town(town2)).toString();
	}

	/**
	 * Add a Town to the graph
	 * 
	 * @param v the name of the Town to be added
	 * 
	 * @return true if the operation was successful, and false otherwise
	 */
	@Override
	public boolean addTown(String v) {
		
		try {
			graph.addVertex(new Town(v));
			return true;
		}
			catch (Exception e) {
			return false;
		}
	}

	/**
	 * Retrieve a given Town from the graph
	 * 
	 * @param name the name of the Town to be retrieved
	 * 
	 * @return the Town once found, or a null Town if not found
	 */
	@Override
	public Town getTown(String name) {
		
		Town returnTown = new Town(graph.getVertex(new Town(name)));
		
		return returnTown;
	}

	/**
	 * Check if the graph contains a given Town
	 * 
	 * @param v the Town to be searched for in the graph
	 * 
	 * @return true if the graph contains the Town, and false otherwise
	 */
	@Override
	public boolean containsTown(String v) {
		return graph.containsVertex(new Town(v));
	}

	/**
	 * Determine whether two Towns are connected by a Road.
	 * 
	 * @param town1 the name of the first Town
	 * @param town2 the name of the second Town
	 * 
	 * @return True if the Road is in the graph,
	 * and False if either there is no Road between the Towns
	 * or if either Town is not in the graph
	 */
	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		
		//Attempt to return the result of a generic contains() call
		try {
			return graph.containsEdge(new Town(town1), new Town(town2));
		}
		
		//If the Towns aren't in the graph, an Exception will be thrown, so return false
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Get a sorted list of all Roads in the graph
	 * 
	 * @return a sorted ArrayList<String> of all Roads in the graph
	 */
	@Override
	public ArrayList<String> allRoads() {
		
		//Create an empty TreeSet for holding and sorting the graph data
		TreeSet<Road> sortedRoads = new TreeSet<>();
		
		//Add the graph Road data to the sortedRoads set. The TreeSet structure will sort the Roads
		sortedRoads.addAll(graph.edgeSet());
		
		ArrayList<String> list = new ArrayList<>();
		
		//For each item in sortedRoads, add its toString representation to the return list
		for (Road road : sortedRoads) {
			list.add(road.toString());
		}
		
		return list;
	}

	/**
	 * Delete a Road from the graph
	 * 
	 * @param town1 the Road's starting Town
	 * @param town2 the Road's destination Town
	 * @param road the name of the Road
	 * 
	 * @return true if the operation was successful, and false otherwise
	 */
	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
				
		try {
			Road temp = graph.removeEdge(new Town(town1), new Town(town2), road);
			
			//removeEdge may return null if no removal was performed. If so, return false
			if (temp == null) return false;
			//If removal was successful, return true
			else return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Delete a Town from the graph, if it exists
	 * 
	 * @param v the name of the Town to be removed
	 * 
	 * @return true if the operation was successful, and false otherwise
	 */
	@Override
	public boolean deleteTown(String v) {
		
		try {
			graph.removeVertex(new Town(v));
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Generate an ArrayList<String> of all towns in the graph in alphabetical order
	 * 
	 * @return an ArrayList<String> of all towns in the graph in alphabetical order
	 */
	@Override
	public ArrayList<String> allTowns() {
		
		TreeSet<Town> towns = new TreeSet<>();
		ArrayList<String> townString = new ArrayList<>();
		
		towns.addAll(graph.vertexSet());
		
		for (Town town : towns) {
			townString.add(town.toString());
		}
		
		return townString;
	}

	/**
	 * Generate an ArrayList of the shortest path from town 1 to town 2
	 * 
	 * @param town1 the name of town 1
	 * @param town2 the name of town 2
	 * 
	 * @return an ArrayList of roads connecting the two towns together, or null if there is no path
	 */
	@Override
	public ArrayList<String> getPathSets(String town1, String town2) {
		try {
			return graph.shortestPath(new Town(town1), new Town(town2));
		}
		catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Generate an ArrayList of the shortest path from town 1 to town 2, specifying distance in miles
	 * 
	 * @param town1 the name of town 1
	 * @param town2 the name of town 2
	 * 
	 * @return an ArrayList of roads connecting the two towns together, or null if there is no path
	 */
	public ArrayList<String> getPath(String town1, String town2) {
		try {
			ArrayList<String> path = graph.shortestPath(new Town(town1), new Town(town2));
			
			//Because strings are immutable, create a returin list and append the word " miles" 
			//to each String to indicate unit of measure
			ArrayList<String> returnPath = new ArrayList<>();
			for (String str : path) {
				returnPath.add(new String (str + " miles"));
			}
			
			//Finally, add a String for the total mileage
			String str = String.format("Total miles: %d miles", graph.getDistance(new Town(town2)));
			returnPath.add(str);
			
			return returnPath;
		}
		//If something goes wrong, return null
		catch (Exception e) {
			return null;
		}
	}

}
