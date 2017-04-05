
/**
 * Aggregated by flight objects and keeps track of passenger info of passenger with flight 
 * and has a price associated
 * 
 * @author mahipaul.tak, luke.renaud, kevin.widdeman
 * 
 * 
 *
 */

public class Ticket {
	
	


	/**
	 * ticket is for this flight
	 */
	private Flight fl;
	
	/**
	 * Ticket ID
	 */
	private Integer ID;
	
	/**
	 * Info of passenger with ticket
	 */
	private PasssengerInfo info;
	
	/**
	 * price of flight including 7% tax
	 */
	private Integer price;
	
	
	/**
	 * Constructor for Ticket
	 * 
	 * @param fl
	 * @param iD
	 * @param info
	 * @param price
	 */
	public Ticket(Flight fl, Integer iD, PasssengerInfo info, Integer price) {
		super();
		this.fl = fl;
		ID = iD;
		this.info = info;
		this.price = price;
	}
	
	
}
