import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.BorderLayout;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class ClientGUI {

	//connection to the server that will be used as the backend for communications
	protected Client client_connection;
	
	protected PasssengerInfo pInfo;
	
	protected JFrame frame;
	protected JList<String> list;
	
	// variables for criteria
	protected JCheckBox checkBox;
	protected JTextField destinationText;
	protected JTextField sourceText;
	protected JTextField dateText;
	
	// variables for flight info
	protected JTextArea infoText;
	protected int currently_viewed_index;
	
	//list of received flights from server
	protected ArrayList<Flight> flightList;

	/**
	 * shouldn't be used, only for extended class
	 */
	public ClientGUI(){
		super();
	}
	/**
	 * Create the application.
	 */
	public ClientGUI(String serverName, int portNumber, PasssengerInfo paInfo) {
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
		
		JLabel lblSearchByFlight = new JLabel("Search By Flight Criteria: ");
		panel_1.add(lblSearchByFlight);
		
		//button to switch to the criteria window
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.next(frame.getContentPane());
			}
		});
		panel_1.add(btnSearch);
		
		list = new JList<String>(new DefaultListModel<String>());
		list.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e){
				currently_viewed_index = list.getSelectedIndex();
				showFlightInfo();
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
		
/* START OF SEARCH CRITERIA WINDOW */
		//window for entering search criteria
		JPanel searchCriteria = new JPanel();
		frame.getContentPane().add(searchCriteria, "searchCriteria");
		searchCriteria.setLayout(new GridLayout(4,2));

		JLabel lblDate = new JLabel("Date: ");
		searchCriteria.add(lblDate);
		
		dateText = new JTextField();
		JPanel PanelDate = new JPanel(); PanelDate.setLayout(new GridBagLayout()); PanelDate.add(dateText, new GridBagConstraints());
		searchCriteria.add(PanelDate);
		dateText.setColumns(10);
		
		JLabel lblDestination = new JLabel("Destination: ");
		searchCriteria.add(lblDestination);
		
		destinationText = new JTextField();
		JPanel PanelDestination = new JPanel(); PanelDestination.setLayout(new GridBagLayout()); PanelDestination.add(destinationText, new GridBagConstraints());
		searchCriteria.add(PanelDestination);
		destinationText.setColumns(10);

		JLabel lblSource = new JLabel("Source: ");
		searchCriteria.add(lblSource);
		
		sourceText = new JTextField();
		JPanel PanelSource = new JPanel(); PanelSource.setLayout(new GridBagLayout()); PanelSource.add(sourceText, new GridBagConstraints());
		searchCriteria.add(PanelSource);
		sourceText.setColumns(10);

		//button to confirm flight search in criteria window
		JButton btnSearch_1 = new JButton("Search");
		btnSearch_1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				runSearch();
				//switch back to the main window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.first(frame.getContentPane());
			}
		});
		JPanel PanelSearch = new JPanel(); PanelSearch.setLayout(new GridBagLayout()); PanelSearch.add(btnSearch_1, new GridBagConstraints());
		searchCriteria.add(PanelSearch);
		
		JLabel seatsAvailable = new JLabel("Seats Available ");
		JPanel PanelSeats = new JPanel(); PanelSeats.setLayout(new FlowLayout());
		checkBox = new JCheckBox("");
		PanelSeats.add(seatsAvailable); PanelSeats.add(checkBox);
		JPanel centreSeatsAvailable = new JPanel(); centreSeatsAvailable.setLayout(new GridBagLayout()); centreSeatsAvailable.add(PanelSeats, new GridBagConstraints());
		searchCriteria.add(PanelSeats);
/* END OF SEARCH CRITERIA WINDOW */
		
/* START OF FLIGHT INFO WINDOW */
		JPanel flightInfo = new JPanel();
		frame.getContentPane().add(flightInfo, "flightInfo");
		flightInfo.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		flightInfo.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnBookFlight = new JButton("Book Flight");
		btnBookFlight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				boolean rv = bookTicket();
				if(rv == true){
					JOptionPane.showMessageDialog(null, "Ticket booked! Printing to ticket file.");
				}
				else{
					JOptionPane.showMessageDialog(null, "Failed to book ticket! Plane is full.");
				}
				//return to main window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.first(frame.getContentPane());
			}
		});
		panel.add(btnBookFlight);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//go back to the main window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.first(frame.getContentPane());
			}
		});
		panel.add(btnCancel);
		
		infoText = new JTextArea();
		infoText.setEditable(false);
		flightInfo.add(infoText, BorderLayout.CENTER);
/* END OF FLIGHT INFO WINDOW */
		
		frame.setVisible(true);
	}

	
		

	
	protected void runSearch(){
		flightList = client_connection.searchFlights();
		for(int i = 0; i < flightList.size();){
			//Delete all flights not matching the current search criteria
			if(flightList.get(i).Source != sourceText.getText() && !sourceText.getText().equals("")){
				flightList.remove(i);
			}
			else if(flightList.get(i).Destination != destinationText.getText() && !destinationText.getText().equals("")){
				flightList.remove(i);
			}
			else if(flightList.get(i).Date != dateText.getText() && !dateText.getText().equals("")){
				flightList.remove(i);
			}
			else if(checkBox.isSelected() && (flightList.get(i).RemainingSeats != 0)){
				flightList.remove(i);
			}
			else{
				i++;
			}
		}
		
		displayFlights();
	}
	
	protected void displayFlights(){
		//Clear and then update the list
		((DefaultListModel<String>) list.getModel()).clear();
		for(int i = 0; i < flightList.size(); i++){
			((DefaultListModel<String>) list.getModel()).addElement(flightList.get(i).getID());
		}
	}
	
	protected void showFlightInfo(){
		//Switch into the flight info window after using this function
		infoText.setText(flightList.get(currently_viewed_index).toString());
	}
	
	protected boolean bookTicket(){
		boolean rv = client_connection.bookFlight(flightList.get(currently_viewed_index));
		return rv;
	}
}

	protected ArrayList<Flight> flightList;

	/**
	 * shouldn't be used, only for extended class
	 */
	public ClientGUI(){
		super();
	}
	/**
	 * Create the application.
	 */
	public ClientGUI(String serverName, int portNumber, PasssengerInfo paInfo) {
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
		
		JLabel lblSearchByFlight = new JLabel("Search By Flight Criteria: ");
		panel_1.add(lblSearchByFlight);
		
		//button to switch to the criteria window
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.next(frame.getContentPane());
			}
		});
		panel_1.add(btnSearch);
		
		list = new JList<String>(new DefaultListModel<String>());
		list.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e){
				currently_viewed_index = list.getSelectedIndex();
				showFlightInfo();
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
		
/* START OF SEARCH CRITERIA WINDOW */
		//window for entering search criteria
		JPanel searchCriteria = new JPanel();
		frame.getContentPane().add(searchCriteria, "searchCriteria");
		searchCriteria.setLayout(new GridLayout(4,2));

		JLabel lblDate = new JLabel("Date: ");
		searchCriteria.add(lblDate);
		
		dateText = new JTextField();
		JPanel PanelDate = new JPanel(); PanelDate.setLayout(new GridBagLayout()); PanelDate.add(dateText, new GridBagConstraints());
		searchCriteria.add(PanelDate);
		dateText.setColumns(10);
		
		JLabel lblDestination = new JLabel("Destination: ");
		searchCriteria.add(lblDestination);
		
		destinationText = new JTextField();
		JPanel PanelDestination = new JPanel(); PanelDestination.setLayout(new GridBagLayout()); PanelDestination.add(destinationText, new GridBagConstraints());
		searchCriteria.add(PanelDestination);
		destinationText.setColumns(10);

		JLabel lblSource = new JLabel("Source: ");
		searchCriteria.add(lblSource);
		
		sourceText = new JTextField();
		JPanel PanelSource = new JPanel(); PanelSource.setLayout(new GridBagLayout()); PanelSource.add(sourceText, new GridBagConstraints());
		searchCriteria.add(PanelSource);
		sourceText.setColumns(10);

		//button to confirm flight search in criteria window
		JButton btnSearch_1 = new JButton("Search");
		btnSearch_1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				runSearch();
				//switch back to the main window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.first(frame.getContentPane());
			}
		});
		JPanel PanelSearch = new JPanel(); PanelSearch.setLayout(new GridBagLayout()); PanelSearch.add(btnSearch_1, new GridBagConstraints());
		searchCriteria.add(PanelSearch);
		
		JLabel seatsAvailable = new JLabel("Seats Available ");
		JPanel PanelSeats = new JPanel(); PanelSeats.setLayout(new FlowLayout());
		checkBox = new JCheckBox("");
		PanelSeats.add(seatsAvailable); PanelSeats.add(checkBox);
		JPanel centreSeatsAvailable = new JPanel(); centreSeatsAvailable.setLayout(new GridBagLayout()); centreSeatsAvailable.add(PanelSeats, new GridBagConstraints());
		searchCriteria.add(PanelSeats);
/* END OF SEARCH CRITERIA WINDOW */
		
/* START OF FLIGHT INFO WINDOW */
		JPanel flightInfo = new JPanel();
		frame.getContentPane().add(flightInfo, "flightInfo");
		flightInfo.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		flightInfo.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnBookFlight = new JButton("Book Flight");
		btnBookFlight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				boolean rv = bookTicket();
				if(rv == true){
					JOptionPane.showMessageDialog(null, "Ticket booked! Printing to ticket file.");
				}
				else{
					JOptionPane.showMessageDialog(null, "Failed to book ticket! Plane is full.");
				}
				//return to main window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.first(frame.getContentPane());
			}
		});
		panel.add(btnBookFlight);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//go back to the main window
				CardLayout cards = (CardLayout)frame.getContentPane().getLayout();
				cards.first(frame.getContentPane());
			}
		});
		panel.add(btnCancel);
		
		infoText = new JTextArea();
		infoText.setEditable(false);
		flightInfo.add(infoText, BorderLayout.CENTER);
/* END OF FLIGHT INFO WINDOW */
		
		frame.setVisible(true);
	}

	
		

	
	protected void runSearch(){
		flightList = client_connection.searchFlights();
		for(int i = 0; i < flightList.size();){
			//Delete all flights not matching the current search criteria
			if(flightList.get(i).Source != sourceText.getText() && !sourceText.getText().equals("")){
				flightList.remove(i);
			}
			else if(flightList.get(i).Destination != destinationText.getText() && !destinationText.getText().equals("")){
				flightList.remove(i);
			}
			else if(flightList.get(i).Date != dateText.getText() && !dateText.getText().equals("")){
				flightList.remove(i);
			}
			else if(checkBox.isSelected() && (flightList.get(i).RemainingSeats != 0)){
				flightList.remove(i);
			}
			else{
				i++;
			}
		}
		
		displayFlights();
	}
	
	protected void displayFlights(){
		//Clear and then update the list
		((DefaultListModel<String>) list.getModel()).clear();
		for(int i = 0; i < flightList.size(); i++){
			((DefaultListModel<String>) list.getModel()).addElement(Integer.toString(flightList.get(i).FlightNumber));
		}
	}
	
	protected void showFlightInfo(){
		//Switch into the flight info window after using this function
		infoText.setText(flightList.get(currently_viewed_index).toString());
	}
	
	protected boolean bookTicket(){
		boolean rv = client_connection.bookFlight(flightList.get(currently_viewed_index));
		return rv;
	}
}
