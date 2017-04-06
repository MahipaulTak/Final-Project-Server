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
	 * message
	 */
	String message;
	ObjectOutputStream ouut;
	ObjectInputStream iin;
	

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
		System.out.println("Server now connected to the client");
	}

	public void run() throws IOException, ClassNotFoundException{
		while(true){
			message = clientIn.readLine();
			if(message == "allFlights"){
				
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
