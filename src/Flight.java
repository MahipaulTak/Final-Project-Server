import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
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
	public ArrayList<Ticket> Tickets = new ArrayList<Ticket>();//Check Luke's code on this
	public Integer tick;


	public Flight(Integer duration, Integer numberOfSeats, Double price, Integer time, String source, String destination, String date){
		RemainingSeats = numberOfSeats;
		Duration = duration;
		NumberOfSeats =  numberOfSeats;
		Price = price;
		Time = time;
		Source = source;
		Destination = destination;
		Date = date;
		ArrayList<Ticket> Tickets = new ArrayList<Ticket>();
		Integer FlightNumber = -1;
		tick = 0;
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
	private String addTicket(Ticket t){
		if(RemainingSeats == 0){
			return "false";
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date current = new Date();
		
		char[] temp = new char[4];
		temp[0] = Date.toCharArray()[6];
		temp[1] = Date.toCharArray()[7];
		temp[2] = Date.toCharArray()[8];
		temp[3] = Date.toCharArray()[9];
		Integer year = Integer.parseInt(new String(temp));
		temp = new char[2];
		temp[0] = Date.toCharArray()[0];
		temp[1] = Date.toCharArray()[1];
		Integer day = Integer.parseInt(new String(temp));
		temp = new char[2];
		temp[0] = Date.toCharArray()[3];
		temp[1] = Date.toCharArray()[4];
		Integer month = Integer.parseInt(new String(temp));
		
		if(Calendar.YEAR > year){
			if(Calendar.MONTH > month){
				if(Calendar.DATE > day ){
					return "false";
				}
			}
		}
		
		Tickets.add(t);
		RemainingSeats--;
		
		return "true";
	}

	
	public boolean isFull(){
		return (RemainingSeats == 0);
	}
	
	public Ticket createTicket(Integer ticketNumber){
		Ticket rv = new Ticket(this, ticketNumber, null, this.Price);
		this.addTicket(rv);
		
		return rv;
	}

	public void setFlightNumber(int newNumber){
		FlightNumber = newNumber;
	}
	
	public String toString(){
		return ("Flight" + FlightNumber.toString() + "\n" +
				Source + " -> " + Destination + "\n" +
				"$" + Double.toString(Price) + "\n" +
				Date + " at " + String.format("%02d", Time / 100) + ":" + String.format("%02d", Time % 100) + "\n" +
				"Seats Remaining: " + RemainingSeats.toString());
	}
}

