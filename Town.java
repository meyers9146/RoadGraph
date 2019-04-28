import java.util.ArrayList;

/**
 * Represents a town as a node of a graph. Contains the name of the Town as well as its adjacent Towns,
 * plus accessor and mutator methods.
 * 
 * @author Mike Meyers
 * @version 1.0
 *
 */
public class Town implements Comparable<Town> {
	
	private String name;
	private ArrayList<Town> adjacentTowns;
	private Town lastTown;
	
	/**
	 * Create a new Town with just a name
	 * @param name the Town's name
	 */
	public Town(String name) {
		this.name = name;
	}
	
	/**
	 * Copy an existing Town object
	 * @param templateTown the Town to be copied
	 */
	public Town (Town templateTown) {
		//Deep copy the passed Town's name
		String name = new String (templateTown.getName());
		this.name = name;
		
		//Deep copy the passed Town's adjacent Towns list
		ArrayList<Town> templateTownAdjacents = templateTown.getAdjacentTowns();
		this.adjacentTowns = templateTownAdjacents;
	}
	
	/**
	 * Compare this Town to another town.
	 * @param o the Town to be compared
	 * @return 0 if the two Towns are equal, and another number otherwise
	 */
	@Override
	public int compareTo (Town o) {
		return (this.name.compareTo(o.getName()));
	}
	
	/**
	 * Generate a String representing this Town
	 * @return a String representing this Town
	 */
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Generate a hashCode for this Town
	 * @return a hashCode for this Town
	 */
	public int hashCode() {
		return name.hashCode();
	}
	
	/**
	 * See if this Town is equal to a passed Object
	 * @return true if this Town is equal to the passed Object, and false otherwise
	 */
	public boolean equals(Object obj) {
		//If passed Object is not a Town, return false
		if (!(obj instanceof Town)) return false;
		
		//If passed Object is a Town with a name matching this Town's name, return true
		else if (this.toString().equals((obj.toString()))) return true;
		
		//Otherwise, return false
		else return false;
	}
	
	/**
	 * Get this Town's name
	 * @return this town's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set this Town's name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get this Town's list of adjacent Towns
	 * @return this Town's list of adjacent Towns
	 */
	public ArrayList<Town> getAdjacentTowns() {
		return adjacentTowns;
	}

	/**
	 * Get the Town prior to this one in a spanning tree
	 * @return the reference to the Town prior to this one in a spanning tree
	 */
	public Town getLastTown() {
		return lastTown;
	}

	/**
	 * Set the Town prior to this one in a spanning tree
	 * @param lastTown the town previous to this one in a traversal
	 */
	public void setLastTown(Town lastTown) {
		this.lastTown = lastTown;
	}
	
	
	
	
}
