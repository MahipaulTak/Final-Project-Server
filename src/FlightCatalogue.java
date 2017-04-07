import java.sql.SQLException;
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
	/**
	 * class for executing commands to databse
	 */
//	private dbDriver dbdrive;

	public ArrayList<Flight> getCatalogue() {
		return catalogue;
	}

	public Integer getSize(){
		return catalogue.size();
	}

	public FlightCatalogue() {

		this.catalogue = new ArrayList<Flight>();
	}
	
	public Flight find(Flight f){
		Flight result = null;
		for(int i = 0; i < catalogue.size(); i++){
			if(f.FlightNumber == catalogue.get(i).FlightNumber){
				result = catalogue.get(i);
				break;
			}
		}
		return result;
	}
	
	public Flight find(Integer f){
		Flight result = null;
		for(int i = 0; i < catalogue.size(); i++){
			if(f == catalogue.get(i).FlightNumber){
				result = catalogue.get(i);
				break;
			}
		}
		return result;
	}
	
	/**
	 * Adds k to the catalogue
	 * 
	 * @param k
	 * @throws SQLException 
	 */
	public void addFlight(Flight k) throws SQLException{
		catalogue.add(k);
		
//		String sql = "INSERT INTO clientinfo " + "VALUES ("+ k.FlightNumber +", '"+ k.Duration+"', '"+k.NumberOfSeats+"', '"+k.RemainingSeats+"', '"+k.Time+"', '"+k.Price+"', '"+k.Source+"', '"+k.Destination+"', '"+k.Date+"')";                                 
//		dbdrive.getStmt().execute(sql);
	}




}
