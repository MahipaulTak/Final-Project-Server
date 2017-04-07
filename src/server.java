import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

/**
 * @author mahipaul.tak, luke.renaud, kevin.widdeman
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
	/**
	 * Reader for input from client
	 */
	BufferedReader clientIn;
	/**
	 * Output from server to client
	 */
	PrintWriter serverOut;
	/**
	 * message received from client
	 */
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
	 */
	public server() throws IOException{
		serverSocket = new ServerSocket(9898,1);
		System.out.println("Flight Server is now running.");
		Client = serverSocket.accept();
		clientIn = new BufferedReader(new InputStreamReader(Client.getInputStream()));
		serverOut = new PrintWriter(Client.getOutputStream(), true);
//		iin = new ObjectInputStream(Client.getInputStream());
//		ouut = new ObjectOutputStream(Client.getOutputStream());
		cat = new FlightCatalogue();
		System.out.println("Server now connected to the client");
	}


	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{
		server ser = new server();
		System.out.println("haha");
		ser.run();
	}


	/**
	 * Funciton for the operation of the server
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 */
	public void run() throws IOException, ClassNotFoundException, SQLException{
		while(true){
			message = clientIn.readLine();


			// Command getFlight
			// sends flight object with flight number gieven by client

			if(message == "getFlight"){
				
				Integer ff = Integer.parseInt(clientIn.readLine());
				ouut = new ObjectOutputStream(Client.getOutputStream());
				ouut.writeObject(cat.find(ff));
				ouut.close();

				System.out.println("sent flight object");
			}

			// Command bookFlight
			// books Ticket sent to flight it should be attached to 
			// returns a string for if the flight was booked or not

			else if(message == "bookFlight"){
				iin = new ObjectInputStream(Client.getInputStream());
				Ticket t = (Ticket) iin.readObject();
				iin.close();
				String back = cat.find(t.getFl()).addTicket(t);
				serverOut.println(back);
				System.out.println("booked flight");

			}

			// Command allTickets
			// sends all Tickets booked for flight # sent afterwards sends a null

			else if(message == "allTickets"){
				Integer ff = Integer.parseInt(clientIn.readLine());
				ouut = new ObjectOutputStream(Client.getOutputStream());
				ouut.writeObject(cat.find(ff).Tickets);
				ouut.writeObject(null);
				ouut.close();
				System.out.println("sent the tickets");

			}

			// Command cancel
			// Receives ticket form server and removes the booking from the
			// connected flight, returns wether the ticket was actually removed 

			else if(message == "cancel"){
				iin = new ObjectInputStream(Client.getInputStream());
				Ticket t = (Ticket) iin.readObject();
				iin.close();
				String back = cat.find(t.getFl()).removeTicket(t);
				System.out.println("removed ticket");
				//				serverOut.println(back);
			}

			// Command addFlight
			// Receives Flight object and adds it to catalog of flights
			// returns flight number

			else if(message == "addFlight"){
				System.out.println("adding");
				ouut = new ObjectOutputStream(Client.getOutputStream());
				Flight f = (Flight) iin.readObject();
				ouut.close();
				Integer back = cat.addFlight(f);
				serverOut.println(back.toString());
				System.out.println("added flight");
			}

		}
	}



}
