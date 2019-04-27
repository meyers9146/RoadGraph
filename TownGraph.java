import java.io.File;
import java.util.*;


public class TownGraph implements GraphInterface<Town, Road> {

	HashSet<Town> towns;
	HashSet<Road> roads;
	
	/**
	 * Return a given Road from the graph. If the Road does not exist, returns null
	 * 
	 * @param sourceVertex the source Town for the Road
	 * @param destinationVertex the destination Town for the Road
	 * @return the Road if it exists, or null otherwise
	 */
	@Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		//If either argument Town is null, return null
		if (sourceVertex == null || destinationVertex == null) return null;
		
		//Iterate through the list of Roads. If a road is found that matches the input, it is returned
		//Otherwise, it returns false
		for (Road road : roads) {
			if (road.equals(new Road(sourceVertex, destinationVertex, "name"))) return road;
		}
		return null;
	}

	/**
	 * Add a new Road to the graph
	 * 
	 * @param sourceVertex the source Town for the Road
	 * @param destinationVertex the destination Town for the Road
	 * @param weight the length of the Road
	 * @param description the name of the Road
	 * 
	 * @return the added Road
	 * 
	 * @throws NullPointerException if passed a null Town
	 * @throws IllegalArgumentException if passed a Town that does not exist in the graph
	 */
	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) 
			throws NullPointerException, IllegalArgumentException{
		
		//If either argument Town is null, throw an exception
		if (sourceVertex == null || destinationVertex == null) {
			throw new NullPointerException();
		}
		
		//If the graph does not contain the argument Towns, throw an exception
		else if (!(this.containsVertex(sourceVertex) && this.containsVertex(destinationVertex))) {
			throw new IllegalArgumentException("The indicated Towns are not in the graph.");
		}
		
		//Otherwise, create a new Road object from the argument information
		Road toAdd = new Road(sourceVertex, destinationVertex, weight, description);
		
		//Add the Road to the graph
		roads.add(toAdd);
		
		//Return the added Road
		return toAdd;
	}

	/**
	 * Add a Town to the graph, if it doesn't exist already
	 * 
	 * @param v the Town to be added
	 * 
	 * @return true if successful, and false if unsuccessful or if the Town (or an equivalent) exists already
	 * 
	 * @throws NullPointerException if the argument Town is null
	 */
	@Override
	public boolean addVertex(Town v) throws NullPointerException{
		//If the argument Town is null, throw exception
		if (v == null) throw new NullPointerException();
		
		//If the Town or an equivalent is already in the graph, return false
		if (this.containsVertex(v)) return false;
		
		//Otherwise, add the Town
		try {
			towns.add(v);
			return true;
		}
		catch (Exception e) {}
		
		
		//Return false if there is a problem adding the Town
		return false;
	}

	/**
	 * Check to see if there is a Road connecting two given Towns
	 * 
	 * @param sourceVertex the source Town for the Road
	 * @param destinationVertex the destination Town for the Road
	 * 
	 * @return true if the Road exists in the graph, and false otherwise
	 */
	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		
		//Iterate through the roads list. If a Road is found matching the arguments, return true
		for (Road road : roads) {
			if (road.equals(new Road(sourceVertex, destinationVertex, "name"))) return true;
		}
		
		//Otherwise, return false
		return false;
	}

	/**
	 * Check to see if a given Town exists in the graph
	 * 
	 * @param v the Town to be searched for
	 * 
	 * @return true if the Town exists in the graph, and false otherwise
	 */
	@Override
	public boolean containsVertex(Town v) {
		
		//Iterate through the Towns list. If a match is found, return true
		for (Town town : towns) {
			if (town.equals(v)) return true;
		}
		
		//Return false if a match is not found
		return false;
	}

	/**
	 * Generate a set of all Roads connected to a given Town.
	 * If there are no Roads connecting a Town, an empty set is returned
	 * 
	 * @param vertex the Town to check for adjacent Roads
	 * 
	 * @return a set containing all adjacent Roads, or an empty set if none exist
	 */
	@Override
	public Set<Road> edgesOf(Town vertex) {
		Set<Road> edgeSet = new HashSet<>();
		
		//Iterate through the Roads set.
		//Any Road found that contains the town is an adjacent Road,
		//And is added to the return Set
		for (Road road : roads) {
			if (road.contains(vertex)) edgeSet.add(road);
		}
		
		return edgeSet;
	}

	/**
	 * Remove a Road, as specified by the Road's parameters.
	 * 
	 * @param sourceVertex the source Town for the Road
	 * @param destinationVertex the destination Town for the Road
	 * @param weight the weight for the Road
	 * @description the name of the Road
	 * 
	 * @return the Road that was removed, or null otherwise
	 */
	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		
		//If either argument Town is null, return null
		if (sourceVertex == null || destinationVertex == null) return null;
		
		//If the graph does not contain either argument Town, return null
		if (!(this.containsVertex(sourceVertex) && this.containsVertex(destinationVertex))) return null;
		
		//If the graph does not contain the edge, return null
		if (!this.containsEdge(sourceVertex, destinationVertex)) return null;
		
		//If the indicated weight is 0, or if the description is null, return null
		if (weight <= 0 || description == null) return null;
		
		//If all the above checks are good, remove the indicated Road
		Road toRemove = new Road(sourceVertex, destinationVertex, weight, description);
		roads.remove(toRemove);
		
		//Return the removed Road
		return toRemove;
	}
	
	/**
	 * Remove an unweighted Road, as specified by the Road's parameters.
	 * 
	 * @param sourceVertex the source Town for the Road
	 * @param destinationVertex the destination Town for the Road
	 * @description the name of the Road
	 * 
	 * @return the Road that was removed, or null otherwise
	 */
	public Road removeEdge(Town sourceVertex, Town destinationVertex, String description) {
		
		//If either argument Town is null, return null
		if (sourceVertex == null || destinationVertex == null) return null;
		
		//If the graph does not contain either argument Town, return null
		if (!(this.containsVertex(sourceVertex) && this.containsVertex(destinationVertex))) return null;
		
		//If the graph does not contain the edge, return null
		if (!this.containsEdge(sourceVertex, destinationVertex)) return null;

		//If all the above checks are good, remove the indicated Road
		Road toRemove = new Road(sourceVertex, destinationVertex, description);
		roads.remove(toRemove);
		
		//Return the removed Road
		return toRemove;
	}
	
	/**
	 * Remove a specific Road from the graph. Will remove both weighted and unweighted Roads
	 * 
	 * @param road the Road to be removed
	 * 
	 */
	public void removeEdge (Road road) {
		
		//Copy the fields of the argument road
		Town source = road.getSource();
		Town destination = road.getDestination();
		int weight = road.getWeight();
		String name = road.getName();
		
		//Call the appropriate removeEdge method depending on whether the Road is weighted
		//or unweighted
		if (weight == 0) {
			removeEdge(source, destination, weight, name);
		}
		else removeEdge(source, destination, name);
	}

	/**
	 * Remove a Town from the graph along with its connecting Roads
	 * 
	 * @param v the Town to be removed
	 * 
	 * @return true if the Town and its Roads are successfully removed, and false
	 * if the Town is not in the graph to begin with.
	 */
	@Override
	public boolean removeVertex(Town v) {
		
		//If the argument Town is not in the graph, return null
		if (this.containsVertex(v)) return false;
		
		//Otherwise, proceed with removing the Town
		//Create a set of the Roads connected to the Town
		Set<Road> roadSet = edgesOf(v);
		
		//Remove the Town from the Towns set
		towns.remove(v);
		
		//Iterate through the Road set to remove each Road from the graph
		for (Road road : roadSet) {
			roads.remove(road);
		}
		
		return true;
	}

	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Create a set of the Roads in the graph.
	 * 
	 * @return a set of the Roads in the graph
	 */
	@Override
	public Set<Road> edgeSet() {
		
		return roads;
	}

	/**
	 * Create a set of the Towns in the graph
	 * 
	 * @return a set of the Towns in the graph
	 */
	@Override
	public Set<Town> vertexSet() {
		
		return towns;
	}

	
}
