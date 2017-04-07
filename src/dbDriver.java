import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
/**
 * 
 * Driver for database interactions between flight catalogue and SQL database
 * 
 * @author luke.renaud, mahipaul.tak, kevin.widemann
 *
 */
public class dbDriver {
	
	
	public Connection getConn() {
		return conn;
	}


	public Statement getStmt() {
		return stmt;
	}


	public ResultSet getRs() {
		return rs;
	}


	/**
	 * Connection to SQL server
	 */
	private Connection conn;
	/**
	 * statement being sent to database
	 */
	private Statement stmt;
	/**
	 * return statement from the server 
	 */
	private ResultSet rs;
	
	
	/**
	 * Constructor for making initial connection to database
	 * 
	 * @throws SQLException
	 */
	public dbDriver() throws SQLException{
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBASE","root","password");
//		stmt = conn.createStatement();
		
	}
	
	
	
	
	
}
