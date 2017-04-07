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

public class AdminGUI extends ClientGUI {
	
	public AdminGUI(String serverName, int portNumber, PasssengerInfo paInfo){
		super();
		client_connection = new Client(serverName, portNumber);
		initialize();
	}
	
	/* FUNCTION FOR CREATING ADMINISTRATOR GUI */
	@Override
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
}
