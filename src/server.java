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
	 * @throws SQLException 
	 */
	public server() throws IOException, SQLException{
		serverSocket = new ServerSocket(9898,1);
		System.out.println("Flight Server is now running.");
		Client = serverSocket.accept();
		clientIn = new BufferedReader(new InputStreamReader(Client.getInputStream()));
		serverOut = new PrintWriter(Client.getOutputStream(), true);
//		iin = new ObjectInputStream(Client.getInputStream());
//		ouut = new ObjectOutputStream(Client.getOutputStream());
		cat = new FlightCatalogue();
		Flight nekw = new Flight(500,500,500.00,500,"calgary","edmonton","12/12/1212");
		cat.addFlight(nekw);
		cat.find(nekw).addTicket(new Ticket(nekw, 0, new PasssengerInfo("lol", "lol","1/1/0200"),30.00));
		System.out.println("Server now connected to the client");
	}


	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{
		server ser = new server();
		//System.out.println("haha");
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

			if(message.equalsIgnoreCase("getFlight")){
				
				Integer ff = Integer.parseInt(clientIn.readLine());
				if(cat.find(ff) != null){
				serverOut.println(cat.find(ff).Duration+"-"+cat.find(ff).NumberOfSeats+"-"+cat.find(ff).Price+"-"+cat.find(ff).Time+"-"+cat.find(ff).Source+"-"+cat.find(ff).Destination+"-"+cat.find(ff).Date);}
				else{
					serverOut.println(new String());
				}
			

				System.out.println("sent flight object");
			}

			// Command bookFlight
			// books Ticket sent to flight it should be attached to 
			// returns a string for if the flight was booked or not

			else if(message.equalsIgnoreCase("bookFlight")){
				String in = clientIn.readLine();
				String[] nf = in.split("-");
				Flight temp = cat.find(Integer.parseInt(nf[0]));
				Ticket t = new Ticket(temp,temp.tick, new PasssengerInfo(nf[1],nf[2],nf[3]),Double.parseDouble(nf[4]));
				String back = cat.find(t.getFl()).addTicket(t);
				serverOut.println(back);
				System.out.println("booked flight");

			}

			// Command allTickets
			// sends all Tickets booked for flight # sent afterwards sends a null

			else if(message.equalsIgnoreCase("allTickets")){
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

			else if(message.equalsIgnoreCase("cancel")){
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

			else if(message.equalsIgnoreCase("addFlight")){
				System.out.println("adding");
				String in = clientIn.readLine();
				String[] nf = in.split("-");
				Flight f = new Flight(Integer.parseInt(nf[0]), Integer.parseInt(nf[1]), Double.parseDouble(nf[2]), Integer.parseInt(nf[3]), nf[4], nf[5], nf[6]);
				
				Integer back = cat.addFlight(f);
				serverOut.println(back.toString());
				System.out.println("added flight");
			}

		}
	}



}
