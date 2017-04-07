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
private BufferedReader stdIn;
private BufferedReader BRsocket;

public Client(String serverName, int portNumber) {
	try {
		inSocket = new Socket(serverName, portNumber);
		stdIn = new BufferedReader(new InputStreamReader(System.in));
		BRsocket = new BufferedReader(new InputStreamReader(
				inSocket.getInputStream()));
		socketOut = new PrintWriter((inSocket.getOutputStream()), true);
	} catch (IOException e) {
		System.err.println(e.getStackTrace());
		System.err.println("Bad connection to server or something?");
	}
}

public void communicate()  {//This is just copy pasta, may need fixing

	String response = "";
	boolean running = true;
	while (running) {
		try {
			System.out.println("Fetching something?");
				response = BRsocket.readLine();
				System.out.println(response);	

		} catch (IOException e) {
			System.out.println("Sending error: " + e.getMessage());
		}
	}
	try {
		stdIn.close();
		BRsocket.close();
		socketOut.close();
	} catch (IOException e) {
		System.out.println("Closing error: " + e.getMessage());
	}
}

public static void main(String[] args) throws IOException  {
	Client aClient = new Client("localhost", 9898);
	//TODO Do something here
}
	
	public void connect_to_server(){
		//TODO - connect to a chosen port shared with the server		
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
		ObjectInputStream InputStream = new ObjectInputStream(inSocket.getInputStream());
		Flight temp = (Flight) InputStream.readObject();
		
		return temp;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;//if it can't return a normal flight/has problems

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
		socketOut.write(requestedFlight.FlightNumber);
		Integer iD, price;
		iD = price = 0;
		PasssengerInfo info = null;//need to generate new ticket ID for flight somehow
		String rv = null;
		try {
			socketOut.println("bookFlight");
			ObjectOutputStream stream = new ObjectOutputStream(inSocket.getOutputStream());
			//GUI TO GET INFO
			Ticket new_ticket = new Ticket(requestedFlight, iD, info, price);
			stream.writeObject(new_ticket);
			rv = BRsocket.readLine();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(rv == "true")
			return true;
		else
			return false;
	}
	
	public void printTicket(Ticket toPrint) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter output = new PrintWriter(ticketDirectory+"ticket", "UTF-8");
		output.println(toPrint.toString());
		output.close();
	}

}
