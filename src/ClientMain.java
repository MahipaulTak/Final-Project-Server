import javax.swing.JOptionPane;
/**
 * @author mahipaul.tak, luke.renaud, kevin.widmann
 *
 */
public class ClientMain {
	public static void main(String[] args){
		int rv = JOptionPane.showConfirmDialog(null, "Run as admin?");
		
		AdminGUI guiA;
		ClientGUI guiP;
		int ss = 0;
		if(rv == JOptionPane.OK_OPTION)
			guiA = new AdminGUI("localhost", 9898, (PasssengerInfo)null);
		else if(rv == JOptionPane.NO_OPTION)
			guiP = new ClientGUI("localhost", 9898, (PasssengerInfo)null);
		else
			System.out.println("Seeya");
	}
	
	
}
