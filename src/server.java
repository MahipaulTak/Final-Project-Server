import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author mahipaul.tak, luke.renaud, kevin.widmann
 * 
 * Class for implementing connections between client gui and flight database
 *
 */
public class server {
	/**
	 * Socket of server
	 */
	ServerSocket serverSocket;
	/**
	 * Socket for interacting with client
	 */
	Socket Client;

	String message;
	/**
	 * output stream for serialized objects
	 */
	ObjectOutputStream ouut;
	/**
	 * input stream for serialized objects
	 */
	ObjectInputStream iin;
	/**
	 * Catalog for flights
	 */
	FlightCatalogue cat;
	/**
	 * Constructor for server, connects to port 9898
	 * 
	 * @throws IOException
	 * @throws SQLException 
	 */
	int ticketcount = 0;
	int flightcount = 0;
	
	public server() throws IOException, SQLException{
		serverSocket = new ServerSocket(9898,1);
		System.out.println("Flight Server is now running.");
		Client = serverSocket.accept();
		
		ouut = new ObjectOutputStream(Client.getOutputStream());		
		iin = new ObjectInputStream(Client.getInputStream());
		
		cat = new FlightCatalogue();
		Flight test = new Flight(500,500,500.00,500,"calgary","edmonton","12/12/2112");
		cat.addFlight(test);
		test.createTicket(ticketcount++);
		
		System.out.println("Server now connected to the client");
	}


	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{
		server ser = new server();
		System.out.println("haha");
		ser.run();
	}


	/**
	 * Function for the operation of the server
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 */
	public void run() throws IOException, ClassNotFoundException, SQLException{
		while(true){
			message = (String)iin.readObject();


			// Command allFlights
			// sends all flights to client

			if(message.equalsIgnoreCase("allFlights")){
				ArrayList<Flight> flights = cat.getCatalogue();
				
				for(int i = 0; i < flights.size(); i++){
					ouut.writeObject(flights.get(i));
					System.out.println("Sent a flight");
				}
				ouut.writeObject(null);
				
				System.out.println("Sent all flights finished");
			}

			// Command bookFlight
			// books Ticket sent to flight it should be attached to 
			// returns a string for if the flight was booked or not

			else if(message.equalsIgnoreCase("bookFlight")){
				Integer flightID = (Integer) iin.readObject();
				Flight toBeBooked = cat.find(flightID);
				
				if(toBeBooked.isFull()){
					ouut.writeObject(new Boolean(false));
					System.out.println("Plane is full, failed to book flight");
				}else{
					ouut.writeObject(new Boolean(true));
					ouut.writeObject(toBeBooked.createTicket(ticketcount++));
					System.out.println("Booked flight");
				}

			}

			// Command allTickets
			// sends all Tickets booked for flight # sent afterwards sends a null

			else if(message.equalsIgnoreCase("allTickets")){
				ArrayList<Flight> flights = cat.getCatalogue();
				
				for(int i = 0; i < flights.size(); i++){
					ArrayList<Ticket> tickets = flights.get(i).Tickets;
					for(int j = 0; j < tickets.size(); j++){
						ouut.writeObject(tickets.get(j));
						System.out.println("Printed a ticket");
					}
				}
				ouut.writeObject(null);
				
				System.out.println("Printed all tickets");
			}

			// Command cancel
			// Receives ticket form server and removes the booking from the
			// connected flight, returns whether the ticket was actually removed 

			else if(message.equalsIgnoreCase("cancel")){
				Ticket t = (Ticket) iin.readObject();
				
				cat.removeTicket(t);
				System.out.println("removed ticket");
				//				serverOut.println(back);
			}

			// Command addFlight
			// Receives Flight object and adds it to catalog of flights
			// returns flight number

			else if(message.equalsIgnoreCase("addFlight")){
				System.out.println("Adding flight");

				Flight in = (Flight) iin.readObject();
				in.setFlightNumber(flightcount++);
				
				cat.addFlight(in);
				
				System.out.println("Added flight");
			}

		}
	}



}
