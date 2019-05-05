
/**
 * Represents a road as an edge in a graph. Contains the end vertices of the road, its weight,
 * and accessor/mutator methods.
 * @author Mike Meyers
 * @version 1.0
 *
 */
public class Road implements Comparable<Road>{

	private String name;
	private Town source, destination;
	private int weight;
	
	/**
	 * Create a weighted Road
	 * @param source this Road's source
	 * @param destination this Road's destination
	 * @param distance this Road's length
	 * @param name this Road's name
	 */
	public Road(Town source, Town destination, int distance, String name) {
		this.source = source;
		this.destination = destination;
		weight = distance;
		this.name = name;
	}
	
	/**
	 * Create an unweighted Road.
	 * @param source this Road's source
	 * @param destination this Road's destination 
	 * @param name this Road's name
	 */
	public Road(Town source, Town destination, String name) {
		this.source = source;
		this.destination = destination;
		this.name = name;
		weight = 1;
	}
	
	/**
	 * Check to see if this road connects to an indicated Town
	 * @param town the Town to be checked
	 * @return true if this Road contains the Town, and false otherwise
	 */
	public boolean contains(Town town) {
		if (source.equals(town)) return true;
		else if (destination.equals(town)) return true;
		else return false;
	}
	
	/**
	 * Generate a String representing this Road
	 * @return a String representing this Road
	 */
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Compare this Road to another Road
	 * @return 0 if the two road names are equal, and a positive or negative number otherwise
	 */
	public int compareTo(Road r) {
		
		return this.name.compareTo(r.getName());
	}
	
	/**
	 * Get this Road's name
	 * @return this Road's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set this Road's name
	 * @param name the intended name for the Road
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get this Road's source
	 * @return this Road's source Town
	 */
	public Town getSource() {
		return source;
	}
	
	/**
	 * Set this Road's source
	 * @param source the intended source Town for this Road
	 */
	public void setSource(Town source) {
		this.source = source;
	}
	
	/**
	 * Get this Road's destination
	 * @return this Road's destination
	 */
	public Town getDestination() {
		return destination;
	}
	
	/**
	 * Set this Road's destination
	 * @param destination the intended Destination for this Road
	 */
	public void setDestination(Town destination) {
		this.destination = destination;
	}
	
	/**
	 * Get this Road's weight
	 * @return this Road's weight if it exists (and 0 if Road is unweighted)
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Set this Road's weight
	 * @param weight the intended weight for this Road
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**
	 * Compare this Road to another Road. Since the Road is not directional, the two Roads
	 * are equal so long as this road's source and destination matches the argument Road's
	 * source and destination.
	 */
	public boolean equals(Object r) {
		//if argument is not a Road, return false
		if (!(r instanceof Road)) return false;
		
		//If the passed Road's source and destination are equal, but this
		//Road's source and destination are NOT equal, return false
		if (((Road)r).getSource().equals(((Road)r).getDestination())) {
			if (!source.equals(destination)) return false;
		}
		
		//Otherwise, if the passed Road's source matches either this Road's source or destination,
		//and the passed Road's destination ALSO matches either this Road's source or destination,
		//then the two Roads are equal and the method returns true
		if (source.equals(((Road)r).getSource())  || source.equals(((Road)r).getDestination())) {
			if (destination.equals(((Road)r).getSource()) || destination.equals(((Road)r).getDestination())) {
				return true;
			}
			else return false;
		}
		
		//Otherwise, return false
		else return false;
	}
	
}
