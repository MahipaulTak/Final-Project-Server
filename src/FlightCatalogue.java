import java.util.ArrayList;

/**
 * @author mahipaul.tak, luke.renaud, kevin.widemann
 * 
 * Catalog for keeping track of flights and their information, aggregates flights and connects to database driver, accesible by the server
 *
 */
public class FlightCatalogue {

	/**
	 * Catalogue of all current flights, should be kept up to date with database
	 */
	private ArrayList<Flight> catalogue;


	public FlightCatalogue() {

		this.catalogue = new ArrayList<Flight>();
	}
	
	
	
	/**
	 * Adds k to the catalogue
	 * 
	 * @param k
	 */
	public void addFlight(Flight k){
		catalogue.add(k);
	}




}
