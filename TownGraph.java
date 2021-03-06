import java.util.*;

/**
 * A class for creating a graph of towns and roads, including mileage for the road lengths
 * @author Mike Meyers
 * @version 1.0
 *
 */
public class TownGraph implements GraphInterface<Town, Road> {

	ArrayList<Town> towns;
	ArrayList<Road> roads;
	
	Town[] townArray;
	int[][] edgeArray;
	
	//Distance[i] will hold the shortest distance to townArray[i] 
	//after calling Dijkstra's Algorithm
	int[] distance;
	
	/**
	 * Default constructor. Creates an empty graph with no content
	 */
	public TownGraph() {
		towns = new ArrayList<>();
		roads = new ArrayList<>();
	}
	
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
			if (road.equals(new Road(sourceVertex, destinationVertex, ""))) return road;
		}
		return null;
	}

	/**
	 * Add a new Road to the graph. Note: this does not permit adding of negative weights
	 * 
	 * @param sourceVertex the source Town for the Road
	 * @param destinationVertex the destination Town for the Road
	 * @param weight the length of the Road
	 * @param description the name of the Road
	 * 
	 * @return the added Road
	 * 
	 * @throws NullPointerException if passed a null Town
	 * @throws IllegalArgumentException if passed a Town that does not exist in the graph,
	 * 									or if the source and destnation are the same
	 */
	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) 
			throws NullPointerException, IllegalArgumentException{
				
		//If either argument Town is null, throw an exception
		if (sourceVertex == null || destinationVertex == null) {
			throw new NullPointerException();
		}
		
		//If the source and destination are the same, throw an exception
		else if (sourceVertex.equals(destinationVertex)) {
			throw new IllegalArgumentException("Source and Destination may not be the same");
		}
		
		//If the graph does not contain the argument Towns, throw an exception
		else if (!(this.containsVertex(sourceVertex) && this.containsVertex(destinationVertex))) {
			throw new IllegalArgumentException("The indicated Towns are not in the graph.");
		}
		
		//If the road already exists, return null
		if (this.containsEdge(sourceVertex, destinationVertex)) throw new IllegalArgumentException(
				"The indicated road " + description + " already exists.");
		
		//Otherwise, create a new Road object from the argument information
		Road toAdd = new Road(sourceVertex, destinationVertex, Math.abs(weight), description);
		
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
	 * Retieve a particular Town from this graph
	 * @param v the Town to be searched for
	 * @return the Town in this graph once found, or null if not present
	 */
	public Town getVertex(Town v) {
		
		for (Town town : towns) {
			if (town.equals(v)) return town;
		}
		
		//If no match is found, return null
		return null;
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
		if (towns.size() > 0) {
			for (Town town : towns) {
				if (town.equals(v)) return true;
			}
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
		
		if (!this.containsVertex(vertex)) return null;
		
		//Iterate through the Roads set.
		//Any Road found that contains the town is an adjacent Road,
		//And is added to the return Set
		for (Road road : roads) {
			if (road.contains(vertex)) edgeSet.add(road);
		}
		
		return edgeSet;
	}

	/**
	 * Remove a Road, as specified by the Road's parameters. Will not remove edges of weight
	 * equal to or less than 1, per the interface
	 * 
	 * @param sourceVertex the source Town for the Road
	 * @param destinationVertex the destination Town for the Road
	 * @param weight the weight for the Road
	 * @param description the name of the Road
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
		
		//If the indicated weight is 1, or if the description is null, return null
		if (weight <= 1 || description == null) return null;
		
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
	 * @param description the name of the Road
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
		if (weight <= 1) {
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
		if (!this.containsVertex(v)) return false;
		
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

	/**
	 * Find the shortest path from a source to a destination
	 * 
	 * @param sourceVertex the starting Town
	 * @param destinationVertex the destination Town
	 * 
	 * @return an ArrayList of strings describing the path from source to destination
	 */
	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex){		
		
		//Call DSP algorithm to generate the tree
		this.dijkstraShortestPath(sourceVertex);
		
		//Start from destination vertex and work backward to build the path
		ArrayList<Town> backtrace = new ArrayList<>();
		
		//Replace the argument destinationVertex with the actual one from the graph
		//and add the destination to the graph
		Town dV = getVertex(destinationVertex);
		backtrace.add(dV);
		
		//Retrieve the graph's destination vertex matching the argument destination vertex
		Town previous = dV.getLastTown();
		
		while (previous != null) {
			backtrace.add(previous);
			previous = previous.getLastTown();
		}
		
		//Create a path and add message strings
		ArrayList<String> path = new ArrayList<>();
		for (int i = 0; i < backtrace.size() - 1; i++) {
			
			//For each vertex, add a String with the following format:
			// "[Source Town name] via [Road name] to [Destination Town name] [weight]"
			path.add(backtrace.get(i).getLastTown().toString() + " via " + getEdge(backtrace.get(i), backtrace.get(i).getLastTown())
					+ " to " + backtrace.get(i).toString() + " " 
					+ getEdge(backtrace.get(i), backtrace.get(i).getLastTown()).getWeight());

		}
		
		//Reverse path, since backtrace searched Towns by reverse order
		Collections.reverse(path);
		
		//When the source is reached, return the completed path
		return path;
	}

	/**
	 * Use Dijkstra's Shortest Path algorithm to determine the shortest paths from a given source Town to any other Town in the graph
	 * @param sourceVertex the starting Town for the algorithm
	 */
	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		//Start by generating a new adjacency matrix
		generateMatrix();
		
		//Generate lists to hold added and unadded Towns
		ArrayList<Town> added = new ArrayList<>();
		ArrayList<Town> unadded = new ArrayList<>(towns);
		distance = new int[towns.size()];

		for (int i = 0; i < towns.size(); i++) {
			distance[i] = Integer.MAX_VALUE; //Instantiate each element with maximum value to mark unvisited vertices
		}
		
		//Create counter for path length
		for (Town town : towns) {
			town.setLastTown(null);
		}
		
		//Find sourceVertex's index in the adjacency matrix
		int sourceIndex = -1;
		for (int i = 0; i < towns.size(); i++) {
			if (sourceVertex.equals(towns.get(i))) {
				sourceIndex = i;
			}
		}
			
		//Set starting index's distance to zero in the distance array
		distance[sourceIndex] = 0;
		
		for (int i = 0; i < townArray.length-1; i++) {
			
			//Find shortest edge
			int minIndex = getNextMin(distance, unadded);
			added.add(townArray[minIndex]);
			unadded.remove(townArray[minIndex]);
			
			//Update distance array to reflect the added edge
			//If the distance array's value is still equal to MAX_VALUE, it will be updated
			//Otherwise, if this vertex's distance is less than the distance to the existing edge from source,
			//it will be updated
			// (note: this checks for existing edges before updating, so edges of 0 will not be overwritten)
			for (int j = 0; j < townArray.length; j++) {
				if (unadded.contains(townArray[j]) && edgeArray[minIndex][j] != Integer.MAX_VALUE) {
					if (edgeArray[minIndex][j] != 0) {
						if (distance[minIndex] + edgeArray[minIndex][j] < distance[j]) {
							distance[j] = distance[minIndex] + edgeArray[minIndex][j];
							
							//Set the Town's predecessor
							townArray[j].setLastTown(townArray[minIndex]);
						}
					}
				}
			}
			
		}//end while
		
	}//end dijkstraShortestPath
	
	
	/**
	 * Get the next minimum edge from the adjacency matrix.
	 *
	 * @param dist the distance array from the graph
	 * @param unadded the list of Towns not yet added to the MST
	 * 
	 * @return the integer of the index of the next closest Town
	 */
	private int getNextMin(int[] dist, ArrayList<Town> unadded) {
		if (unadded.isEmpty()) return -1; //Will throw NoSuchElementException if passed an empty unadded set
		
		int minDistance = Integer.MAX_VALUE;
		int minIndex = -1;
		
		//Iterate through the sourceIndex's adjacent edges
		//Find the shortest edge to a vertex that isn't in the adjacency matrix
		for (int i = 0; i < dist.length; i++) {
			if (dist[i] < minDistance && unadded.contains(townArray[i])) {
					minDistance = dist[i];
					minIndex = i;
			}
		}
		
		//Return the index of the edge that was located
		return minIndex;
		
	}

	/**
	 * Create a set of the Roads in the graph.
	 * 
	 * @return a set of the Roads in the graph
	 */
	@Override
	public Set<Road> edgeSet() {
		
		//HashSet has a constructor that accepts any Collection object
		return new HashSet<Road>(roads);
	}

	/**
	 * Create a set of the Towns in the graph
	 * 
	 * @return a set of the Towns in the graph
	 */
	@Override
	public Set<Town> vertexSet() {
		
		//HashSet has a constructor that accepts any Collection object
		return new HashSet<Town>(towns);
	}
	
	/**
	 * Generate an adjacency matrix of the current Towns and Edges to allow traversal of the graph.
	 * The matrix consists of an X by X grid of integers, where X is the number of Towns in the graph.
	 * A value of 0 in the graph indicates no edge, and any other value indicates an edge existing.
	 * An unweighted graph will populate with 1's, and a weighted graph will populate with the weight
	 * of the edges.
	 */
	private void generateMatrix() {
		//Create an array of the Town objects and corresponding 2D array for the edges
		townArray = towns.toArray(new Town[towns.size()]);
		edgeArray = new int[townArray.length][townArray.length];
		
		for (Road road : roads) {
			//Search the townArray to find the source vertex
			int i = -1;
			boolean found = false;
			while (!found && i < townArray.length) {
				i++;
				if (road.getSource().equals(townArray[i])) {
					found = true;
				}
			}
			
			//Once source is found, search the townArray again to find the destination vertex
			int j = -1;
			found = false;
			while (!found && i < townArray.length) {
				j++;
				if (road.getDestination().equals(townArray[j])) {
					found = true;
				}
			}
			
			//Once both source and destination are found, add the edge to the adjacency matrix
			edgeArray[i][j] = road.getWeight();
			edgeArray[j][i] = road.getWeight();
		}
		return;
	}
	
	/**
	 * After running Djikstra's algorithm, find the distance to a given destination
	 * from the destination table 
	 * @param destination the destination Town
	 * @return the distance from the source to the destination
	 */
	public int getDistance(Town destination) {
		//find Town index in townArray
		for (int i = 0; i < towns.size(); i++) {
			if (towns.get(i).equals(destination)){
				//When a match is found, return its distance from within the distance array
				return distance[i];
			}
		}
		
		//If no match is found, return -1;
		return -1;
	}
	

}
