import java.io.Serializable;

/**
 * Aggregated by flight objects and keeps track of passenger info of passenger with flight 
 * and has a price associated
 * 
 * @author mahipaul.tak, luke.renaud, kevin.widdeman
 * 
 * 
 *
 */

public class Ticket implements Serializable{
	
	


	public Flight getFl() {
		return fl;
	}


	public void setFl(Flight fl) {
		this.fl = fl;
	}


	public Integer getID() {
		return ID;
	}


	public void setID(Integer iD) {
		ID = iD;
	}


	public PasssengerInfo getInfo() {
		return info;
	}


	public void setInfo(PasssengerInfo info) {
		this.info = info;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


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
	private Double price;
	
	
	/**
	 * Constructor for Ticket
	 * 
	 * @param fl
	 * @param iD
	 * @param info
	 * @param price
	 */
	public Ticket(Flight fl, Integer iD, PasssengerInfo info, Double price) {
		super();
		this.fl = fl;
		ID = iD;
		this.info = info;
		this.price = price;
	}
	
	// doesnt work for soem reason
	
	public String toString(){
		String result = new String("Ticket#: " + ID +" booked for flight#:"+ fl.FlightNumber+  " for the price of $" + price );
		return result;
	}
	
}
