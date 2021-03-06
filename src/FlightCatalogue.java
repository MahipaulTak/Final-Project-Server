import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author mahipaul.tak, luke.renaud, kevin.widmann
 * 
 * Catalog for keeping track of flights and their information, aggregates flights and connects to database driver, accesible by the server
 *
 */
public class FlightCatalogue {

	/**
	 * Catalogue of all current flights, should be kept up to date with database
	 */
	private ArrayList<Flight> catalogue;

	private Integer fnumb;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	public ArrayList<Flight> getCatalogue() {
		
		return catalogue;
	}

	public Integer getSize(){
		return catalogue.size();
	}

	public FlightCatalogue() throws SQLException {
		fnumb = 0;
		this.catalogue = new ArrayList<Flight>();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBASE","root","password");

		stmt = conn.createStatement();
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
	public Integer addFlight(Flight k) throws SQLException{
		
		k.FlightNumber = fnumb;
		fnumb++;
		catalogue.add(k);
		
		
		String sql = "INSERT INTO flightinfo " + "VALUES ("+ k.FlightNumber +", '"+ k.Duration+"', '"+k.NumberOfSeats+"', '"+k.RemainingSeats+"', '"+k.Time+"', '"+k.Price+"', '"+k.Source+"', '"+k.Destination+"', '"+k.Date+"')";                                 
		stmt.execute(sql);
		return (fnumb -1);
	}
	
	public void removeTicket(Ticket t){
		boolean flag = false;
		
		for(int i = 0; i < catalogue.size() && !flag; i++){
			Flight temp = catalogue.get(i);
			ArrayList<Ticket> tickets = temp.Tickets;
			for(int j = 0; j < tickets.size(); j++){
				if(tickets.get(j).getID().equals(t.getID())){
					temp.Tickets.remove(j);
					flag = true;
					
					break;
				}
			}
		}
	}

}
