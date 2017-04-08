import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;


/**
 * @author mahipaul.tak, luke.renaud, kevin.widmann
 *
 * Class for connecting to server, should be able to utilize passenger and admin G.U.I and provide a way for the
 * two G.U.Is to be selected.
 *
 */
public class Client {

private final String ticketDirectory = "./";

private Socket inSocket;
private ObjectOutputStream ouut;		
private ObjectInputStream iin;



public Client(String serverName, int portNumber) {
	try {
		inSocket = new Socket(serverName, portNumber);
		ouut = new ObjectOutputStream(inSocket.getOutputStream());		
		iin = new ObjectInputStream(inSocket.getInputStream());
	} catch (IOException e) {
		System.err.println(e.getStackTrace());
		System.err.println("Bad connection to server or something?");
	}
}

	
	public void add_flight(Integer duration, Integer numberOfSeats, Double price, Integer time, String source, String destination, String date){
		Flight new_flight = new Flight(duration, numberOfSeats, price, time, source, destination, date);
		
		try {
			ouut.writeObject(new String("addFlight"));
			ouut.writeObject(new_flight);
			System.out.println("Added flight");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Display all flights to user
	 */
	public ArrayList<Flight> searchFlights(){
		ArrayList<Flight> Flights = new ArrayList<Flight>();
		
		try{
			Flight temp = (Flight)iin.readObject();
			while(temp != null){
				Flights.add(temp);
				temp = (Flight)iin.readObject();
			}
		}catch(ClassNotFoundException e){
			System.out.println("Couldn't get class Flight");
		} catch (IOException e) {
			System.out.println("Error reading from socket");
		}
		
		return Flights;
	}
	
	public boolean bookFlight(Flight requestedFlight){
/*		PasssengerInfo info = new PasssengerInfo("First", "Last", "BDay");
		String rv = null;
		try {
			socketOut.println("bookFlight");
			//ObjectOutputStream stream = new ObjectOutputStream(inSocket.getOutputStream());
			socketOut.println(requestedFlight.FlightNumber+"-"+info.getFName()+"-"+info.getLName()+"-"+info.getBDate()+"-"+requestedFlight.Price);
			//TODO GUI TO GET INFO
			//Ticket new_ticket = new Ticket(requestedFlight, requestedFlight.FlightNumber, info, requestedFlight.Price);
			//stream.writeObject(new_ticket);
			rv = BRsocket.readLine();
			//stream.close();
		} catch (IOException e) {
			System.err.println("IO problems in bookFlight");
			e.printStackTrace();
		}
		
		if(rv.equalsIgnoreCase("true"))
			return true;
		else*/
			return false;
	}
	
	public void cancelTicket(Ticket ticketToCancel){
		try {
			ouut.writeObject(new String("cancel"));
			ouut.writeObject(ticketToCancel);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("IO problems in cancelTicket");
		}
	}
	
	public ArrayList<Ticket> allTickets(){
		ArrayList<Ticket> rv = new ArrayList<Ticket>();
		try {
			ouut.writeObject(new String("allTickets"));
			
			Ticket temp = (Ticket)iin.readObject();
			while(temp != null){
				rv.add(temp);
				temp = (Ticket)iin.readObject();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("IO problems in allTickets");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Classnotfound in allTickets");
		}
		
		return rv;
	}
	
	
	public void printTicket(Ticket toPrint) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter output = new PrintWriter(ticketDirectory+"ticket", "UTF-8");
		output.println(toPrint.toString());
		output.close();
	}

}
