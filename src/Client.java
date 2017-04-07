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
private PrintWriter socketOut;
private Socket inSocket;
private BufferedReader BRsocket;

public Client(String serverName, int portNumber) {
	try {
		inSocket = new Socket(serverName, portNumber);
		BRsocket = new BufferedReader(new InputStreamReader(
				inSocket.getInputStream()));
		socketOut = new PrintWriter((inSocket.getOutputStream()), true);
	} catch (IOException e) {
		System.err.println(e.getStackTrace());
		System.err.println("Bad connection to server or something?");
	}
}

	/**
	 * 
	 * @param flightNumber
	 * @return Flight
	 */
	public Flight get_flight(Integer flightNumber){
		try {
		socketOut.println("getFlight");
		socketOut.println(flightNumber);
		//ObjectInputStream InputStream = new ObjectInputStream(inSocket.getInputStream());
		//Flight temp = (Flight) InputStream.readObject();
		
		String in = BRsocket.readLine();
		if(in.equalsIgnoreCase(""))
			return null;
		
		String[] nf = in.split("-");
		Flight f = new Flight(Integer.parseInt(nf[0]), Integer.parseInt(nf[1]), Double.parseDouble(nf[2]), Integer.parseInt(nf[3]), nf[4], nf[5], nf[6]);
		//InputStream.close();
		return f;
		//} catch (ClassNotFoundException e) {
		//	System.err.println("Class not found");
		//	e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO problems in get_flight");
			e.printStackTrace();
		}
		return null;//if it can't return a normal flight/has problems

	}
	
	
	public void add_flight(Integer duration, Integer numberOfSeats, Double price, Integer time, String source, String destination, String date){
		Flight new_flight = new Flight(duration, numberOfSeats, price, time, source, destination, date);
		//try {
			socketOut.println("addFlight");
			socketOut.println(duration+"-"+numberOfSeats+"-"+price+"-"+time+"-"+source+"-"+destination+"-"+date);
			//ObjectOutputStream stream = new ObjectOutputStream(inSocket.getOutputStream());
			//stream.writeObject(new_flight);
			//stream.close();
		//} catch (IOException e) {
			//System.err.println("IO problems in add_flight");
			//e.printStackTrace();
		//}

	}
	/**
	 * Display all flights to user
	 */
	public ArrayList<Flight> searchFlights(){
		ArrayList<Flight> Flights = new ArrayList<Flight>();
		int i = 0;
		Flight temp = get_flight(i);
		while(temp!=null){
			Flights.add(temp);
			i++;
			temp = get_flight(i);
		}
		
		return Flights;
		
	}
	
	public boolean bookFlight(Flight requestedFlight){
		PasssengerInfo info = new PasssengerInfo("First", "Last", "BDay");
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
		else
			return false;
	}
	
	public void cancelTicket(Ticket new_ticket){
		try {
			socketOut.println("cancel");
			ObjectOutputStream stream = new ObjectOutputStream(inSocket.getOutputStream());
			stream.writeObject(new_ticket);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("IO problems in cancelTicket");
			
		}
	}
	
	public ArrayList<Ticket> allTickets(){
		ArrayList<Ticket> rv = null;
		try {
			socketOut.println("allTickets");
			ObjectInputStream InputStream = new ObjectInputStream(inSocket.getInputStream());
			rv = (ArrayList<Ticket>) InputStream.readObject();
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
