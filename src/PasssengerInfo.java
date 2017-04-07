import java.io.Serializable;

/**
 * @author mahipaul.tak, luke.renaud, kevin.widemann
 *
 * Class for keeping track of passenger info and is attached to a ticket
 * should contain all relevant passenger info as provided in documentation
 *
 */
public class PasssengerInfo implements Serializable{

	/**
	 * String representing first name of passenger
	 */
	private String FName;
	/**
	 * String representing last name of passenger
	 */
	private String LName;
	/**
	 * String representind date of birth of passenger
	 * in Formtat: DD/MM/YYYY
	 */
	private String BDate;


	/**
	 * Constructor for passenger info object, bdate should be in the format
	 * DD/MM/YYYY
	 *
	 * @param fName
	 * @param lName
	 * @param bDate
	 */
	public PasssengerInfo(String fName, String lName, String bDate) {
		super();
		FName = fName;
		LName = lName;
		BDate = bDate;
	}

	public String getFName() {
		return FName;
	}

	public String getLName() {
		return LName;
	}

	public String getBDate() {
		return BDate;
	}

	/**
	 * Converts given ints into a date of the format
	 *   DD/MM/YYYY
	 *
	 * @param d
	 * @param m
	 * @param y
	 * @return
	 */
	public static String ItoBD(Integer d, Integer m, Integer y){
		String dd = new String(Integer.toString(d));
		String mm = new String(Integer.toString(m));
		String yyyy = new String(Integer.toString(y));
		
		return (dd + "/" + mm + "/" + yyyy);

	}
	
	/*
	 * Outputs passenger info as
	 * "*FName* *LName* has a date of birth of *BDate*. "
	 *
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return (FName + LName  + "has a date of birth" + BDate);
	}



}
