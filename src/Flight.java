import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author mahipaul.tak, luke.renaud, kevin.widmann
 *
 * aggregated by flight catalog, refer to online resources for functions to implement
 * also aggregates tickets
 *
 */

//Probably need some libraries
public class Flight implements Serializable{
	public Integer FlightNumber, Duration, NumberOfSeats, RemainingSeats, Time;//Time is just an int of 24 hour time
	double Price;
	public String Source, Destination, Date;
	public ArrayList<Ticket> Tickets;//Check Luke's code on this


	public Flight(Integer duration, Integer numberOfSeats, Double price, Integer time, String source, String destination, String date){
		RemainingSeats = 0;
		Duration = duration;
		NumberOfSeats =  numberOfSeats;
		Price = price;
		Time = time;
		Source = source;
		Destination = destination;
		Date = date;
		ArrayList<Ticket> Tickets = new ArrayList<Ticket>();
		Integer FlightNumber = -1;
	}

//May not need? but leave around for when it goes into client
/*	public Integer addFlight(Integer duration, Integer numberOfSeats, Double price, Integer time, String source, String destination, String date){
		RemainingSeats = 0;
		Duration = duration;
		NumberOfSeats =  numberOfSeats;
		Price = price*1.07;// TAXATION IS THEFT
		Time = time;
		Source = source;
		Destination = destination;
		Date = date;
		Tickets = new ArrayList<Ticket>(numberOfSeats);
		//fill list with x new tickets?
		for(int i = 0; i<numberOfSeats; i++){
			Ticket temp = new Ticket(this, generateNewTicketID(), , price); //Passenger Info needs default constructor
			Tickets.set(i, temp);
		}

		//add flight to list of flights if there is one
		//FlightList.add(this);

		//set flight number to next available number:
		//FlightNumber = getNextFlightNumber();
		return FlightNumber;

	}*/

	public void editInfo(){
		//call GUI to update info? Or maybe just remove this 
	}

	/**
	 * @return String
	 * 
	 * return the important data of a flight in a long string delimited by... '-'? I guess
	 * make sure you know what order these come in. Unless we want to kill this function and just give public access to the flight info.
	 */
	public String showInfo(){
		return Integer.toString(FlightNumber) + "-" + Integer.toString(Duration) + "-" + Integer.toString(NumberOfSeats) + "-" + Integer.toString(FlightNumber) + "-" + Integer.toString(Time)+ 
				"-" + Double.toString(Price)  + "-" + Source  + "-" + Destination  + "-" + Date;
	}
	
	
	/**
	 * Function for adding a booking to a flight
	 * 
	 * @param t
	 * @return
	 */
	public String addTicket(Ticket t){
		
		
		if(RemainingSeats == 0){
			return "false";
		}
		
		Tickets.add(t);
		RemainingSeats--;
		
		return "true";
	}
	
	/**
	 * 
	 * removes ticket from list of bookings
	 * @param t
	 * @return
	 */
	public String removeTicket(Ticket t){
		boolean result = Tickets.remove(t);
		
		if(result){
			RemainingSeats++;
			return "true";
			
		}
		else
			return"false";
	}
	
	

}

