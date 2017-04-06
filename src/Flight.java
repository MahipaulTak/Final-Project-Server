import java.util.ArrayList;

/**
 * @author mahipaul.tak, luke.renaud, kevin.widmann
 *
 * aggregated by flight catalog, refer to online resources for functions to implement
 * also aggregates tickets
 *
 */
public class Flight {
  private Integer FlightNumber, Duration, NumberOfSeats, RemainingSeats, Time;//Time is just an int of 24 hour time
  private String Source, Destination, Date;
  private ArrayList<Ticket> Tickets;
  private Double Price;
  
  /**
 * Default Constructor
 */
public Flight(){
	Source = Destination = Date = null;
    //something something about initializing
  }
  
  /**
 * @param duration
 * @param numberOfSeats
 * @param price
 * @param time
 * @param source
 * @param destination
 * @param date
 * @return FlightNumber // I think this is right.
 * 
 * fills in information of a flight and return the flight number to reference back to.
 * maybe should include a constructor and return an object of type Flight, can be changed later I guess.
 */
public Integer addFlight(Integer duration, Integer numberOfSeats, Double price, Integer time, String source, String destination, String date){
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
    //for(int i = 0; i<numberOfSeats; i++){
    //	Ticket temp = new Ticket(this, generateNewTicketID(), PasssengerInfo(), price); //Passenger Info needs default constructor
    //	Tickets.set(i, temp);
    //}
    
    //add flight to list of flights if there is one
    //FlightList.add(this);
    
    //set flight number to next available number:
    //FlightNumber = getNextFlightNumber();
    return FlightNumber;
    
  }

/**
 * @param duration
 * @param numberOfSeats
 * @param price
 * @param time
 * @param source
 * @param destination
 * @param date
 * @return rv
 * 
 * calls addFlight multiple times, needs to be passed in an array of flight information though, best way I can think of to add multiple flights
 * this function might be best in FlightCatalogue instead of flight
 */
public Integer[] addMultipleFlights(Integer[] duration, Integer[] numberOfSeats, Double[] price, Integer[] time, String[] source, String[] destination, String[] date){
	Integer[] rv = new Integer[duration.length];
	for(int i = 0; i<duration.length; i++){
		Flight temp = new Flight();
		rv[i] = temp.addFlight(duration[i], numberOfSeats[i], price[i], time[i], source[i], destination[i], date[i]);
		//FlightList.add(temp);
		
	}
    //fill list with x new tickets?
    //for(int i = 0; i<numberOfSeats; i++){
    //	Ticket temp = new Ticket(this, generateNewTicketID(), PasssengerInfo(), price); //Passenger Info needs default constructor
    //	Tickets.set(i, temp);
    //}
    
    //add flight to list of flights if there is one
    //FlightList.add(this);
    
    //set flight number to next available number:
    //FlightNumber = getNextFlightNumber();
    return rv;
    
}

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

}