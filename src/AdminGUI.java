import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AdminGUI {

	//connection to the server that will be used as the backend for communications
	protected Client client_connection;
	
	protected PasssengerInfo pInfo;
	
	protected JFrame frame;
	protected JList<String> list;
	
	// variables for criteria
	protected JTextField destinationText;
	protected JTextField sourceText;
	protected JTextField dateText;
	protected JTextField timeText;
	protected JTextField durationText;
	protected JTextField seatsText;
	protected JTextField priceText;
	
	// variables for flight info
	protected JTextArea infoText;
	protected int currently_viewed_index;
	
	//list of received flights from server
	protected ArrayList<Ticket> ticketList;
	/**
	 * Create the application.
	 */
	public AdminGUI(String serverName, int portNumber, PasssengerInfo paInfo) {
		//client_connection = new Client(serverName, portNumber);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 461, 308);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
/* START OF MAIN WINDOW */
		//main window
		JPanel listPane = new JPanel();
		frame.getContentPane().add(listPane, "listPane");
		listPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		listPane.add(panel_1, BorderLayout.SOUTH);
		
		//button to browse tickets
		JButton btnTickets = new JButton("Browse Tickets");
		btnTickets.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				loadTickets();
				displayTickets();
			}
		});
		panel_1.add(btnTickets);
		
		//button to add flights
		JButton btnAddFlight = new JButton("Add Flight");
		btnAddFlight.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//Switch to flight information window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.next(frame.getContentPane());
			}
		});
		panel_1.add(btnAddFlight);
		
		//button to add flights from file
		JButton btnFlightFile = new JButton("Add Flights From File");
		btnFlightFile.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String filename = JOptionPane.showInputDialog("Filename of flights file:");
				//Handle the flight file
				readFlightFile(filename);
			}
		});
		panel_1.add(btnFlightFile);
		
		list = new JList<String>(new DefaultListModel<String>());
		list.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e){
				currently_viewed_index = list.getSelectedIndex();
				showTicketInfo();
				//switch to the info window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.last(frame.getContentPane());
			}
			@Override
			public void mouseEntered(MouseEvent e){
				//do nothing
			}
			public void mouseExited(MouseEvent e){
				//do nothing
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//do nothing
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				//do nothing
			}
		});
		listPane.add(list, BorderLayout.CENTER);
/* END OF MAIN WINDOW */

/* START OF FLIGHT INFORMATION WINDOW */
		//window for entering search criteria
		JPanel flightInfo = new JPanel();
		frame.getContentPane().add(flightInfo, "flightInfo");
		flightInfo.setLayout(new GridLayout(8,2));
		//DATE
		JLabel lblDate = new JLabel("Date: ");
		flightInfo.add(lblDate);
		
		dateText = new JTextField();
		JPanel PanelDate = new JPanel(); PanelDate.setLayout(new GridBagLayout()); PanelDate.add(dateText, new GridBagConstraints());
		flightInfo.add(PanelDate);
		dateText.setColumns(10);
		//TIME
		JLabel lblTime = new JLabel("Time: ");
		flightInfo.add(lblTime);
		
		timeText = new JTextField();
		JPanel PanelTime = new JPanel(); PanelTime.setLayout(new GridBagLayout()); PanelTime.add(timeText, new GridBagConstraints());
		flightInfo.add(PanelTime);
		timeText.setColumns(10);
		//DURATION
		JLabel lblDuration = new JLabel("Duration: ");
		flightInfo.add(lblDuration);
		
		durationText = new JTextField();
		JPanel PanelDuration = new JPanel(); PanelDuration.setLayout(new GridBagLayout()); PanelDuration.add(durationText, new GridBagConstraints());
		flightInfo.add(PanelDuration);
		durationText.setColumns(10);
		//DESTINATION
		JLabel lblDestination = new JLabel("Destination: ");
		flightInfo.add(lblDestination);
		
		destinationText = new JTextField();
		JPanel PanelDestination = new JPanel(); PanelDestination.setLayout(new GridBagLayout()); PanelDestination.add(destinationText, new GridBagConstraints());
		flightInfo.add(PanelDestination);
		destinationText.setColumns(10);
		//SOURCE
		JLabel lblSource = new JLabel("Source: ");
		flightInfo.add(lblSource);
		
		sourceText = new JTextField();
		JPanel PanelSource = new JPanel(); PanelSource.setLayout(new GridBagLayout()); PanelSource.add(sourceText, new GridBagConstraints());
		flightInfo.add(PanelSource);
		sourceText.setColumns(10);
		//NUMBER OF SEATS
		JLabel lblSeats = new JLabel("Seats: ");
		flightInfo.add(lblSeats);
		
		seatsText = new JTextField();
		JPanel PanelSeats = new JPanel(); PanelSeats.setLayout(new GridBagLayout()); PanelSeats.add(seatsText, new GridBagConstraints());
		flightInfo.add(PanelSeats);
		seatsText.setColumns(10);
		//PRICE
		JLabel lblPrice = new JLabel("Price: ");
		flightInfo.add(lblPrice);
		
		priceText = new JTextField();
		JPanel PanelPrice = new JPanel(); PanelPrice.setLayout(new GridBagLayout()); PanelPrice.add(priceText, new GridBagConstraints());
		flightInfo.add(PanelPrice);
		priceText.setColumns(10);

		//button to confirm flight addition in criteria window
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				addSingleFlight();
				//switch back to the main window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.first(frame.getContentPane());
			}
		});
		
		//button to cancel flight addition
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//switch back to the main window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.first(frame.getContentPane());
			}
		});
		
		JPanel PanelConfirm = new JPanel(); PanelConfirm.setLayout(new GridBagLayout()); PanelConfirm.add(btnConfirm, new GridBagConstraints());
		JPanel PanelCancel = new JPanel(); PanelCancel.setLayout(new GridBagLayout()); PanelCancel.add(btnCancel, new GridBagConstraints());
		flightInfo.add(PanelConfirm);
		flightInfo.add(PanelCancel);
/* END OF FLIGHT INFORMATION WINDOW */
		
/* START OF TICKET INFO WINDOW */
		JPanel ticketInfo = new JPanel();
		frame.getContentPane().add(ticketInfo, "ticketInfo");
		ticketInfo.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		ticketInfo.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//return to main window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.first(frame.getContentPane());
			}
		});
		panel.add(btnBack);
		
		JButton btnCancelT = new JButton("Cancel Ticket");
		btnCancelT.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cancelTicket();
				//go back to the main window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.first(frame.getContentPane());
			}
		});
		panel.add(btnCancelT);
		
		infoText = new JTextArea();
		infoText.setEditable(false);
		ticketInfo.add(infoText, BorderLayout.CENTER);
/* END OF TICKET INFO WINDOW */
		
		frame.setVisible(true);
	}

	
		
	protected void addSingleFlight(){
		Flight newFlight = new Flight(Integer.parseInt(durationText.getText()), Integer.parseInt(seatsText.getText()), Double.parseDouble(priceText.getText()), Integer.parseInt(timeText.getText()), sourceText.getText(), destinationText.getText(), dateText.getText());
		client_connection.addFlight(newFlight);
	}
	
	protected void loadTickets(){
		ticketList = client_connection.allTickets();
	}
	
	protected void displayTickets(){
		//Clear and then update the list
		((DefaultListModel<String>) list.getModel()).clear();
		for(int i = 0; i < ticketList.size(); i++){
			((DefaultListModel<String>) list.getModel()).addElement(ticketList.get(i).getID().toString());
		}
	}
	
	protected void showTicketInfo(){
		//Switch into the flight info window after using this function
		infoText.setText(ticketList.get(currently_viewed_index).toString());
	}
	
	protected void cancelTicket(){
		client_connection.cancelTicket(ticketList.get(currently_viewed_index));
	}
	
	protected void readFlightFile(String filename){
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.out.println("file " + filename + " not found");
			e.printStackTrace();
			System.exit(1);
		}
		
		while(input.ready()){
			String line = input.readLine().trim();
			String[] contents = line.split(" ");
			
			Flight toAdd = new Flight(contents[0], Integer.parseInt(contents[1]), Double.parseDouble(contents[2]), Integer.parseInt(contents[3]), contents[4], contents[5], contents[6]);
			client_connection.addFlight(toAdd);
		}
	}
}
