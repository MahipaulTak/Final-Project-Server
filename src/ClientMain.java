import javax.swing.JOptionPane;

public class ClientMain {
	public static void main(String[] args){
		int rv = JOptionPane.showConfirmDialog(null, "Run as admin?");
		
		AdminGUI guiA;
		ClientGUI guiP;
		
		if(rv == JOptionPane.OK_OPTION)
			guiA = new AdminGUI("localhost", 9898, (PasssengerInfo)null);
		if(rv == JOptionPane.NO_OPTION)
			guiP = new ClientGUI("localhost", 9898, (PasssengerInfo)null);
		else
			System.out.println("Seeya");
	}
	
	
}