import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
		iin = new ObjectInputStream(Client.getInputStream());
		ouut = new ObjectOutputStream(Client.getOutputStream());
		cat = new FlightCatalogue();
		System.out.println("Server now connected to the client");
	}

	/**
	 * 
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void run() throws IOException, ClassNotFoundException{
		while(true){
			message = clientIn.readLine();
			if(message == "allFlights"){
				for(int i = 0; i < cat.getSize() ; i++){
				ouut.writeObject(cat.getCatalogue().get(i));
				}
			}
			else if(message == "bookFlight"){

			}
			else if(message == "allTickets"){
				
			}
			else if(message == "cancel"){
				
			}
			else if(message == "addFlight"){
				Flight f = (Flight) iin.readObject();
				
			}

		}
	}



}
