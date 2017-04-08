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
		
		try {
			ouut.writeObject(new String("allFlights"));
		} catch (IOException e1) {
			System.out.println("unable to communicate to server in searchFlights");
			e1.printStackTrace();
		}
		
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
		
		try {
			ouut.writeObject(new String("bookFlight"));
			ouut.writeObject(new Integer(requestedFlight.FlightNumber));
		} catch (IOException e) {
			System.out.println("Error writing to socket in book flight");
		}
		try{
			boolean rv = (boolean) iin.readObject();
			if(!rv){
				System.out.println("Plane full");
				return false;
			}
			Ticket toPrint = (Ticket) iin.readObject();
			printTicket(toPrint);
			
		} catch (IOException e){
			System.out.println("Error reading from socket in book flight");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found in book flight");
		}
			return true;
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
