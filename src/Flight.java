import java.util.ArrayList;

/**
 * @author mahipaul.tak, luke.renaud, kevin.widmann
 *
 * aggregated by flight catalogue, refer to online resources for functions to implement
 * also aggregates tickets
 *
 */

//Probably need some libraries
public class Flight {
	public Integer FlightNumber, Duration, NumberOfSeats, RemainingSeats, Price, Time;//Time is just an int of 24 hour time
	public String Source, Destination, Date;
	public ArrayList<Ticket> Tickets;//Check Luke's code on this


	public Flight(){//default constructor
		Source = null;
		Destination =null;
		Date = null;
		Time = null;
		//something something about initializing
	}

	public Integer addFlight(Integer duration, Integer numberOfSeats, Integer price, Integer time, String source, String destination, String date){

		RemainingSeats = 0;
		Duration = duration;
		NumberOfSeats =  numberOfSeats;
		Price = price;
		Time = time;
		Source = source;
		Destination = destination;
		Date = date;

		//add flight to list of flights if there is one
		//FlightList.add(this);

		//set flight number to next available number:
		//flightNumber = getNextFlightNumber();
		return FlightNumber;

	}


}
