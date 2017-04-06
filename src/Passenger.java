
/**
 * passenger G.U.I please refer to documentation for requirements 
 * 
 * @author mahipaul.tak, luke.renaud, kevin.widmann
 * 
 * 
 *
 */
public class Passenger {
	//TODO - JPanel outer layer?
	//TODO - other elements we may need access to in the gui
	
	public Passenger(){
		make_gui();
		connect_to_server();
	}
	
	public void connect_to_server(){
		//TODO - connect to a chosen port shared with the server
	}
	
	public Flight get_flight(Integer flightNumber){
		//TODO - request flight from server based on flight number
	}
	
	public boolean buy_ticket(Flight requestedFlight){
		//TODO - check if a ticket can be purchased, then alert the server that it has been purchased
	}
	
	private make_gui(){
		//TODO - code for creating the gui and initializing class variables
	}
}
